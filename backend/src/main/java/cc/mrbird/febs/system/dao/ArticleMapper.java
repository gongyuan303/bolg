package cc.mrbird.febs.system.dao;

import cc.mrbird.febs.common.config.MyMapper;
import cc.mrbird.febs.system.domain.Article;
import org.springframework.stereotype.Component;

@Component
public interface ArticleMapper extends MyMapper<Article> {
    /**
     * 递归删除
     *
     * @param articleId articleId
     */
    void deleteArticles(String articleId);
}
