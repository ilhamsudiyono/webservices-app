package com.miniproject.interviewcode.service.store.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.stereotype.Service;

import com.miniproject.interviewcode.model.store.StoreInformation;
import com.miniproject.interviewcode.repository.IStoreInformationRepository;
import com.miniproject.interviewcode.service.store.IStoreInformationService;

@Service
public class StoreInfoProducerServiceImpl implements IStoreInformationService {

	 @Autowired
	    AuthenticationManager authenticationManager;

	 
	@Autowired
	public IStoreInformationRepository iStoreInformationRepository;

//	@Override
//	public Page<StoreInformation> findByUserId(int userId, Pageable pageable) {
//		// TODO Auto-generated method stub
//		return iStoreInformationRepository.findByUserIdContaining(userId, pageable);
//	}

    public List<StoreInformation> getAllStoreInformation(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<StoreInformation> pagedResult = iStoreInformationRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<StoreInformation>();
        }
    }

@Override
public ResponseEntity<?> getAllListInformation(String userId, int pageNo, int pageSize) {
	// TODO Auto-generated method stub
	boolean result = false;
	List<StoreInformation> list = new ArrayList<StoreInformation>();
	Pageable pages = PageRequest.of(pageNo, pageSize);
	Page<StoreInformation> page2 = iStoreInformationRepository.getAllByUserid(userId, pages);
	
	list = page2.getContent();
	
	if (page2.getSize() > 0) {
		result = true;
	} 
	
	Map<String, Object> response = new HashMap<>();
	response.put("result", result);
	response.put("listings", list);
	
	return new ResponseEntity<Map<String, Object>>(response, SetCspHeader(), HttpStatus.OK);
}



public HttpHeaders SetCspHeader() {
	HttpHeaders responseHeaders = new HttpHeaders();
	responseHeaders.set("Content-Security-Policy","base-uri 'self'; object-src 'none'; script-src 'self'" );
	
	return responseHeaders;
}

	

}
