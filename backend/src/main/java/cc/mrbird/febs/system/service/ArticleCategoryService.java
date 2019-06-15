package cc.mrbird.febs.system.service;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.service.IService;
import cc.mrbird.febs.system.domain.ArticleCategory;

import java.util.List;
import java.util.Map;

public interface ArticleCategoryService extends IService<ArticleCategory> {

    Map<String, Object> findArticleCategorys(QueryRequest request, ArticleCategory articleCategory);

    List<ArticleCategory> findArticleCategorys(ArticleCategory articleCategory, QueryRequest request);

    void createArticleCategory(ArticleCategory articleCategory);

    void updateArticleCategory(ArticleCategory articleCategory);

    void deleteArticleCategorys(String[] articleCategoryIds);
}
