package com.gsdai.myownwiki.controller;

import com.gsdai.myownwiki.resp.CommonResp;
import com.gsdai.myownwiki.resp.EbookResp;
import com.gsdai.myownwiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import req.EbookReq;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/ebook")
public class EbookController {
    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(EbookReq req) {
        CommonResp<List<EbookResp>> commonResp = new CommonResp<>();
        List<EbookResp> list = ebookService.list(req);
        commonResp.setContent(list);
        return commonResp;
    }
}
