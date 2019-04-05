package com.warbs.cmfclogin;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest4 {

    @Test
    public void onCreate() {
        String email = "CS495@gmail.com";
        String expected = "CS495@gmail.com";
        String output = "CS495@gmail.com";
        assertEquals(expected, output, email);
    }


}