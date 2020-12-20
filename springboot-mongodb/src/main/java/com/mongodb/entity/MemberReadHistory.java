package com.mongodb.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @Author lryepoch
 * @CreateDate 2020/12/20 15:25
 * @Description 用户浏览商品历史记录
 */
//标示映射到Mongodb文档上的领域对象
@Document
@Data
public class MemberReadHistory {
    //标示某个域为ID域
    @Id
    private String id;
    //标示某个字段为Mongodb的索引字段
    @Indexed
    private Long memberId;
    private String memberNickname;
    private String memberIcon;
    @Indexed
    private Long productId;
    private String productName;
    private String productPic;
    private String productSubTitle;
    private String productPrice;
    private Date createTime;
}
