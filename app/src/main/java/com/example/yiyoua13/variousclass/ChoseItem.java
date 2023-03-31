package com.example.yiyoua13.variousclass;

public class ChoseItem {
    private String name;
    private String ID;
    private Boolean chose;
    public ChoseItem(String name, String ID, Boolean chose) {
        this.name = name;
        this.ID = ID;
        this.chose = chose;
    }
    public ChoseItem() {
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public Boolean getChose() {
        return chose;
    }
    public void setChose(Boolean chose) {
        this.chose = chose;
    }

    public boolean isChose() {
        return chose;
    }
}
