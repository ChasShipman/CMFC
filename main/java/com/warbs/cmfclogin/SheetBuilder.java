package com.warbs.cmfclogin;

public class SheetBuilder {
    private String ID, content;
    public SheetBuilder(){

    }
    public SheetBuilder(String ID, String content)
    {
        this.ID = ID;
        this.content = content;
    }
    public String getID()
    {
        return ID;
    }
    public String getContent()
    {
        return content;
    }
}
