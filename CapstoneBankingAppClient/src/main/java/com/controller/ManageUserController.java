package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.constants.SessionAttributesConstant;
import com.delegate.UserDelegate;
import com.model.SearchQuery;
import com.model.User;

@Controller
public class ManageUserController {
	
	@Autowired
	private UserDelegate userDelegate;

	@GetMapping("/manage-user")
	public String manageUser(Model model, @RequestParam("query") String query) {
		Boolean userStatus = userDelegate.getStatus();
		if(!userStatus) {
			return "service-down";
		}
		
		// initialize list of User to show
		List<User> users = new ArrayList<User>();
		
		// Add a query attribute to model if theres none
		if(query == null) {
			query = "";
		}
		users = userDelegate.getSearchResult(query);
		
		model.addAttribute("searchResult", users);
		
		return "manage-user";
	}
}
