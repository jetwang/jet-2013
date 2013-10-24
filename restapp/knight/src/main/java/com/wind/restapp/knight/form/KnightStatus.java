package com.wind.restapp.knight.form;

import com.wind.restapp.util.Constants;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "knight-status", namespace = Constants.NS + "/knight-status")
public enum KnightStatus {
    Dead("-1"), King("1");
    private String id;

    KnightStatus(String id) {
        this.id = id;
    }

    public static KnightStatus fromId(String id) {
        for (KnightStatus knightStatus : values()) {
            if (knightStatus.id.equals(id))
                return knightStatus;
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public static String[] ids() {
        String[] ids = new String[values().length];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = values()[i].id;
        }
        return ids;
    }
}