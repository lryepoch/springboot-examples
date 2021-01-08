package com.mongodb.dao;

import com.mongodb.entity.MemberReadHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @Author lryepoch
 * @CreateDate 2020/12/20 15:54
 * @Description dao
 */
public interface MemberReadHistoryRepository extends MongoRepository<MemberReadHistory, String> {
    /**
     * @author lryepoch
     * @date 2020/12/20 15:31
     * @description 根据会员id按时间倒序获取浏览记录
     */
    List<MemberReadHistory> findByMemberIdOrderByCreateTimeDesc(Long memberId);
}
