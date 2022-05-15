package com.gsdai.myownwiki.service;

import com.gsdai.myownwiki.domain.Category;
import com.gsdai.myownwiki.domain.CategoryExample;
import com.gsdai.myownwiki.mapper.CategoryMapper;
import com.gsdai.myownwiki.req.CategoryQueryReq;
import com.gsdai.myownwiki.req.CategorySaveReq;
import com.gsdai.myownwiki.resp.CategoryQueryResp;
import com.gsdai.myownwiki.util.CopyUtil;
import com.gsdai.myownwiki.util.SnowFlake;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;

    public List<CategoryQueryResp> all() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        List<CategoryQueryResp> list = CopyUtil.copyList(categoryList, CategoryQueryResp.class);
        return list;
    }

    public void save(CategorySaveReq req) {
        Category category = CopyUtil.copy(req, Category.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);
        } else {
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    public void delete(Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }
}
