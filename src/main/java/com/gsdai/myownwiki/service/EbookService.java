package com.gsdai.myownwiki.service;

import com.gsdai.myownwiki.domain.Ebook;
import com.gsdai.myownwiki.domain.EbookExample;
import com.gsdai.myownwiki.mapper.EbookMapper;
import com.gsdai.myownwiki.req.EbookReq;
import com.gsdai.myownwiki.resp.EbookResp;
import com.gsdai.myownwiki.util.CopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {

    @Resource
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria ebookExampleCriteria = ebookExample.createCriteria();
        ebookExampleCriteria.andNameLike("%" + req.getName() + "%");
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        return CopyUtil.copyList(ebookList, EbookResp.class);
    }
}
