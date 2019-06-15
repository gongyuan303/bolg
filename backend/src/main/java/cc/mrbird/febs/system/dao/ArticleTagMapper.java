package cc.mrbird.febs.system.dao;

import cc.mrbird.febs.common.config.MyMapper;
import cc.mrbird.febs.system.domain.ArticleTag;

public interface ArticleTagMapper extends MyMapper<ArticleTag> {
    /**
     * 递归删除
     *
     * @param articleTagId articleTagId
     */
    void deleteArticleTags(String articleTagId);
}
