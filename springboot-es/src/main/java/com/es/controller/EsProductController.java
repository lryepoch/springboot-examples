package com.es.controller;

import com.es.service.EsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/esProduct")
public class EsProductController {
    @Autowired
    private EsProductService esProductService;


}
