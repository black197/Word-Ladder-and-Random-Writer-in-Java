package com.thymeleaf.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class PageController {
	private WordLadder wordLadder = new WordLadder();
	
    @GetMapping("/")
    public String greetingForm(Model model) {
    	model.addAttribute("word", new Word());
        return "index";
    }
    
    @PostMapping("/")
    public String greetingSubmit(@ModelAttribute Word word, Map<String, Object> map) throws Exception {
    	String result = wordLadder.findLadder(word.getWord1(), word.getWord2());
    	map.put("answer", result);
    	return "index";
    }
}