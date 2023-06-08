package com.miniproject.interviewcode.service.store;

import org.springframework.http.ResponseEntity;

public interface IStoreInfoProducerService {

	public ResponseEntity<?> getAllStoreList(String userId, int pageNo, int pageSize);
	
	
//	Page<StoreInformation> findByUserId(int userId, Pageable pageable);

//	public ResponseEntity<?> getAllListInformation(Long userId, int page, String substring);
}
