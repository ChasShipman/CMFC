package com.warbs.cmfclogin.Model;

public class User {
    private String username;
    private String password;
    private String email;
   // private String name;
    public User()
    {

    }
    public User( String username, String password, String email)
    {
       //this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;


    }
    /*public String name()
    {

        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }*/
    public String getUsername()
    {

        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
}
