package com.meignotte.nicolas.vapeur;

/**
 * Created by nicolas on 31/05/17.
 */
public class Game {
        int resid;
        String desc;
        String title;
        int price;
        int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Game(String title, int resid, String desc, int price, int id ) {
        this.resid=resid;
        this.desc=desc;
        this.price=price;
        this.title=title;
        this.id=id;
        }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
