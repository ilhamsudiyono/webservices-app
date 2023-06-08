package com.miniproject.interviewcode.model.auth;

import javax.xml.bind.annotation.XmlElement;


public class LoginRequest {
    
	@XmlElement(name = "noTelp", required = false)
    private String noTelp;

	@XmlElement(name = "password", required = false)
	private String password;

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
