package com.gsdai.myownwiki.controller;

import com.gsdai.myownwiki.req.EbookQueryReq;
import com.gsdai.myownwiki.req.EbookSaveReq;
import com.gsdai.myownwiki.resp.CommonResp;
import com.gsdai.myownwiki.resp.EbookQueryResp;
import com.gsdai.myownwiki.resp.PageResp;
import com.gsdai.myownwiki.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/ebook")
public class EbookController {
    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(@Valid EbookQueryReq req) {
        CommonResp<PageResp<EbookQueryResp>> commonResp = new CommonResp<>();
        PageResp<EbookQueryResp> list = ebookService.list(req);
        commonResp.setContent(list);
        return commonResp;
    }

    @PostMapping("/save")
    public CommonResp save(@RequestBody EbookSaveReq req) {
        CommonResp commonResp = new CommonResp<>();
        ebookService.save(req);
        return commonResp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id) {
        CommonResp commonResp = new CommonResp<>();
        ebookService.delete(id);
        return commonResp;
    }
}
