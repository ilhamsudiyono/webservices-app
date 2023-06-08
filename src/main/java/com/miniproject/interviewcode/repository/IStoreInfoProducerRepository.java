package com.miniproject.interviewcode.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.miniproject.interviewcode.model.store.StoreInfoProducer;

@Repository
@Transactional
public interface IStoreInfoProducerRepository extends PagingAndSortingRepository<StoreInfoProducer, String> {
//	  Page<StoreInformation> findByPublished(boolean published, Pageable pageable);
	  
	
	@Query(value = "select * from producer_istore where user_id=:userId", nativeQuery = true)
	public Page<StoreInfoProducer> getAllStoreByUserid(@Param("userId") String id_user, Pageable page);

	@Query(value = "select * from producer_istore where user_id=:userId", nativeQuery = true)
	public List<StoreInfoProducer> getAllStoreInfoByUserid(@Param("userId") String id_user);

	@Query(value = "select COUNT(store_id) from producer_istore where user_id=:userId", nativeQuery = true)
	public int getTotalStore(@Param("userId") String id_user);


}
