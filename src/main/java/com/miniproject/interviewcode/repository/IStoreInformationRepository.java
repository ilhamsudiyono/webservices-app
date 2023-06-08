package com.miniproject.interviewcode.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.miniproject.interviewcode.model.store.StoreInformation;
import com.miniproject.interviewcode.model.user.User;

@Repository
@Transactional
public interface IStoreInformationRepository extends PagingAndSortingRepository<StoreInformation, String> {
//	  Page<StoreInformation> findByPublished(boolean published, Pageable pageable);
	  
	
	@Query(value = "select * from istore where user_id=:userId", nativeQuery = true)
	public Page<StoreInformation> getAllByUserid(@Param("userId") String id_user, Pageable page);

	@Query(value = "select COUNT(id) from istore where user_id=:userId", nativeQuery = true)
	public int getTotalStore(@Param("userId") String id_user);
	


}
