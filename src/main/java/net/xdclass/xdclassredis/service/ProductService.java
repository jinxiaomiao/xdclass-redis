package net.xdclass.xdclassredis.service;

import net.xdclass.xdclassredis.model.ProductDO;

import java.util.Map;

/**
 * @author jinxm
 * @date 2022-02-25
 * @description
 */
public interface ProductService {

    int save(ProductDO productDO);

    int delById(int id);

    ProductDO updateById(ProductDO productDO);

    ProductDO findById(int id);

    Map<String,Object> page(int page, int size);
}
