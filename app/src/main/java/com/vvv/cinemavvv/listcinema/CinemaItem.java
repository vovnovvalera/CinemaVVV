package com.vvv.cinemavvv.listcinema;


/**
 * Created by valera on 31.01.2017.
 */
//модель данных для списка
public class CinemaItem {
    public int id;
    public String image;
    public String name;
    public String nameEng;
    public String premiere;
    public String description;

    public CinemaItem(int id, String image, String name, String nameEng, String premiere, String description) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.nameEng = nameEng;
        this.premiere = premiere;
        this.description = description;
    }
}
