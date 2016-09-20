package com.amitupadhyay.clickin.util;

/**
 * Created by aupadhyay on 9/7/16.
 */

public class Services {

    private String desc;
    private int image;
    private String price;
    private String serviceDescription;

    public Services() {
    }

    public Services(String desc, int image, String price, String serviceDescription) {
        this.desc = desc;
        this.image = image;
        this.price = price;
        this.serviceDescription = serviceDescription;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }
}
