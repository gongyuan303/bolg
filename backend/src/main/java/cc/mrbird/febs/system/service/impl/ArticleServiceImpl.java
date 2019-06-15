package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.domain.Tree;
import cc.mrbird.febs.common.service.impl.BaseService;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.utils.TreeUtil;
import cc.mrbird.febs.system.dao.ArticleMapper;
import cc.mrbird.febs.system.domain.Article;
import cc.mrbird.febs.system.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Slf4j
@Service("ArticleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ArticleServiceImpl extends BaseService<Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Map<String, Object> findArticles(QueryRequest request, Article article) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Article> articles = findArticles(article, request);
            List<Tree<Article>> trees = new ArrayList<>();
            buildTrees(trees, articles);
            Tree<Article> articleTree = TreeUtil.build(trees);

            result.put("rows", articleTree);
            result.put("total", articles.size());
        } catch (Exception e) {
            log.error("获取部门列表失败", e);
            result.put("rows", null);
            result.put("total", 0);
        }
        return result;
    }

    @Override
    public List<Article> findArticles(Article article, QueryRequest request) {
        Example example = new Example(Article.class);
        Example.Criteria criteria = example.createCriteria();
//        if (StringUtils.isNotBlank(article.getArticleName()))
//            criteria.andCondition("article_name=", article.getArticleName());
//
//        if (StringUtils.isNotBlank(article.getCreateTimeFrom()) && StringUtils.isNotBlank(article.getCreateTimeTo())) {
//            criteria.andCondition("date_format(CREATE_TIME,'%Y-%m-%d') >=", article.getCreateTimeFrom());
//            criteria.andCondition("date_format(CREATE_TIME,'%Y-%m-%d') <=", article.getCreateTimeTo());
//        }
        FebsUtil.handleSort(request, example, "order_num");
        return this.selectByExample(example);
    }

    @Override
    @Transactional
    public void createArticle(Article article) {
//        Long parentId = article.getParentId();
//        if (parentId == null)
//            article.setParentId(0L);
//        article.setCreateTime(new Date());
        this.save(article);
    }

    @Override
    @Transactional
    public void updateArticle(Article article) {
        article.setModifyTime(new Date());
        this.updateNotNull(article);
    }

    @Override
    @Transactional
    public void deleteArticles(String[] articleIds) {
        Arrays.stream(articleIds).forEach(articleId -> this.articleMapper.deleteArticles(articleId));
    }

    private void buildTrees(List<Tree<Article>> trees, List<Article> articles) {
        articles.forEach(article -> {
            Tree<Article> tree = new Tree<>();
            tree.setId(article.getCategoryId().toString());
            tree.setKey(tree.getId());
//            tree.setParentId(article.getParentId().toString());
            tree.setText(article.getTitle());
            tree.setCreateTime(article.getCreateTime());
            tree.setModifyTime(article.getModifyTime());
            tree.setOrder(article.getOrderNum());
            tree.setTitle(tree.getText());
            tree.setValue(tree.getId());
            trees.add(tree);
        });
    }
}
