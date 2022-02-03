package com.gsdai.myownwiki.controller;

import com.gsdai.myownwiki.domain.Ebook;
import com.gsdai.myownwiki.resp.CommonResp;
import com.gsdai.myownwiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/ebook")
public class EbookController {
    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list() {
        CommonResp<List<Ebook>> commonResp = new CommonResp<>();
        List<Ebook> list = ebookService.list();
        commonResp.setContent(list);
        return commonResp;
    }
}
