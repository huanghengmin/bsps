package com.hzih.bsps.domain;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-30
 * Time: 下午6:26
 * To change this template use File | Settings | File Templates.
 */
public class Resource {
    private int id;
    private String name;
    private int type;
    private String description;

    public Resource() {
    }

    public Resource(String name, int type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
