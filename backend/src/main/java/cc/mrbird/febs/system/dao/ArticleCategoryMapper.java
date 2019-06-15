package cc.mrbird.febs.system.dao;

import cc.mrbird.febs.common.config.MyMapper;
import cc.mrbird.febs.system.domain.ArticleCategory;

public interface ArticleCategoryMapper extends MyMapper<ArticleCategory> {

    /**
     * 递归删除
     *
     * @param articleCategoryId articleCategoryId
     */
    void deleteArticleCategorys(String articleCategoryId);
}
