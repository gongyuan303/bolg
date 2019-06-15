package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.domain.Tree;
import cc.mrbird.febs.common.service.impl.BaseService;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.utils.TreeUtil;
import cc.mrbird.febs.system.dao.ArticleCommentMapper;
import cc.mrbird.febs.system.domain.ArticleComment;
import cc.mrbird.febs.system.service.ArticleCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Slf4j
@Service("ArticleCommentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ArticleCommentServiceImpl extends BaseService<ArticleComment> implements ArticleCommentService {

    @Autowired
    private ArticleCommentMapper articleCommentMapper;

    @Override
    public Map<String, Object> findArticleComments(QueryRequest request, ArticleComment articleComment) {
        Map<String, Object> result = new HashMap<>();

        try {
            List<ArticleComment> articleComments = findArticleComments(articleComment, request);
            List<Tree<ArticleComment>> trees = new ArrayList<>();
            buildTrees(trees, articleComments);
            Tree<ArticleComment> articleCommentTree = TreeUtil.build(trees);

            result.put("rows", articleCommentTree);
            result.put("total", articleComments.size());
        } catch (Exception e) {
            log.error("获取部门列表失败", e);
            result.put("rows", null);
            result.put("total", 0);
        }
        return result;
    }

    @Override
    public List<ArticleComment> findArticleComments(ArticleComment articleComment, QueryRequest request) {
        Example example = new Example(ArticleComment.class);
        Example.Criteria criteria = example.createCriteria();
//        if (StringUtils.isNotBlank(articleComment.getArticleCommentName()))
//            criteria.andCondition("articleComment_name=", articleComment.getArticleCommentName());
//
//        if (StringUtils.isNotBlank(articleComment.getCreateTimeFrom()) && StringUtils.isNotBlank(articleComment.getCreateTimeTo())) {
//            criteria.andCondition("date_format(CREATE_TIME,'%Y-%m-%d') >=", articleComment.getCreateTimeFrom());
//            criteria.andCondition("date_format(CREATE_TIME,'%Y-%m-%d') <=", articleComment.getCreateTimeTo());
//        }
        FebsUtil.handleSort(request, example, "order_num");
        return this.selectByExample(example);
    }

    @Override
    @Transactional
    public void createArticleComment(ArticleComment articleComment) {
//        Long parentId = articleComment.getParentId();
//        if (parentId == null)
//            articleComment.setParentId(0L);
//        articleComment.setCreateTime(new Date());
        this.save(articleComment);
    }

    @Override
    @Transactional
    public void updateArticleComment(ArticleComment articleComment) {
        articleComment.setModifyTime(new Date());
        this.updateNotNull(articleComment);
    }

    @Override
    @Transactional
    public void deleteArticleComments(String[] articleCommentIds) {
        Arrays.stream(articleCommentIds).forEach(articleCommentId -> this.articleCommentMapper.deleteArticleComments(articleCommentId));
    }

    private void buildTrees(List<Tree<ArticleComment>> trees, List<ArticleComment> articleComments) {
        articleComments.forEach(articleComment -> {
            Tree<ArticleComment> tree = new Tree<>();
            tree.setId(articleComment.getMsgId().toString());
            tree.setKey(tree.getId());
//            tree.setParentId(articleComment.getParentId().toString());
            tree.setText(articleComment.getName());
            tree.setCreateTime(articleComment.getCreateTime());
            tree.setModifyTime(articleComment.getModifyTime());
            tree.setOrder(articleComment.getOrderNum());
            tree.setTitle(tree.getText());
            tree.setValue(tree.getId());
            trees.add(tree);
        });
    }
}
