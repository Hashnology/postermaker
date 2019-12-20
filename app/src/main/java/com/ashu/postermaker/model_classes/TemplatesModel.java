package com.ashu.postermaker.model_classes;

public class TemplatesModel {
    private String template_id;
    private String templateName;
    private String cat_id;
    private String cat_name;
    private String banner_image;

    public TemplatesModel(String template_id, String templateName, String cat_id, String cat_name, String banner_image) {
        this.template_id = template_id;
        this.templateName = templateName;
        this.cat_id = cat_id;
        this.cat_name = cat_name;
        this.banner_image = banner_image;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
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

    public String getBanner_image() {
        return banner_image;
    }

    public void setBanner_image(String banner_image) {
        this.banner_image = banner_image;
    }
}