package com.es.controller;

import com.es.entity.EsProduct;
import com.es.service.EsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lryepoch
 * @date 2020/12/21 10:50
 * @description 搜索商品管理controller
 */
@RestController
@RequestMapping("/esProduct")
public class EsProductController {

    @Autowired
    private EsProductService esProductService;

    /**
     * @author lryepoch
     * @date 2020/12/21 13:49
     * @description 从数据库中导入所有商品到ES
     */
    @RequestMapping(value = "/importAll", method = RequestMethod.GET)
    public Object importAllList() {
        int count = esProductService.importAll();
        return count;
    }

    /**
     * @author lryepoch
     * @date 2020/12/21 13:49
     * @description 根据id删除商品
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public Object delete(@PathVariable Long id) {
        esProductService.delete(id);
        return 1;
    }

    /**
     * @author lryepoch
     * @date 2020/12/21 13:50
     * @description 批量删除商品
     */
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    public Object delete(@RequestParam("ids") List<Long> ids) {
        esProductService.delete(ids);
        return 1;
    }

    /**
     * @author lryepoch
     * @date 2020/12/21 13:49
     * @description 根据id创建商品
     */
    @RequestMapping(value = "/create/{id}", method = RequestMethod.POST)
    public Object create(@PathVariable Long id) {
        EsProduct esProduct = esProductService.create(id);
        if (esProduct != null) {
            return esProduct;
        } else {
            return null;
        }
    }

    /**
     * @author lryepoch
     * @date 2020/12/21 13:50
     * @description 根据关键字搜索名称或者副标题
     */
    @RequestMapping(value = "/search/simple", method = RequestMethod.GET)
    public Object search(@RequestParam(required = false) String keyword,
                         @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                         @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Page<EsProduct> esProductPage = esProductService.search(keyword, pageNum, pageSize);
        return esProductPage;
    }
}
