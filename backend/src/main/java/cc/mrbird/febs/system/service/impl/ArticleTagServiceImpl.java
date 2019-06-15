package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.domain.Tree;
import cc.mrbird.febs.common.service.impl.BaseService;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.utils.TreeUtil;
import cc.mrbird.febs.system.dao.ArticleTagMapper;
import cc.mrbird.febs.system.domain.ArticleTag;
import cc.mrbird.febs.system.service.ArticleTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Slf4j
@Service("ArticleTagService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ArticleTagServiceImpl extends BaseService<ArticleTag> implements ArticleTagService {

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public Map<String, Object> findArticleTags(QueryRequest request, ArticleTag articleTag) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<ArticleTag> articleTags = findArticleTags(articleTag, request);
            List<Tree<ArticleTag>> trees = new ArrayList<>();
            buildTrees(trees, articleTags);
            Tree<ArticleTag> articleTagTree = TreeUtil.build(trees);

            result.put("rows", articleTagTree);
            result.put("total", articleTags.size());
        } catch (Exception e) {
            log.error("获取部门列表失败", e);
            result.put("rows", null);
            result.put("total", 0);
        }
        return result;
    }

    @Override
    public List<ArticleTag> findArticleTags(ArticleTag articleTag, QueryRequest request) {
        Example example = new Example(ArticleTag.class);
        Example.Criteria criteria = example.createCriteria();
//        if (StringUtils.isNotBlank(articleTag.getArticleTagName()))
//            criteria.andCondition("articleTag_name=", articleTag.getArticleTagName());
//
//        if (StringUtils.isNotBlank(articleTag.getCreateTimeFrom()) && StringUtils.isNotBlank(articleTag.getCreateTimeTo())) {
//            criteria.andCondition("date_format(CREATE_TIME,'%Y-%m-%d') >=", articleTag.getCreateTimeFrom());
//            criteria.andCondition("date_format(CREATE_TIME,'%Y-%m-%d') <=", articleTag.getCreateTimeTo());
//        }
        FebsUtil.handleSort(request, example, "order_num");
        return this.selectByExample(example);
    }

    @Override
    @Transactional
    public void createArticleTag(ArticleTag articleTag) {
//        Long parentId = articleTag.getParentId();
//        if (parentId == null)
//            articleTag.setParentId(0L);
//        articleTag.setCreateTime(new Date());
        this.save(articleTag);
    }

    @Override
    @Transactional
    public void updateArticleTag(ArticleTag articleTag) {
        articleTag.setModifyTime(new Date());
        this.updateNotNull(articleTag);
    }

    @Override
    @Transactional
    public void deleteArticleTags(String[] articleTagIds) {
        Arrays.stream(articleTagIds).forEach(articleTagId -> this.articleTagMapper.deleteArticleTags(articleTagId));
    }

    private void buildTrees(List<Tree<ArticleTag>> trees, List<ArticleTag> articleTags) {
        articleTags.forEach(articleTag -> {
            Tree<ArticleTag> tree = new Tree<>();
            tree.setId(articleTag.getTagId().toString());
            tree.setKey(tree.getId());
//            tree.setParentId(articleTag.getParentId().toString());
            tree.setText(articleTag.getName());
            tree.setCreateTime(articleTag.getCreateTime());
            tree.setModifyTime(articleTag.getModifyTime());
            tree.setOrder(articleTag.getOrderNum());
            tree.setTitle(tree.getText());
            tree.setValue(tree.getId());
            trees.add(tree);
        });
    }
}
