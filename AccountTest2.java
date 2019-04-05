package com.warbs.cmfclogin;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest2 {

    @Test
    public void onCreate() {
        String username = "CS494";
        String expected = "CS494";
        String password = "1265465465";
        assertEquals(password, expected, username);
    }


}