package com.ashu.postermaker.model_classes;

public class SliderModel {
    private String id;
    private String name;
    private String image;
    private String is_active;
    private String is_delete;

    public SliderModel(String id, String name, String image, String is_active, String is_delete) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.is_active = is_active;
        this.is_delete = is_delete;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }
}
