package com.gsdai.myownwiki.service;

import com.gsdai.myownwiki.domain.Ebook;
import com.gsdai.myownwiki.domain.EbookExample;
import com.gsdai.myownwiki.mapper.EbookMapper;
import com.gsdai.myownwiki.resp.EbookResp;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import req.EbookReq;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EbookService {
    public static final EbookExample EBOOK_EXAMPLE = new EbookExample();
    @Resource
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria ebookExampleCriteria = ebookExample.createCriteria();
        ebookExampleCriteria.andNameLike("%" + req.getName() + "%");
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        List<EbookResp> respList = new ArrayList<>();
        for (Ebook ebook : ebookList) {
            EbookResp ebookResp = new EbookResp();
            BeanUtils.copyProperties(ebook, ebookResp);
            respList.add(ebookResp);
        }

        return respList;
    }
}
