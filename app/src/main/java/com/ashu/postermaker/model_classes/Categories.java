package com.ashu.postermaker.model_classes;

import java.util.ArrayList;

public class Categories {

    private String cat_id;
    private String cat_name;
    private ArrayList<TemplatesModel> templatesModelArrayList;

    public Categories(String cat_id, String cat_name, ArrayList<TemplatesModel> templatesModelArrayList) {
        this.cat_id = cat_id;
        this.cat_name = cat_name;
        this.templatesModelArrayList = templatesModelArrayList;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public ArrayList<TemplatesModel> getTemplatesModelArrayList() {
        return templatesModelArrayList;
    }

    public void setTemplatesModelArrayList(ArrayList<TemplatesModel> templatesModelArrayList) {
        this.templatesModelArrayList = templatesModelArrayList;
    }
}