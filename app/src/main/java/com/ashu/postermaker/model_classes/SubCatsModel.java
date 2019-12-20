package com.ashu.postermaker.model_classes;

public class SubCatsModel {

    private String sub_cat_id;
    private String sub_cat_name;
    private String sub_cat_image;







    public SubCatsModel(String sub_cat_id, String sub_cat_name, String sub_cat_image) {
        this.sub_cat_id = sub_cat_id;
        this.sub_cat_name = sub_cat_name;
        this.sub_cat_image = sub_cat_image;
    }

    public String getSub_cat_id() {
        return sub_cat_id;
    }

    public void setSub_cat_id(String sub_cat_id) {
        this.sub_cat_id = sub_cat_id;
    }

    public String getSub_cat_name() {
        return sub_cat_name;
    }

    public void setSub_cat_name(String sub_cat_name) {
        this.sub_cat_name = sub_cat_name;
    }

    public String getSub_cat_image() {
        return sub_cat_image;
    }

    public void setSub_cat_image(String sub_cat_image) {
        this.sub_cat_image = sub_cat_image;
    }
}