package com.mongodb.controller;

import com.mongodb.entity.MemberReadHistory;
import com.mongodb.service.MemberReadHistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author lryepoch
 * @CreateDate 2020/12/20 15:52
 * @Description 会员商品浏览记录管理Controller
 */
@Controller
@RequestMapping("/memeber/readHistory")
public class MemberReadHistoryController {
    @Resource
    private MemberReadHistoryService memberReadHistoryService;

    @ResponseBody
    @PostMapping(value = "/create")
    public Object create(@RequestBody MemberReadHistory memberReadHistory) {
        int count = memberReadHistoryService.create(memberReadHistory);
        if (count > 0) {
            return count;
        } else {
            return 0;
        }
    }

    @ResponseBody
    @PostMapping(value = "/delete")
    public Object delete(@RequestParam("ids") List<String> ids) {
        int count = memberReadHistoryService.delete(ids);
        System.out.println("删除记录条数："+count);
        if (count > 0) {
            return count;
        } else {
            return 0;
        }
    }

    @ResponseBody
    @GetMapping("/list")
    public List<MemberReadHistory> list(Long memberId) {
        return memberReadHistoryService.list(memberId);
    }
}
