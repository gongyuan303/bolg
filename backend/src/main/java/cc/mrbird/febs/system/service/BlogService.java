package cc.mrbird.febs.system.service;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.service.IService;
import cc.mrbird.febs.system.domain.Blog;

import java.util.List;
import java.util.Map;

public interface BlogService extends IService<Blog> {

    Map<String, Object> findBlogs(QueryRequest request, Blog blog);

    List<Blog> findBlogs(Blog blog, QueryRequest request);

    void createBlog(Blog blog);

    void updateBlog(Blog blog);

    void deleteBlogs(String[] blogIds);
}
