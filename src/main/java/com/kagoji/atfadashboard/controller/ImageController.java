package com.kagoji.atfadashboard.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.kagoji.atfadashboard.entity.Image;

import com.kagoji.atfadashboard.model.ImageModel;

import com.kagoji.atfadashboard.service.FilesStorageService;
import com.kagoji.atfadashboard.service.ImageService;


import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/image")
public class ImageController {
	
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private FilesStorageService filesStorageService;
	
	@ModelAttribute
	public void Welcome(HttpServletRequest request,Model model) {
		
		String queryString = request.getQueryString();
		System.out.println("ImageController Query String: " + queryString);
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
	
	@GetMapping("/list")
    public String imageMainPage(
            Model model,
            Principal principal,
            Image image,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Image> imPage;

            if (keyword == null) {
            	imPage = imageService.findAllAlbumListWithPagination(pageable);
            } else {
            	imPage = imageService.searchAlbumsByKeyword(keyword, pageable);
            }
         
            model.addAttribute("images", imPage.getContent());
            model.addAttribute("currentPage", imPage.getNumber() + 1);
            model.addAttribute("totalPages", imPage.getTotalPages());
            model.addAttribute("totalItems", imPage.getTotalElements());
            model.addAttribute("pageSize", size);
            model.addAttribute("keyword", keyword);
            model.addAttribute("pageTitle", "Image");
            
            System.out.print(imPage);

        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "pages/Image.html";
    }
	
	@PostMapping("/add")
	 public String albumAdd(
			 ImageModel imageModel,
			 RedirectAttributes redirectAttributes,
			 HttpServletRequest request,
			 @RequestParam("imageFile") MultipartFile file
	 ) { 
		
		String message = "";

	    try {
	    	//String fileUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/path/to/your/files/" + savedFilePath.getFileName();
	    	
	    	String imageFilepath = filesStorageService.save(file);
	    	String imageUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+imageFilepath;
	    	imageModel.setImageUrl(imageUrl);
	    	imageService.saveImage(imageModel);

	      message = "Uploaded the image successfully: " + file.getOriginalFilename();
	      redirectAttributes.addFlashAttribute("message", message);
	      
	    } catch (Exception e) {
	      message = "Could not upload the image: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
	      redirectAttributes.addFlashAttribute("errorMessage", message);
	    }
	
		return "redirect:/image/list"; 
	}
	
	/*@GetMapping("/delete/{id}")
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
	 
	 */
}
