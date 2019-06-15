package cc.mrbird.febs.system.service;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.service.IService;
import cc.mrbird.febs.system.domain.ArticleTag;

import java.util.List;
import java.util.Map;

public interface ArticleTagService extends IService<ArticleTag> {

    Map<String, Object> findArticleTags(QueryRequest request, ArticleTag articleTag);

    List<ArticleTag> findArticleTags(ArticleTag articleTag, QueryRequest request);

    void createArticleTag(ArticleTag articleTag);

    void updateArticleTag(ArticleTag articleTag);

    void deleteArticleTags(String[] articleTagIds);
}
