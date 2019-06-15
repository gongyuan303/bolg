package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.domain.Tree;
import cc.mrbird.febs.common.service.impl.BaseService;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.utils.TreeUtil;
import cc.mrbird.febs.system.dao.BlogMapper;
import cc.mrbird.febs.system.domain.Blog;
import cc.mrbird.febs.system.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Slf4j
@Service("BlogService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class BlogServiceImpl extends BaseService<Blog> implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public Map<String, Object> findBlogs(QueryRequest request, Blog blog) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Blog> blogs = findBlogs(blog, request);
            List<Tree<Blog>> trees = new ArrayList<>();
            buildTrees(trees, blogs);
            Tree<Blog> blogTree = TreeUtil.build(trees);

            result.put("rows", blogTree);
            result.put("total", blogs.size());
        } catch (Exception e) {
            log.error("获取部门列表失败", e);
            result.put("rows", null);
            result.put("total", 0);
        }
        return result;
    }

    @Override
    public List<Blog> findBlogs(Blog blog, QueryRequest request) {
        Example example = new Example(Blog.class);
        Example.Criteria criteria = example.createCriteria();
//        if (StringUtils.isNotBlank(blog.getBlogName()))
//            criteria.andCondition("blog_name=", blog.getBlogName());
//
//        if (StringUtils.isNotBlank(blog.getCreateTimeFrom()) && StringUtils.isNotBlank(blog.getCreateTimeTo())) {
//            criteria.andCondition("date_format(CREATE_TIME,'%Y-%m-%d') >=", blog.getCreateTimeFrom());
//            criteria.andCondition("date_format(CREATE_TIME,'%Y-%m-%d') <=", blog.getCreateTimeTo());
//        }
        FebsUtil.handleSort(request, example, "order_num");
        return this.selectByExample(example);
    }

    @Override
    @Transactional
    public void createBlog(Blog blog) {
//        Long parentId = blog.getParentId();
//        if (parentId == null)
//            blog.setParentId(0L);
//        blog.setCreateTime(new Date());
        this.save(blog);
    }

    @Override
    @Transactional
    public void updateBlog(Blog blog) {
        blog.setModifyTime(new Date());
        this.updateNotNull(blog);
    }

    @Override
    @Transactional
    public void deleteBlogs(String[] blogIds) {
        Arrays.stream(blogIds).forEach(blogId -> this.blogMapper.deleteBlogs(blogId));
    }

    private void buildTrees(List<Tree<Blog>> trees, List<Blog> blogs) {
        blogs.forEach(blog -> {
            Tree<Blog> tree = new Tree<>();
            tree.setId(blog.getBlogId().toString());
            tree.setKey(tree.getId());
//            tree.setParentId(blog.getParentId().toString());
            tree.setText(blog.getBlogName());
            tree.setCreateTime(blog.getCreateTime());
            tree.setModifyTime(blog.getModifyTime());
            tree.setOrder(blog.getOrderNum());
            tree.setTitle(tree.getText());
            tree.setValue(tree.getId());
            trees.add(tree);
        });
    }
}
