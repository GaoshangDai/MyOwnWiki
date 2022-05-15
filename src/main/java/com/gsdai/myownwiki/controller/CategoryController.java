package com.gsdai.myownwiki.controller;

import com.gsdai.myownwiki.req.CategoryQueryReq;
import com.gsdai.myownwiki.req.CategorySaveReq;
import com.gsdai.myownwiki.resp.CommonResp;
import com.gsdai.myownwiki.resp.CategoryQueryResp;
import com.gsdai.myownwiki.resp.PageResp;
import com.gsdai.myownwiki.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping("/list")
    public CommonResp list(@Valid CategoryQueryReq req) {
        CommonResp<PageResp<CategoryQueryResp>> commonResp = new CommonResp<>();
        PageResp<CategoryQueryResp> list = categoryService.list(req);
        commonResp.setContent(list);
        return commonResp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody CategorySaveReq req) {
        CommonResp commonResp = new CommonResp<>();
        categoryService.save(req);
        return commonResp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id) {
        CommonResp commonResp = new CommonResp<>();
        categoryService.delete(id);
        return commonResp;
    }
}
