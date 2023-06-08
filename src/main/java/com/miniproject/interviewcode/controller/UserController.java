package com.miniproject.interviewcode.controller;

import java.net.URI;
import java.time.Instant;
import java.util.Base64;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.miniproject.interviewcode.model.auth.SignUpRequest;
import com.miniproject.interviewcode.model.payload.ApiResponse;
import com.miniproject.interviewcode.model.request.UserManagementRequest;
import com.miniproject.interviewcode.model.role.Role;
import com.miniproject.interviewcode.model.role.RoleName;
import com.miniproject.interviewcode.model.user.User;
import com.miniproject.interviewcode.repository.IRoleRepository;
import com.miniproject.interviewcode.repository.IUserRepository;
import com.miniproject.interviewcode.service.user.IUserService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
//@RequestMapping("/api")
public class UserController {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IUserService iUserService;

	@Autowired
	private IRoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@GetMapping("/users")
	@ApiOperation(value = "Get All User by CurrentUser", notes = "Get All User By Current User")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	public ResponseEntity<?> getUserManagement(@RequestBody UserManagementRequest userRequest,
			HttpServletRequest request) throws Exception {

		String token = request.getHeader("authorization").substring(7);
		String[] parts = token.split("\\.");
		JSONObject payload = new JSONObject(decode(parts[1]));
		boolean exp = payload.getLong("exp") > (System.currentTimeMillis() / 1000);

		if (exp) {
			return iUserService.getAllUsers(userRequest.getPageNo(), userRequest.getPageSize());
		} else {
			return new ResponseEntity<>("UNAUTHORIZED or TOKEN Expired...!", SetCspHeader(), HttpStatus.UNAUTHORIZED);
		}

	}

	@GetMapping("/users/{id}")
	@ApiOperation(value = "Get User Pathvariable by CurrentUser", notes = "Get All User By Current User")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	public ResponseEntity<?> getUserManagementById(@PathVariable String id, HttpServletRequest request)
			throws Exception {

		String token = request.getHeader("authorization").substring(7);
		String[] parts = token.split("\\.");
		JSONObject payload = new JSONObject(decode(parts[1]));
		boolean exp = payload.getLong("exp") > (System.currentTimeMillis() / 1000);
		if (exp) {
			return iUserService.getUserById(id);
		} else {
			return new ResponseEntity<>("UNAUTHORIZED or TOKEN Expired...!", SetCspHeader(), HttpStatus.UNAUTHORIZED);
		}

	}

	@PostMapping("/users")
	public ResponseEntity<?> registerUserEmail(@Valid @RequestBody SignUpRequest signUpRequest) {

		Instant instant = Instant.now();
		Role auth = null;

		if (userRepository.existsByNoTelp(signUpRequest.getNoTelp())) {
			return new ResponseEntity(new ApiResponse(false, "Phone number already in use!"), HttpStatus.BAD_REQUEST);
		}

		User user = new User(signUpRequest.getNoTelp(), signUpRequest.getNama(), signUpRequest.getPassword(), true,
				instant.now(), instant.now());

//		  Create Role ex: ROLE_ADMIN and ROLE_USER       
//        Role authCreate = new Role();
//        authCreate.setName(RoleName.ROLE_ADMIN);
//        authCreate.setCreatedDate(instant);
//        authCreate.setCreatedBy(user.getUserId());
//        auth = roleRepository.save(authCreate);

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		Role userRole = roleRepository.findByName(RoleName.ROLE_USER);
		user.setRoles(Collections.singleton(userRole));

		User result = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{nama}")
				.buildAndExpand(result.getNama()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
	}

	private static String decode(String encodedString) {
		return new String(Base64.getUrlDecoder().decode(encodedString));
	}

	public HttpHeaders SetCspHeader() {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Security-Policy", "base-uri 'self'; object-src 'none'; script-src 'self'");
		return responseHeaders;
	}

}
