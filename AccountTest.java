package com.warbs.cmfclogin;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    @Test
    public void onCreate() {
       String username = "CS495";
       String expected = "CS495";
       String password = "1265465465";
       assertEquals(username, expected, username);
    }


}