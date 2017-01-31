package com.vvv.cinemavvv.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by valera on 31.01.2017.
 */
//ну тут все понятно , простая модель данных
public class ListCinema {
    @SerializedName("list")
    private List<ListData> list;

    public List<ListData> getList() {
        return list;
    }

    public class ListData {
        @SerializedName("image")
        private String image;
        @SerializedName("name")
        private String name;
        @SerializedName("name_eng")
        private String nameEng;
        @SerializedName("premiere")
        private String premiere;
        @SerializedName("description")
        private String description;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNameEng() {
            return nameEng;
        }

        public void setNameEng(String nameEng) {
            this.nameEng = nameEng;
        }

        public String getPremiere() {
            return premiere;
        }

        public void setPremiere(String premiere) {
            this.premiere = premiere;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
