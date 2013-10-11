package com.wind.restapp.knight.form;

public enum KnightStatus {
    Dead("-1", "dead"), King("1", "king");

    private String id;
    private String title;

    KnightStatus(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public static KnightStatus fromId(String id) {
        for (KnightStatus knightStatus : values()) {
            if (knightStatus.id.equals(id))
                return knightStatus;
        }
        return null;
    }
}