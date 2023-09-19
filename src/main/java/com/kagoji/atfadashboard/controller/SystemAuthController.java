package com.kagoji.atfadashboard.controller;

import java.security.Principal;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



import com.kagoji.atfadashboard.model.UserModel;
import com.kagoji.atfadashboard.service.UserService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class SystemAuthController {
	
	
	private UserService userService;
	public SystemAuthController(UserService userService) {
        this.userService = userService;
    }
	
	
	@ModelAttribute
	public void Welcome(HttpServletRequest request,Model model) {
		String queryString = request.getQueryString();
        System.out.println("SystemAuthController Query String: " + queryString);
     // Get a specific query parameter (e.g., "message")
        String message = request.getParameter("message");
        String errorMessage = request.getParameter("errorMessage");
        if (message != null) {
            // Set a value in the model based on the value of message
            model.addAttribute("message", message);
        } else if (errorMessage != null) {
            // Set a different value in the model based on the value of errorMessage
            model.addAttribute("errorMessage", errorMessage);
        }else {
        	
        }
		
	}
	
	@GetMapping("/sign-up")
	public String SignUpPage() {
		return "pages/SignUp.html";
	}
	
	@PostMapping("/sign-up")
	public String userRegistrationSave(@ModelAttribute UserModel userModel ) {
		userService.registerUser(userModel);
		
		return "redirect:/sign-up?message=Registration successfully completed.";
	}
	
	
	//@GetMapping({"/", "/sign-in"})
	@GetMapping("/sign-in")
	public String login(Model model,UserModel userModel) {
		model.addAttribute(userModel);
		return "pages/SignIn.html";
	}
	
	@GetMapping("/temp")
	public String dashboard() {
		//return "pages/blank-page.html";
		return "pages/AdminDashboard.html";
	}
	
	/*
	 * @GetMapping("/admin/dashboard") public String dashboard(Model model,Principal
	 * principal) { UserDetails userDetails =
	 * userDetailsService.loadUserByUsername(principal.getName());
	 * model.addAttribute(userDetails); return "pages/AdminDashboard.html"; }
	 */

}
