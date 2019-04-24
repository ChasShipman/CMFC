package com.warbs.cmfclogin;

public class Composition {
    private String title, edit;//, create;

    public Composition() {
    }

    public Composition(String title, String edit){//, String create) {
        this.title = title;
        this.edit = edit;
        // this.create = create;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String name) {

        this.title = name;
    }

    //  public String getCreate() {
//
    //      return create;
    //  }

    // public void setCreate(String create) {
//
    //      this.create = create;
    // }

    public String getEdit() {

        return edit;
    }

    public void setEdit(String edit) {

        this.edit = edit;
    }
}
