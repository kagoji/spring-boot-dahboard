package com.kagoji.atfadashboard.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kagoji.atfadashboard.entity.Album;
import com.kagoji.atfadashboard.model.AlbumModel;
import com.kagoji.atfadashboard.service.AlbumService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/album")
public class AlbumController {
	
	@Autowired
	private AlbumService albumService;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@ModelAttribute
	public void AlbumControllerWelcome(HttpServletRequest request, Model model) {

		String queryString = request.getQueryString();
		System.out.println("AlbumController Query String: " + queryString);
		String message = request.getParameter("message");
		String errorMessage = request.getParameter("errorMessage");
		if (message != null) { 
			model.addAttribute("message", message);
		} else if (errorMessage != null) { 
			model.addAttribute("errorMessage", errorMessage);
		} else {

		}

	}

	@GetMapping("/list")
	 public String albumMainPage(Model model,Principal principal,Album album) { 
		
		UserDetails	  userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute(userDetails);
		model.addAttribute("pageTitle", "Album");
		
		ArrayList<Album> albums = new ArrayList<Album>();
		albums = albumService.findAllAlbumList();
		model.addAttribute("albums", albums);
		
		return "pages/Album.html"; 
	}
	
	@PostMapping("/add")
	 public String albumAdd(Model model,AlbumModel albumModel,RedirectAttributes redirectAttributes) { 
		albumService.saveAlbum(albumModel);
		redirectAttributes.addFlashAttribute("message", "Album has been added!!");
		return "redirect:/album/list"; 
	}
}
