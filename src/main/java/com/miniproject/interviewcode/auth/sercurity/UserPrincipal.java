package com.miniproject.interviewcode.auth.sercurity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.miniproject.interviewcode.model.user.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idUser;

    private String noTelp;

    
    private String nama;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long idUser, String noTelp, String nama,String password,Collection<? extends GrantedAuthority> authorities) {
        this.idUser = idUser;
        this.noTelp = noTelp;
        this.nama = nama;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());

        return new UserPrincipal(
                user.getUserId(),
                user.getNoTelp(),
                user.getNama(),
                user.getPassword(),
                authorities
        );
    }

    public Long getId() {
        return idUser;
    }


    public String getNama() {
        return nama;
    }

    

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(idUser, that.idUser);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idUser);
    }

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return noTelp;
	}
}
