package com.miniproject.interviewcode.model.auth;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Id;
import javax.validation.constraints.*;

import com.miniproject.interviewcode.model.role.Role;


public class SignUpRequest {

	@Id
    @NotBlank
    @Size(min = 10, max = 13)
    private String noTelp;

    @NotBlank
    @Size(max = 60)
    private String nama;

    @NotBlank
    @Size(min = 6, max = 16)
    private String password;
    
    @NotBlank
    private Set<Role> roleName;
    
    @NotBlank
	private Boolean isLogin;
    
    @NotBlank
	private Timestamp createdAt;
    
	
    public String getNoTelp() {
		return noTelp;
	}

	public void setNoTelp(String noTelp) {
		this.noTelp = noTelp;
	}

	public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
    
	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



	public Boolean getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(Boolean isLogin) {
		this.isLogin = isLogin;
	}
	
	public Set<Role> getRoleName() {
		return roleName;
	}

	public void setRoleName(@NotBlank Set<Role> roleName) {
		this.roleName = roleName;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	
    
    
}
