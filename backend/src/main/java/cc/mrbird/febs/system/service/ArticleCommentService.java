package cc.mrbird.febs.system.service;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.service.IService;
import cc.mrbird.febs.system.domain.ArticleComment;

import java.util.List;
import java.util.Map;

public interface ArticleCommentService extends IService<ArticleComment> {

    Map<String, Object> findArticleComments(QueryRequest request, ArticleComment articleComment);

    List<ArticleComment> findArticleComments(ArticleComment articleComment, QueryRequest request);

    void createArticleComment(ArticleComment articleComment);

    void updateArticleComment(ArticleComment articleComment);

    void deleteArticleComments(String[] articleCommentIds);
}
