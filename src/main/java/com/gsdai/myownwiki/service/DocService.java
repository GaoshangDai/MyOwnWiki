package com.gsdai.myownwiki.service;

import com.gsdai.myownwiki.domain.Doc;
import com.gsdai.myownwiki.domain.DocExample;
import com.gsdai.myownwiki.mapper.DocMapper;
import com.gsdai.myownwiki.req.DocSaveReq;
import com.gsdai.myownwiki.resp.DocQueryResp;
import com.gsdai.myownwiki.util.CopyUtil;
import com.gsdai.myownwiki.util.SnowFlake;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DocService {

    @Resource
    private DocMapper docMapper;

    @Resource
    private SnowFlake snowFlake;

    public List<DocQueryResp> all() {
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        List<Doc> docList = docMapper.selectByExample(docExample);

        List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);
        return list;
    }

    public void save(DocSaveReq req) {
        Doc doc = CopyUtil.copy(req, Doc.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            doc.setId(snowFlake.nextId());
            docMapper.insert(doc);
        } else {
            docMapper.updateByPrimaryKey(doc);
        }
    }

    public void delete(Long id) {
        docMapper.deleteByPrimaryKey(id);
    }
}
