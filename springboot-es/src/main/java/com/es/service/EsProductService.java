package com.es.service;

import com.es.entity.EsProduct;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/12/21 10:28
 * @description 商品搜索管理Service
 */
public interface EsProductService {
    /**
     * @author lryepoch
     * @date 2020/12/21 10:31
     * @description 从数据库中导入所有商品到ES
     */
    int importAll();

    /**
     * @author lryepoch
     * @date 2020/12/21 10:30
     * @description 根据id删除商品
     */
    void delete(Long id);

    /**
     * @author lryepoch
     * @date 2020/12/21 10:30
     * @description 根据id创建商品
     */
    EsProduct create(Long id);

    /**
     * @author lryepoch
     * @date 2020/12/21 10:30
     * @description 批量删除商品
     */
    void delete(List<Long> ids);

    /**
     * @author lryepoch
     * @date 2020/12/21 10:30
     * @description 根据关键字搜索名称或者副标题
     */
    Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize);
}
