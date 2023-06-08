package com.miniproject.interviewcode.controller;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.interviewcode.model.request.SInformationRequest;
import com.miniproject.interviewcode.model.store.StoreInfoProducer;
import com.miniproject.interviewcode.model.store.TypeList;
import com.miniproject.interviewcode.model.user.User;
import com.miniproject.interviewcode.repository.IStoreInfoProducerRepository;
import com.miniproject.interviewcode.repository.IUserRepository;
import com.miniproject.interviewcode.service.store.IStoreInfoProducerService;
import com.miniproject.interviewcode.service.user.IUserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/public-api")
public class StoreInfoProducerController {
	
	@Autowired
	private IStoreInfoProducerRepository iStoreInformationRepository;
	
	@Autowired
	private IUserRepository userRepository;
	
	
	@Autowired
	private IStoreInfoProducerService storeInformationService;
	
	@Autowired
	private IUserService userService;
	
	
	private static String decode(String encodedString) {
		return new String(Base64.getUrlDecoder().decode(encodedString));
	}
	
	public HttpHeaders SetCspHeader() {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Security-Policy","base-uri 'self'; object-src 'none'; script-src 'self'" );
		
		return responseHeaders;
	}
	
	@GetMapping("/listings")
	 @ApiOperation(value = "Get Listing Store by CurrentUser", notes = "Get Listing Store By Current User")
		@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	  public ResponseEntity<?> getAllListInformation(
	        @RequestParam String userId, @RequestParam int pageNo, @RequestParam int pageSize, HttpServletRequest request) throws Exception{
				
		 String token = request.getHeader("authorization").substring(7);
		 String[] parts = token.split("\\.");
		 JSONObject payload = new JSONObject(decode(parts[1]));
		 boolean exp = payload.getLong("exp") > (System.currentTimeMillis()/1000);

		 if (exp) {
			 return storeInformationService.getAllStoreList(userId, pageNo, pageSize);
		} else {
			return new ResponseEntity<>("UNAUTHORIZED or TOKEN Expired...!", SetCspHeader(), HttpStatus.UNAUTHORIZED);
	    }
	  }
	
	 @GetMapping("/listings2")
	 @ApiOperation(value = "Get Listing Store by CurrentUser", notes = "Get Listing Store By Current User")
		@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	  public ResponseEntity<?> getAllListByUserid(
			  @RequestBody SInformationRequest informationRequest, HttpServletRequest request) throws Exception{
				
		 String token = request.getHeader("authorization").substring(7);
		 String[] parts = token.split("\\.");
		 JSONObject payload = new JSONObject(decode(parts[1]));
		 boolean exp = payload.getLong("exp") > (System.currentTimeMillis()/1000);

		 if (exp) {
			 return storeInformationService.getAllStoreList(informationRequest.getUserId(), informationRequest.getPageNo(), informationRequest.getPageSize());
		} else {
			return new ResponseEntity<>("UNAUTHORIZED or TOKEN Expired...!", SetCspHeader(), HttpStatus.UNAUTHORIZED);
	    }
	  }
	 
	
	 @PostMapping("/create/listings")
		@ApiOperation(value = "Create Listing Store by CurrentUser", notes = "Create Listing Store By Current User")
		@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
		public ResponseEntity<?> createListingPublic(@RequestBody SInformationRequest informationRequest,
				HttpServletRequest request) throws Exception {
		 
		 String token = request.getHeader("authorization").substring(7);
		 String[] parts = token.split("\\.");
		 JSONObject payload = new JSONObject(decode(parts[1]));
		 boolean exp = payload.getLong("exp") > (System.currentTimeMillis()/1000);
		 Instant instant = Instant.now();
		 boolean result = false;
		 if (exp) {
			 User usr = userService.getListByIdUser(informationRequest.getUserId());
			 
			 if (usr.getUserId() != null) {
				StoreInfoProducer store = new StoreInfoProducer();
				
				store.setUserId(this.userRepository.getListByIdUser(informationRequest.getUserId()));
				store.setListing_type(informationRequest.getListingType().equals("RENT") ? TypeList.valueOf("RENT") : TypeList.valueOf("SALE"));
				store.setPrice(informationRequest.getPrice());
				store.setCreatedAt(instant.toEpochMilli());
				store.setUpdateAt(instant.toEpochMilli());
				iStoreInformationRepository.save(store);
				
				List<StoreInfoProducer> list = new ArrayList<StoreInfoProducer>();
				Pageable pages = PageRequest.of(0, iStoreInformationRepository.getTotalStore(informationRequest.getUserId()));
				Page<StoreInfoProducer> page2  = iStoreInformationRepository.getAllStoreByUserid(informationRequest.getUserId(), pages);
				list = page2.getContent();
				if (page2.getSize() > 0) {
					result = true;
				} 
				
				Map<String, Object> response = new HashMap<>();
			    response.put("listings", list);
				return new ResponseEntity<Map<String, Object>>(response, SetCspHeader(), HttpStatus.OK);
				
			} else {
				return new ResponseEntity<>("User Id is not found... !", SetCspHeader(), HttpStatus.FOUND);

			}
		} else {
			return new ResponseEntity<>("UNAUTHORIZED or TOKEN Expired...!", SetCspHeader(), HttpStatus.UNAUTHORIZED);
	    }
	 }
	

	

}
