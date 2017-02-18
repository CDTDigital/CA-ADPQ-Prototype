package com.intimetec.crns.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/")
public class DefaultController {
	
	@RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "Get Login Page", notes = "Default page for application that is Login Page")
	String index() {
		return "login";
	}
	
	@RequestMapping(value = "forgotPassword", method = RequestMethod.GET)
	@ApiOperation(value = "Get Forgot password Page", notes = "Forgot password Page")
	public String forgotPassword() {
		return "forgotPassword";
	}
	
	@RequestMapping(value = "signup", method = RequestMethod.GET)
	@ApiOperation(value = "Get Sign Up Page", notes = "Sign Up Page")
	String signup() {
		return "signup";
	}
}