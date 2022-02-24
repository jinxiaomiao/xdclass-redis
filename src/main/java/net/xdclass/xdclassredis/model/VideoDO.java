package net.xdclass.xdclassredis.model;

/**
 * @author jinxm
 * @date 2022-02-24
 * @description
 */
public class VideoDO {

    private int id;

    private String title;

    private String img;

    private int price;

    public VideoDO(){}

    public VideoDO(int id, String title, String img, int price) {
        this.id = id;
        this.title = title;
        this.img = img;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
