package com.merlot.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.merlot.demo.model.Material;
import com.merlot.demo.model.Member;
import com.merlot.demo.model.MemberType;
import com.merlot.demo.repository.MaterialRepository;
import com.merlot.demo.repository.MemberTypeRepository;

@SpringBootApplication
public class Merlot2Application  implements CommandLineRunner {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static String MATERIALS_FILE = "materials.csv";
	private static String MEMBER_TYPE_FILE = "member_types.csv";
	
	@Autowired
	private MemberTypeRepository memberTypeRepository;
	
	@Autowired
	private MemberServiceImpl memberService;
	
	@Autowired
	private MaterialRepository materialRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(Merlot2Application.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		File file = new File(MEMBER_TYPE_FILE);
		if (file.exists() && file.isFile()) {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String line = "";
			while((line = in.readLine()) != null) {
				try {
					MemberType memberType = MemberType.parseMemberType(line);
					memberTypeRepository.save(memberType);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
					System.out.println("Failed Parsing line: " + line);
				}
			}
			in.close();
			List<MemberType> memberTypes = (List<MemberType>) memberTypeRepository.findAll();
			logger.info("*******************************************************Found " + memberTypes.size() + " records");
		} else {
			List<MemberType> types = getMemberTypes();
			types.stream().forEach(book -> memberTypeRepository.save(book));
			List<MemberType> savedMemberTypes = (List<MemberType>) memberTypeRepository.findAll();
			saveMemberTypesToDisk(savedMemberTypes);
		}
		// Load materials
		file = new File(MATERIALS_FILE);
		if (file.exists() && file.isFile()) {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String line = "";
			while((line = in.readLine()) != null) {
				try {
					Material material = Material.parseMaterial(line);
					materialRepository.save(material);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
					System.out.println("Failed Parsing line: " + line);
				}
			}
			in.close();
		} else {
			// First Run Save
			List<Material> materials = getMaterials();
			materials.stream().forEach(material -> materialRepository.save(material));
			List<Material> savedMaterials = (List<Material>) materialRepository.findAll();
			saveMaterialsToDisk(savedMaterials);
		}

		// Create first Member
		Member firstMember = getFirstMember();
		memberService.create(firstMember);
	}
	
	/**
	 * 
	 * @param memberTypes
	 * @throws IOException
	 */
	public static void saveMemberTypesToDisk(List<MemberType> memberTypes) throws IOException {
		File file = new File(MEMBER_TYPE_FILE);
		if(!file.exists()) {
			file.createNewFile();
		}
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		for(MemberType type : memberTypes) {
			out.write(type.toString() + "\r\n");
		}
		out.flush();
		out.close();
	}
	
	public static void saveMaterialsToDisk(List<Material> materials) throws IOException {
		File file = new File(MATERIALS_FILE);
		if(!file.exists()) {
			file.createNewFile();
		}
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		for(Material m : materials) {
			out.write(m.toString() + "\r\n");
		}
		out.flush();
		out.close();
	}
	
	public static List<MemberType> getMemberTypes() {
		List<MemberType> list = new ArrayList<>();
		list.add(new MemberType("Faculty"));
		list.add(new MemberType("Staff"));
		list.add(new MemberType("Student"));
		list.add(new MemberType("Administrator"));
		list.add(new MemberType("Librarian"));
		list.add(new MemberType("Health Care Professional"));
		list.add(new MemberType("Consultant"));
		list.add(new MemberType("Teacher (K-12)"));
		list.add(new MemberType("Content Developer/Author"));
		list.add(new MemberType("Other"));
		return list;
	}
	
	public static Member getFirstMember() {
		Member member = new Member();
		member.setFirst_name("Robert");
		member.setLast_name("Tu");
		member.setUsername("user");
		member.setPassword("test");
		member.setEmail("robert.f.tu@gmail.com");
		member.setDisplayEmailFlag('Y');
		member.setJob_title("Consultant");
        member.setMember_type("Consultant");
		return member;
	}
	
	public static List<Material> getMaterials() {
		List<Material> materials = new ArrayList<>();
		materials.add(new Material("The Hobbit", "A Children Fantasy Book", "Book", "J. R. R. Tolkien", "", ""));
		materials.add(new Material("Lord of the Rings", "A Children Fantasy Book", "Book", "J. R. R. Tolkien", "", ""));
		materials.add(new Material("Snow White and the Seven Dwarfs", "A Children Fantasy Book", "Book", "Brothers Grimm", "", ""));
		materials.add(new Material("Moby Dick", "About the big Whale", "Book", "Herman Melville", "", ""));
		materials.add(new Material("Snow Crash", "A Science Fiction Story", "Book", "Neal Stephenson", "", ""));
		//materials.add(new Book("Game of Thrones", "George R. R. Martin", "A Fantasy Fiction Story"));
		return materials;
	}
}




