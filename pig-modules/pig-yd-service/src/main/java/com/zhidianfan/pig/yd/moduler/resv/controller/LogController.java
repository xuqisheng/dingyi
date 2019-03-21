package com.zhidianfan.pig.yd.moduler.resv.controller;

import com.zhidianfan.pig.yd.moduler.common.dto.SuccessTip;
import com.zhidianfan.pig.yd.moduler.common.dto.Tip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author sherry
 * @Description
 * @Date Create in 2018/11/16
 * @Modified By:
 */

@RestController("/log")
public class LogController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 删除指定表的指定条数，按照指定日期
     *
     * @param tableName
     * @param filedName
     * @param count
     * @return
     */
    @GetMapping("/alllog/delete")
    public ResponseEntity deleteByConfig(String tableName, String filedName, int count) {
        String sql = "delete from " + tableName + " where " + filedName + " < " + filedName + " limit " + count;
        int delteCount = jdbcTemplate.update(sql);
        Tip tip = new SuccessTip(200, "" + delteCount);
        return ResponseEntity.ok(tip);
    }

}
