package com.mongodb.service;

import com.mongodb.entity.MemberReadHistory;

import java.util.List;

public interface MemberReadHistoryService {

    /**
     * @author lryepoch
     * @date 2020/12/20 15:33
     * @description 生成浏览记录
     */
    int create(MemberReadHistory memberReadHistory);

    /**
     * @author lryepoch
     * @date 2020/12/20 15:33
     * @description 批量删除浏览记录
     */
    int delete(List<String> ids);

    /**
     * @author lryepoch
     * @date 2020/12/20 15:33
     * @description 获取用户浏览历史记录
     */
    List<MemberReadHistory> list(Long memberId);
}
