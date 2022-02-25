package net.xdclass.xdclassredis.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.xdclass.xdclassredis.model.ProductDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author jinxm
 * @date 2022-02-25
 * @description
 */
@Mapper
public interface ProductMapper extends BaseMapper<ProductDO> {
}
