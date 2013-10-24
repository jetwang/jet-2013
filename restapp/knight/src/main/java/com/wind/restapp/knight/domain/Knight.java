package com.wind.restapp.knight.domain;


import com.wind.restapp.knight.form.KnightStatus;
import com.wind.restapp.util.Constants;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.MessageFormat;

@javax.persistence.Entity
@Table(name = "knight", uniqueConstraints = {@UniqueConstraint(columnNames = {"knight_id", "KNIGHT_NUMBER"})})
@XmlRootElement(name = "knight", namespace = Constants.NS + "/knight")
public class Knight {
    @Column(name = "knight_id")
    private String knightId;
    @Column(length = 20, name = "name")
    private String name;
    @Column(length = 200, name = "description")
    private String description;
    @Column(length = 10, name = "KNIGHT_NUMBER")
    @GeneratedValue()
    @Id
    private long number;
    @Column
    private String statusId = KnightStatus.Dead.getId();

    public String getKnightId() {
        return knightId;
    }

    public void setKnightId(String knightId) {
        this.knightId = knightId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public KnightStatus status() {
        return KnightStatus.fromId(statusId);
    }

    public void status(KnightStatus status) {
        if (status != null)
            this.statusId = status.getId();
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return MessageFormat.format("[Id: {0}, Name: {1}]", knightId, name);
    }
}
