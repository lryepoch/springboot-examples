package com.mongodb.service.impl;

import com.mongodb.dao.MemberReadHistoryMapper;
import com.mongodb.entity.MemberReadHistory;
import com.mongodb.service.MemberReadHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author lryepoch
 * @CreateDate 2020/12/20 15:55
 * @Description service
 */
@Service
public class MemberReadHistoryServiceImpl implements MemberReadHistoryService {
    @Autowired
    private MemberReadHistoryMapper memberReadHistoryMapper;

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public int create(MemberReadHistory memberReadHistory) {
        memberReadHistory.setId(null);
        memberReadHistory.setCreateTime(new Date());
        MemberReadHistory save = memberReadHistoryMapper.save(memberReadHistory);

//        MemberReadHistory save1 = mongoTemplate.save(memberReadHistory);

        return 1;
    }

    @Override
    public int delete(List<String> ids) {
        List<MemberReadHistory> deleteList = new ArrayList<>();
        for (String id:ids){
            MemberReadHistory memberReadHistory = new MemberReadHistory();
            memberReadHistory.setId(id);
            deleteList.add(memberReadHistory);
        }
        memberReadHistoryMapper.deleteAll(deleteList);

//        mongoTemplate.remove();

        return 0;
    }

    @Override
    public List<MemberReadHistory> list(Long memberId) {
        List<MemberReadHistory> list = memberReadHistoryMapper.findByMemberIdOrderByCreateTimeDesc(memberId);

//        Query query = new Query(Criteria.where("memberId").is(memberId));
//        MemberReadHistory list = mongoTemplate.findOne(query, MemberReadHistory.class);

        return list;
    }
}

