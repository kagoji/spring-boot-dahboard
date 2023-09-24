package com.kagoji.atfadashboard.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kagoji.atfadashboard.entity.User;
import com.kagoji.atfadashboard.model.UserModel;
import com.kagoji.atfadashboard.service.UserService;
import com.kagoji.atfadashboard.service.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class SystemAuthController {
	
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	private UserServiceImpl userServiceImpl;
	public SystemAuthController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }
	
	
	@ModelAttribute
	public void Welcome(HttpServletRequest request,Model model) {
		
		String queryString = request.getQueryString();
		System.out.println("AdminController Query String: " + queryString);
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
	public String SignUpPage(Principal principal) {
		if(principal.getName() != null) {
        	System.out.println("SystemAuthController Auth: " + principal.getName());
        	return "redirect:/admin/dashboard?message=you have already loggedin.";
        }
		return "pages/SignUp.html";
	}
	
	@PostMapping("/sign-up")
	public String userRegistrationSave(@ModelAttribute UserModel userModel ) {
		userServiceImpl.registerUser(userModel);
		
		return "redirect:/sign-up?message=Registration successfully completed.";
	}
	
	
	@GetMapping({"/", "/sign-in"})
	public String login(Model model,UserModel userModel) {
		
		model.addAttribute(userModel);
		return "pages/SignIn.html";
	}
	
	@GetMapping("/temp")
	public String dashboard(Principal principal) {
		
	        if(principal.getName() != null) {
	        	System.out.println("SystemAuthController Auth: " + principal.getName());
	        }
		return "pages/AdminDashboard.html";
	}
	
	@GetMapping("/change-password")
	public String changePasswordPage(Model model, Principal principal) {
		
	        if(principal.getName() != null) {
	        	System.out.println("SystemAuthController Auth: " + principal.getName());
	        }
	        model.addAttribute("pageTitle", "Change Password");
		return "pages/ChangePassoword.html";
	}
	
	@PostMapping("/change-password")
	public String changePasswordSubmit(
			Model model, 
			Principal principal,
			@RequestParam("currentPassword") String currentPassword,
			@RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword
			) {
			
			// Retrieve the currently authenticated user
        	User currentUser = userServiceImpl.findByUserName(principal.getName());

        	// Verify if the provided current password matches the stored hashed password
            if (!passwordEncoder.matches(currentPassword, currentUser.getPassword())) {
                // Current password is incorrect
                return "redirect:/change-password?errorMessage=Invalid Current Password";
            }
            System.out.println("newPassword: " + newPassword);
            System.out.println("confirmPassword: " + confirmPassword);
            if (! newPassword.equals(confirmPassword)) {
                // Current password is incorrect
                return "redirect:/change-password?errorMessage=New password and confirm password doesn't matched";
            }
         // Hash the new password
            String hashedPassword = passwordEncoder.encode(newPassword);
            
            currentUser.setPassword(hashedPassword);
            
            userServiceImpl.saveUser(currentUser);
            
            return "redirect:/change-password?message=Password has been changed successfully.";
	}
	
	

}
