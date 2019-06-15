package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.domain.Tree;
import cc.mrbird.febs.common.service.impl.BaseService;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.utils.TreeUtil;
import cc.mrbird.febs.system.dao.ArticleCategoryMapper;
import cc.mrbird.febs.system.domain.ArticleCategory;
import cc.mrbird.febs.system.service.ArticleCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Slf4j
@Service("ArticleCategoryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ArticleCategoryServiceImpl extends BaseService<ArticleCategory> implements ArticleCategoryService {

    @Autowired
    private ArticleCategoryMapper articleCategoryMapper;

    @Override
    public Map<String, Object> findArticleCategorys(QueryRequest request, ArticleCategory articleCategory) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<ArticleCategory> articleCategorys = findArticleCategorys(articleCategory, request);
            List<Tree<ArticleCategory>> trees = new ArrayList<>();
            buildTrees(trees, articleCategorys);
            Tree<ArticleCategory> articleCategoryTree = TreeUtil.build(trees);

            result.put("rows", articleCategoryTree);
            result.put("total", articleCategorys.size());
        } catch (Exception e) {
            log.error("获取部门列表失败", e);
            result.put("rows", null);
            result.put("total", 0);
        }
        return result;
    }

    @Override
    public List<ArticleCategory> findArticleCategorys(ArticleCategory articleCategory, QueryRequest request) {
        Example example = new Example(ArticleCategory.class);
        Example.Criteria criteria = example.createCriteria();
//        if (StringUtils.isNotBlank(articleCategory.getArticleCategoryName()))
//            criteria.andCondition("articleCategory_name=", articleCategory.getArticleCategoryName());
//
//        if (StringUtils.isNotBlank(articleCategory.getCreateTimeFrom()) && StringUtils.isNotBlank(articleCategory.getCreateTimeTo())) {
//            criteria.andCondition("date_format(CREATE_TIME,'%Y-%m-%d') >=", articleCategory.getCreateTimeFrom());
//            criteria.andCondition("date_format(CREATE_TIME,'%Y-%m-%d') <=", articleCategory.getCreateTimeTo());
//        }
        FebsUtil.handleSort(request, example, "order_num");
        return this.selectByExample(example);
    }

    @Override
    @Transactional
    public void createArticleCategory(ArticleCategory articleCategory) {
//        Long parentId = articleCategory.getParentId();
//        if (parentId == null)
//            articleCategory.setParentId(0L);
//        articleCategory.setCreateTime(new Date());
        this.save(articleCategory);
    }

    @Override
    @Transactional
    public void updateArticleCategory(ArticleCategory articleCategory) {
        articleCategory.setModifyTime(new Date());
        this.updateNotNull(articleCategory);
    }

    @Override
    @Transactional
    public void deleteArticleCategorys(String[] articleCategoryIds) {
        Arrays.stream(articleCategoryIds).forEach(articleCategoryId -> this.articleCategoryMapper.deleteArticleCategorys(articleCategoryId));
    }

    private void buildTrees(List<Tree<ArticleCategory>> trees, List<ArticleCategory> articleCategorys) {
        articleCategorys.forEach(articleCategory -> {
            Tree<ArticleCategory> tree = new Tree<>();
            tree.setId(articleCategory.getCategoryId().toString());
            tree.setKey(tree.getId());
//            tree.setParentId(articleCategory.getParentId().toString());
            tree.setText(articleCategory.getName());
            tree.setCreateTime(articleCategory.getCreateTime());
            tree.setModifyTime(articleCategory.getModifyTime());
            tree.setOrder(articleCategory.getOrderNum());
            tree.setTitle(tree.getText());
            tree.setValue(tree.getId());
            trees.add(tree);
        });
    }
}
