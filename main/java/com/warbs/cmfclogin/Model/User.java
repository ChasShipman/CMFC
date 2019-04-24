package com.warbs.cmfclogin.Model;

public class User {
    private String password;
    private String email;
    private String name;
    private String UID;
    public User()
    {

    }
    public User(String name, String password, String email)
    {
       this.name = name;
       // this.username = username;
        this.password = password;
        this.email = email;


    }
    public String getUID()
    {
        return UID;
    }
    public void setUID(String UID)
    {
        this.UID = UID;
    }
    public String getName()
    {

        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    /*public String getUsername()
    {

        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    */public String getPassword()
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
