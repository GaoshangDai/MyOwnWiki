package com.gsdai.myownwiki.controller;

import com.gsdai.myownwiki.req.DocSaveReq;
import com.gsdai.myownwiki.resp.DocQueryResp;
import com.gsdai.myownwiki.resp.CommonResp;
import com.gsdai.myownwiki.resp.PageResp;
import com.gsdai.myownwiki.service.DocService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/doc")
public class DocController {
    @Resource
    private DocService docService;

    @GetMapping("/all/{ebookId}")
    public CommonResp all(@PathVariable Long ebookId) {
        CommonResp<List<DocQueryResp>> resp = new CommonResp<>();
        List<DocQueryResp> list = docService.all(ebookId);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody DocSaveReq req) {
        CommonResp commonResp = new CommonResp<>();
        docService.save(req);
        return commonResp;
    }

    @DeleteMapping("/delete/{idsStr}")
    public CommonResp delete(@PathVariable String idsStr) {
        CommonResp commonResp = new CommonResp<>();
        List<String> list =  Arrays.asList(idsStr.split(","));
        docService.delete(list);
        return commonResp;
    }

    @GetMapping("/find-content/{id}")
    public CommonResp findContent(@PathVariable Long id) {
        CommonResp<String> commonResp = new CommonResp<>();
        String content = docService.findContent(id);
        commonResp.setContent(content);
        return commonResp;
    }
}
