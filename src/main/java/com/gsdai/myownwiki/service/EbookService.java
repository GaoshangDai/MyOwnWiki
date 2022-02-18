package com.gsdai.myownwiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gsdai.myownwiki.domain.Ebook;
import com.gsdai.myownwiki.domain.EbookExample;
import com.gsdai.myownwiki.mapper.EbookMapper;
import com.gsdai.myownwiki.req.EbookReq;
import com.gsdai.myownwiki.resp.EbookResp;
import com.gsdai.myownwiki.resp.PageResp;
import com.gsdai.myownwiki.util.CopyUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {

    @Resource
    private EbookMapper ebookMapper;

    public PageResp<EbookResp> list(EbookReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria ebookExampleCriteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            ebookExampleCriteria.andNameLike("%" + req.getName() + "%");
        }

        PageHelper.startPage(req.getPage(), req.getSize());
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);
        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);

        List<EbookResp> list = CopyUtil.copyList(ebookList, EbookResp.class);
        PageResp<EbookResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }
}
