package com.kapcb.ccc.encryption.controller;

import com.alibaba.fastjson2.JSON;
import com.kapcb.ccc.encryption.po.common.MyDatasource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <a>Title: TestController </a>
 * <a>Author: cb <a>
 * <a>Description: TestController <a>
 *
 * @author cb
 * @version 1.0
 * @date 2022/10/26 16:23
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("test")
@RequiredArgsConstructor
public class TestController {

    private final MyDatasource myDatasource;

    @GetMapping
    public String getMyDatasource() {
        String result = JSON.toJSONString(myDatasource);
        log.info("result is : {}", result);
        return result;
    }

}