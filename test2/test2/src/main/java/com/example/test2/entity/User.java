package com.example.test2.entity;

import jakarta.persistence.*;


@Entity
@Table(name= "newcredential")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    @Column(unique = true)
    private String userName;
    @Column
    private String password;

    @Column
    private String userRole;

    public User() {
        this.userRole = "user"; // Default role set to "user"
    }

    // security Question ------------------------------->

    @Column(name = "security_answer1")
    private String securityAnswer1;

    @Column(name = "security_answer2")
    private String securityAnswer2;

    public String getSecurityAnswer1() {
        return securityAnswer1;
    }

    public void setSecurityAnswer1(String securityAnswer1) {
        this.securityAnswer1 = securityAnswer1;
    }


    public String getSecurityAnswer2() {
        return securityAnswer2;
    }

    public void setSecurityAnswer2(String securityAnswer2) {
        this.securityAnswer2 = securityAnswer2;
    }

    // --------------------------------------------------------------#######

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public User(Long userId, String userName, String password) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}



