package com.donal.model;

/**
 * User
 */

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Simple POJO to contain user attributes
 */

@XmlRootElement(name = "user")
public class UserData {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
