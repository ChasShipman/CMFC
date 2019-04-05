package com.warbs.cmfclogin;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest3 {

    @Test
    public void onCreate() {
        String email = "CS494@gmail.com";
        String expected = "CS494@gmail.com";
        String output = "CS494@gmail.com";
        assertEquals(expected, output, email);
    }


}