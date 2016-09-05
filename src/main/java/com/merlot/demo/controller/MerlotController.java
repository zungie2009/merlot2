/**
 * 
 */
package com.merlot.demo.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.merlot.demo.CurrentUser;
import com.merlot.demo.MemberServiceImpl;
import com.merlot.demo.Merlot2Application;
import com.merlot.demo.model.Material;
import com.merlot.demo.model.Member;
import com.merlot.demo.model.MemberType;
import com.merlot.demo.repository.MaterialRepository;
import com.merlot.demo.repository.MemberTypeRepository;

/**
 * @author Robert Tu - (Sep 3, 2016 3:45:15 PM)
 *
 */
@Controller
public class MerlotController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MemberTypeRepository memberTypeRepository;
	
	@Autowired
	private MaterialRepository materialRepository;
	
	@Autowired
	private MemberServiceImpl memberService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@RequestMapping("/")
    public String index(Model model, HttpSession session) {
		/*String sessionUserName = (String) session.getAttribute("username");
		if(sessionUserName == null) {
			sessionUserName = getAuthenticatedUsername();
			session.setAttribute("username", sessionUserName);
		}*/
		if(getAuthenticatedMember() != null) {
			model.addAttribute("member", getAuthenticatedMember());
		}
		return "main";
    }
	
	@RequestMapping("/edit/{id}")
    public String editWithId(@PathVariable("id") long id,  Model model) {
		//updateSession(model, session);
		if(getAuthenticatedMember() != null) {
			model.addAttribute("member", getAuthenticatedMember());
		}
		if(id > 0) {
			Material material = materialRepository.findOne(id);
			if(material != null) {
				model.addAttribute("material", material);
			}
		}
		List<Material> materials = (List<Material>) materialRepository.findAll();
		model.addAttribute("materials", materials);
		return "edit";
    }
	
	@RequestMapping("/edit")
    public String edit(Model model) {
		//updateSession(model, session);
		if(getAuthenticatedMember() != null) {
			model.addAttribute("member", getAuthenticatedMember());
		}
		model.addAttribute("material", new Material());
		List<Material> materials = (List<Material>) materialRepository.findAll();
		model.addAttribute("materials", materials);
		return "edit";
    }
	
	@RequestMapping("/signup")
    public String signup(@RequestParam Map<String,String> params, Model model) {
		Set<String> keys = params.keySet();
		keys.stream().forEach(key -> System.out.println("*******************Found Key " + key + " with value: " + params.get(key)));
		
		List<MemberType> memberTypes = (List<MemberType>) memberTypeRepository.findAll();
        //System.out.println("*******************************************************Found " + memberTypes.size() + " records");
		model.addAttribute("editmember", new Member());
        model.addAttribute("memberTypes", memberTypes);
		return "signup";
    }
	
	@RequestMapping("/member_edit/{id}")
	public String editMember(@PathVariable("id") long id, Model model) {
		Member member = null;
		if(id > 0) {
			member = memberService.getMemberById(id).get();
		}
		if(member != null) {
			model.addAttribute("editmember", member);
		} else {
			model.addAttribute("editmember", new Member());
		}
		List<MemberType> memberTypes = (List<MemberType>) memberTypeRepository.findAll();
        model.addAttribute("memberTypes", memberTypes);
		return "signup";
    }
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
    public String login(@RequestParam Optional<String> error, Model model, HttpSession session) {
		List<Member> memberList = (List<Member>) memberService.getAllUsers();
		//logger.info("Found " + memberList.size() + " members");
		if(error.isPresent()) {
			model.addAttribute("error", error);
		}
		return "login";
    }
	
	@RequestMapping(value="/saveMaterial", method=RequestMethod.POST)
    public String submitLogin(@RequestParam("id") long id, @RequestParam Map<String,String> params, Model model, HttpSession session) {
		//Set<String> keys = params.keySet();
		//keys.stream().forEach(key -> System.out.println("*******************Found Key " + key + " with value: " + params.get(key)));
		//updateSession(model, session);
		if(getAuthenticatedMember() != null) {
			model.addAttribute("member", getAuthenticatedMember());
		}
		Material material = new Material();
		if(id > 0) {
			// Update
			material.setId(id);
			logger.info("*******************Update material ID " + id);
		}
		material.setTitle(params.get("title"));
		material.setDescription(params.get("description"));
		material.setCategory(params.get("category"));
		material.setAuthor(params.get("author"));
		material.setUrl(params.get("url"));
		material.setIsbn(params.get("isbn"));
		Material savedMaterial = materialRepository.save(material);
		model.addAttribute("material", savedMaterial);
		List<Material> materials = (List<Material>) materialRepository.findAll();
		model.addAttribute("materials", materials);
		persistMaterialsToDisk(materials);
		return "edit";
    }
	
	@RequestMapping(value="/signon", method=RequestMethod.POST)
    public String signon(@RequestParam Map<String,String> params, Model model, HttpSession session) {
		List<MemberType> memberTypes = (List<MemberType>) memberTypeRepository.findAll();
        model.addAttribute("memberTypes", memberTypes);
        
        Member member = new Member();
        member.setFirst_name(params.get("member.merlotUserDetails.firstName"));
        member.setLast_name(params.get("member.merlotUserDetails.lastName"));
        member.setEmail(params.get("member.merlotUserDetails.email"));
        member.setUsername(params.get("member.merlotUserDetails.userName"));
        member.setPassword(params.get("member.merlotUserDetails.password"));
        if(params.get("member.displayEmail").equals("true")) {
        	member.setDisplayEmailFlag('Y');
        } else {
        	member.setDisplayEmailFlag('N');
        }
        member.setJob_title(params.get("member.title"));
        member.setMember_type(params.get("member.memberType"));
        Member savedMember = memberService.create(member);
        model.addAttribute("savedMember", savedMember);
        
        Optional<Member> newMember = memberService.getMemberByUsername(params.get("member.merlotUserDetails.userName"));
		return "main";
    }
	
	protected String getUserUID(HttpSession session) {
		UUID uid = (UUID) session.getAttribute("uid");
        if(uid == null) {
        	uid = UUID.randomUUID();
        }
        return uid.toString();
	}
	
	protected void updateSession(Model model, HttpSession session) {
		String sessionUserName = (String) session.getAttribute("username");
		if(sessionUserName == null) {
			sessionUserName = getAuthenticatedUsername();
			session.setAttribute("username", sessionUserName);
		}
		model.addAttribute("username", sessionUserName);
	}
	
	/**
	 * 
	 * @return
	 */
	protected String getAuthenticatedUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    String userName = "";
	    if(!name.equalsIgnoreCase("anonymousUser")) {
	    	CurrentUser user = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    	Member loginMember = user.getMember();
	    	userName = loginMember.getFirst_name() + " " + loginMember.getLast_name();
	    	//System.out.println("Found Login User Name: " + userName);
	    }
		return userName;
	}
	
	/**
	 * 
	 * @return
	 */
	protected Member getAuthenticatedMember() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    if(!name.equalsIgnoreCase("anonymousUser")) {
	    	CurrentUser user = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    	return user.getMember();
	    }
		return null;
	}
	
	protected void persistMaterialsToDisk(List<Material> materials) {
		try {
			Merlot2Application.saveMaterialsToDisk(materials);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	protected Connection getConnection() throws SQLException {
		return jdbcTemplate.getDataSource().getConnection();
	}
	
	protected Member loadMember(String userName, String password) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "Select * from member where user_name = ? and password = ?";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,  userName);
			pstmt.setString(2,  password);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Member member = new Member();
				member.setFirst_name(rs.getString("first_name"));
				member.setLast_name(rs.getString("last_name"));
				member.setUsername(userName);
				member.setEmail(rs.getString("enail"));
				member.setJob_title("job_title");
				member.setMember_type("member_type");
				return member;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(sql);
			throw e;
		} finally {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
		}
		return null;
	}
}
