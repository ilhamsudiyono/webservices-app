package com.miniproject.interviewcode.service.user;

import java.util.List;
import java.util.Optional;

import com.miniproject.interviewcode.model.role.Role;
import com.miniproject.interviewcode.model.role.RoleName;


public interface IRoleService  {
	
	List<Role> getListByIdRole(Long idRole);
	
	List<Role> findAll();
	
	Role save(Role role);

    void deleteById(Long id);
    
    Role findByName(RoleName roleName);

}
