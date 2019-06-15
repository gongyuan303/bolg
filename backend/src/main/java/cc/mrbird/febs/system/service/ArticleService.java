package cc.mrbird.febs.system.service;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.service.IService;
import cc.mrbird.febs.system.domain.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService extends IService<Article> {

    Map<String, Object> findArticles(QueryRequest request, Article article);

    List<Article> findArticles(Article articleTag, QueryRequest request);

    void createArticle(Article article);

    void updateArticle(Article article);

    void deleteArticles(String[] articleIds);
}
