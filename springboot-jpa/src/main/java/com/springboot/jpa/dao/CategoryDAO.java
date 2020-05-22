package com.springboot.jpa.dao;

import com.springboot.jpa.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * @author lryepoch
 * @date 2019/11/29
 * @description
 */
public interface CategoryDAO  extends JpaRepository<Category, Integer> {

}