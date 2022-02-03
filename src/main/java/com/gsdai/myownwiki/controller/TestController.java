package com.gsdai.myownwiki.controller;

import com.gsdai.myownwiki.domain.Test;
import com.gsdai.myownwiki.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class TestController {
    @Resource
    private TestService testService;

    @RequestMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/test/list")
    public List<Test> list() {
        return testService.list();
    }
}
