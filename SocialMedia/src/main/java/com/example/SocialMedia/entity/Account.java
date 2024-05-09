package com.example.SocialMedia.entity;

import jakarta.persistence.*;

//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;

@Entity
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="accountId")
    private Integer accountId;  //unique and not blank
    private String username;     //over 4 char
    private String password;
   
    
    //no args constructor
    public Account(){

    }
   
    
    
    public Account(String username, String password){
        this.username = username;
        this.password = password;
    }
    
  //Parameterized constructor, useful when to retrieve data from database
    public Account(Integer accountId, String username, String password) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
    }


    //getters and setters
    public Integer getAccountId() {
        return accountId;
    }
   
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }


    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
   
    
    public String getPassword() {
        return password;
    }
   
    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
    //Overriding equals method and toString method
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

    
    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
    
}
