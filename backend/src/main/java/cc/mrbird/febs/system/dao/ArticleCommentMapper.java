package cc.mrbird.febs.system.dao;

import cc.mrbird.febs.common.config.MyMapper;
import cc.mrbird.febs.system.domain.ArticleComment;

public interface ArticleCommentMapper extends MyMapper<ArticleComment> {

    /**
     * 递归删除
     *
     * @param articleCommentId articleCommentId
     */
    void deleteArticleComments(String articleCommentId);

}
