package com.example.quanlydanhba;

import java.io.Serializable;

public class Contact implements Serializable {
    private String name;
    private String phone;
    private String group;
    private String gender;
    private boolean isFavorite;
    private String note;

    public Contact(String name, String phone, String group, String gender, boolean isFavorite, String note) {
        this.name = name;
        this.phone = phone;
        this.group = group;
        this.gender = gender;
        this.isFavorite = isFavorite;
        this.note = note;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getGroup() { return group; }
    public void setGroup(String group) { this.group = group; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getAvatarText() {
        if (name != null && !name.trim().isEmpty()) {
            String[] parts = name.trim().split("\\s+");
            return parts[parts.length - 1].substring(0, 1).toUpperCase();
        }
        return "C";
    }
}