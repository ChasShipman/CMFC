package com.warbs.cmfclogin;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest5 {

    @Test
    public void onCreate() {
        String password  = "12345678";
        String expected = "12345678";
        String output = "12345678";
        assertEquals(expected, output, password);
    }


}