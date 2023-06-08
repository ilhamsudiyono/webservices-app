package com.miniproject.interviewcode.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

	@GetMapping("/test")
	public ResponseEntity<?> test() {
		return ResponseEntity.ok("It is working...");
	}

}
