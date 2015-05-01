package com.example.inga.mcash;

/**
 * Created by Inga on 22.04.2015.
 */
public class MenuItem {

    private String name;
    private String img;

    public MenuItem(String name, String img){
        this.name = name;
        this.img = img;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public void setImg(String img){
        this.img=img;
    }

    public String getImg(){
        return img;
    }

}
