package net.xdclass.xdclassredis.controller;

import net.xdclass.xdclassredis.dao.VideoDao;
import net.xdclass.xdclassredis.model.VideoDO;
import net.xdclass.xdclassredis.util.JsonData;
import net.xdclass.xdclassredis.util.JsonUtil;
import net.xdclass.xdclassredis.vo.CartItemVO;
import net.xdclass.xdclassredis.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jinxm
 * @date 2022-02-23
 * @description
 */
@RestController
@RequestMapping("api/v1/cart")
public class CartController {



    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private VideoDao videoDao;

    @RequestMapping("add")
    public JsonData addCart(int videoId,int buyNum){

        //获取购物车
        BoundHashOperations<String,Object,Object> myCart = getMyCartOps();

        Object cacheObj = myCart.get(videoId+"");

        String result = "";
        if(cacheObj!=null){
            result = (String)cacheObj;
        }


        //购物车没这个商品
        if(cacheObj == null){

            CartItemVO cartItem = new CartItemVO();
            VideoDO videoDO = videoDao.findDetailById(videoId);

            cartItem.setBuyNum(buyNum);
            cartItem.setPrice(videoDO.getPrice());
            cartItem.setProductId(videoDO.getId());
            cartItem.setProductImg(videoDO.getImg());
            cartItem.setProductTitle(videoDO.getTitle());

            myCart.put(videoId+"",JsonUtil.objectToJson(cartItem));

        }else {
          //增加商品购买数量
            CartItemVO cartItemVO = JsonUtil.jsonToPojo(result,CartItemVO.class);
            cartItemVO.setBuyNum(cartItemVO.getBuyNum()+buyNum);

            myCart.put(videoId+"",JsonUtil.objectToJson(cartItemVO));
        }


        return JsonData.buildSuccess();


    }


    /**
     * 查看我的购物车
     */
    @RequestMapping("mycart")
    public JsonData getMycart(){
        //获取购物车
        BoundHashOperations<String,Object,Object> myCart = getMyCartOps();

        List<Object> itemList =  myCart.values();

        List<CartItemVO> cartItemVOList = new ArrayList<>();

        for(Object item : itemList){
            CartItemVO cartItemVO = JsonUtil.jsonToPojo((String)item,CartItemVO.class);
            cartItemVOList.add(cartItemVO);
        }

        CartVO cartVO = new CartVO();
        cartVO.setCartItems(cartItemVOList);

        return JsonData.buildSuccess(cartVO);

    }


    /**
     * 清空我的购物车
     * @return
     */
    @RequestMapping("clear")
    public JsonData clear(){

        String key = getCartKey();
        redisTemplate.delete(key);

        return JsonData.buildSuccess();
    }




    /**
     * 抽取我的购物车通用方法
     * @return
     */
    private BoundHashOperations<String,Object,Object> getMyCartOps(){

        String key = getCartKey();

        return redisTemplate.boundHashOps(key);
    }


    private String getCartKey(){

        //用户的id,从拦截器获取
        int userId = 88;
        String cartKey = String.format("video:cart:%s",userId);
        return cartKey;

    }


}
