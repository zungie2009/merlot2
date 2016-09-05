package com.merlot.demo;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Robert Tu - (Sep 1, 2016 2:08:53 PM)
 *
 */
public class MvcConfig extends WebMvcConfigurerAdapter {
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("merlot");
        registry.addViewController("/merlot/signup").setViewName("signup");
        registry.addViewController("/merlot/logout").setViewName("logout");
        registry.addViewController("/merlot/login").setViewName("login");
        registry.addViewController("/merlot/edit").setViewName("edit");
    }
}
