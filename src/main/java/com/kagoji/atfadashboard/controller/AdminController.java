package com.kagoji.atfadashboard.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


import jakarta.servlet.http.HttpServletRequest;

@Controller
//@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	/*
	 * @ModelAttribute public void AdminControllerWelcome(HttpServletRequest
	 * request,Model model) { String queryString = request.getQueryString();
	 * System.out.println("AdminController Query String: " + queryString); // Get a
	 * specific query parameter (e.g., "message") String message =
	 * request.getParameter("message"); String errorMessage =
	 * request.getParameter("errorMessage"); if (message != null) { // Set a value
	 * in the model based on the value of message model.addAttribute("message",
	 * message); } else if (errorMessage != null) { // Set a different value in the
	 * model based on the value of errorMessage model.addAttribute("errorMessage",
	 * errorMessage); }else {
	 * 
	 * }
	 * 
	 * }
	 */
	
	@GetMapping("/admin/dashboard")
	public String dashboard(Model model,Principal principal) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute(userDetails);
		return "pages/AdminDashboard.html";
	}
	
	
}
