package net.xdclass.xdclassredis.vo;

/**
 * @author jinxm
 * @date 2022-02-24
 * @description
 */

public class CartItemVO {
    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 购买数量
     */
    private Integer buyNum;

    /**
     * 商品标题
     */
    private String productTitle;

    /**
     * 图片
     */
    private String productImg;

    /**
     * 商品单价
     */
    private int price;

    /**
     * 总价格，单价+数量
     */
    private int totalPrice;


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    /**
     * 商品单价 * 购买数量
     *
     * @return
     */
    public int getTotalPrice() {

        return this.price * this.buyNum;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}