package com.gsdai.myownwiki.service;

import com.gsdai.myownwiki.domain.Demo;
import com.gsdai.myownwiki.mapper.DemoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DemoService {
    @Resource
    private DemoMapper demoMapper;

    public List<Demo> list() {
        return demoMapper.selectByExample(null);
    }
}
