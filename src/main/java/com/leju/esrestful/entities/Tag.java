package com.leju.esrestful.entities;

import org.springframework.beans.factory.annotation.Autowired;

import com.leju.esrestful.configuration.ElasticsearchConfiguration;

public class Tag {
    private String id;
    private String name;

    
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}