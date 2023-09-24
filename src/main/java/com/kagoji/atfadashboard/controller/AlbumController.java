package com.kagoji.atfadashboard.controller;


import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String albumMainPage(
            Model model,
            Principal principal,
            Album album,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Album> albumPage;

            if (keyword == null) {
                albumPage = albumService.findAllAlbumListWithPagination(pageable);
            } else {
                albumPage = albumService.searchAlbumsByKeyword(keyword, pageable);
            }
            albumPage = albumService.findAllAlbumListWithPagination(pageable);
            model.addAttribute("albums", albumPage.getContent());
            model.addAttribute("currentPage", albumPage.getNumber() + 1);
            model.addAttribute("totalPages", albumPage.getTotalPages());
            model.addAttribute("totalItems", albumPage.getTotalElements());
            model.addAttribute("pageSize", size);
            model.addAttribute("keyword", keyword);
            model.addAttribute("pageTitle", "Album");

        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "pages/Album.html";
    }
	
	@PostMapping("/add")
	 public String albumAdd(
			 Model model,AlbumModel albumModel,
			 RedirectAttributes redirectAttributes
	 ) { 
		albumService.saveAlbum(albumModel);
		redirectAttributes.addFlashAttribute("message", "Album has been added!!");
		return "redirect:/album/list"; 
	}
	
	 @GetMapping("/delete/{id}")
	 public String deleteAlbum(
			 @PathVariable("id") String id, 
			 Model model, 
			 RedirectAttributes redirectAttributes
	 ) {
	    try {
	      albumService.deleteById(id);

	      redirectAttributes.addFlashAttribute("message", "The Album has been deleted successfully!");
	    } catch (Exception e) {
	      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
	    }

	    return "redirect:/album/list";
	  }
	 
	 @GetMapping("/edit/{id}")
	 public String editAlbum(
			 @PathVariable("id") String id, 
			 Model model, 
			 RedirectAttributes redirectAttributes,
             @RequestParam(defaultValue = "1") int page,
             @RequestParam(defaultValue = "3") int size
	 ) {
	    try {
	    
	      //Get album
	      Album albuminfo = albumService.findAlbum(id).orElseThrow(() -> new IllegalArgumentException("Invalid Album ID"));
	      model.addAttribute("albuminfo",albuminfo);
	      
	      //list data with pagination
	      Pageable pageable = PageRequest.of(page - 1, size);
          Page<Album> albumPage;
          albumPage = albumService.findAllAlbumListWithPagination(pageable);
          model.addAttribute("albums", albumPage.getContent());
          model.addAttribute("currentPage", albumPage.getNumber() + 1);
          model.addAttribute("totalPages", albumPage.getTotalPages());
          model.addAttribute("totalItems", albumPage.getTotalElements());
          model.addAttribute("pageSize", size);
          model.addAttribute("pageTitle", "Edit Album");
	    } catch (Exception e) {
	      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
	      return "redirect:/album/list";
	    }

	    return "pages/Album.html";
	  }
	 
	 @PostMapping("/edit/{id}")
	 public String albumUpdate(
			 Model model,
			 AlbumModel albumModel,
			 @PathVariable("id") String id,
			 RedirectAttributes redirectAttributes
	 ) { 
		albumService.updateAlbum(id,albumModel);
		redirectAttributes.addFlashAttribute("message", "Album has been updated!!");
		return "redirect:/album/list"; 
	}
	 
	 @GetMapping("/{id}/published/{status}")
	 public String albumPublishStatusChange(
			 Model model,
			 AlbumModel albumModel,
			 @PathVariable("id") String id,
			 @PathVariable("status") int status,
			 RedirectAttributes redirectAttributes
	 ) { 
		albumService.updateAlbumStatus(id,status);
		redirectAttributes.addFlashAttribute("message", "Album publish status has been changed!!");
		return "redirect:/album/list"; 
	}
	 
	 
}
