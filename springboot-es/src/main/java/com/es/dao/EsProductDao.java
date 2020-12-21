package com.es.dao;

import com.es.entity.EsProduct;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/12/21 10:41
 * @description TODO
 */
public interface EsProductDao {
    /**
     * @author lryepoch
     * @date 2020/12/21 11:18
     * @description 搜索系统中的商品管理自定义Dao
     */
    List<EsProduct> getAllEsProductList(@Param("id") Long id);
}
