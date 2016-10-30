package com.donal.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by donal on 10/30/16.
 */

@XmlRootElement(name = "post")
public class PostData {

    private String id;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
