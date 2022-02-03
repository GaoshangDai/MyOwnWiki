package com.gsdai.myownwiki.service;

import com.gsdai.myownwiki.domain.Test;
import com.gsdai.myownwiki.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestService {
    @Resource
    private TestMapper testMapper;

    public List<Test> list() {
        return testMapper.list();
    }
}
