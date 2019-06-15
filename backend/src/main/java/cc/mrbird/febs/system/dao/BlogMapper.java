package cc.mrbird.febs.system.dao;

import cc.mrbird.febs.common.config.MyMapper;
import cc.mrbird.febs.system.domain.Blog;

public interface BlogMapper extends MyMapper<Blog> {
    /**
     * 递归删除
     *
     * @param blogId blogId
     */
    void deleteBlogs(String blogId);
}
