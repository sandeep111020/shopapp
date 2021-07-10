package com.example.shopsapp;

public class shopmodel {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    String name;
    String desc;
    String number;
    String city;
    String items;
    String offer;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    String url;
    public  shopmodel(){

    }
   public shopmodel(String name,String desc,String number,String city,String items,String offer,String url){
        this.name=name;
        this.number=number;
        this.desc=desc;
        this.city=city;
        this.offer=offer;
        this.items=items;
        this.url=url;

   }
}
