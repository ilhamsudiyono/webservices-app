package com.miniproject.interviewcode.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.miniproject.interviewcode.model.store.StoreInformation;

@Repository
@Transactional
public interface IStoreInformationRepository extends PagingAndSortingRepository<StoreInformation, String> {
	
	@Query(value = "select * from istore where user_id=:userId", nativeQuery = true)
	public Page<StoreInformation> getAllByUserid(@Param("userId") String id_user, Pageable page);

	@Query(value = "select COUNT(id) from istore where user_id=:userId", nativeQuery = true)
	public int getTotalStore(@Param("userId") String id_user);
	


}
