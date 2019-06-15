package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.domain.Tree;
import cc.mrbird.febs.common.service.impl.BaseService;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.utils.TreeUtil;
import cc.mrbird.febs.system.dao.LinkMapper;
import cc.mrbird.febs.system.domain.Link;
import cc.mrbird.febs.system.service.LinkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Slf4j
@Service("LinkService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LinkServiceImpl extends BaseService<Link> implements LinkService {

    @Autowired
    private LinkMapper linkMapper;

    @Override
    public Map<String, Object> findLinks(QueryRequest request, Link link) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Link> links = findLinks(link, request);
            List<Tree<Link>> trees = new ArrayList<>();
            buildTrees(trees, links);
            Tree<Link> linkTree = TreeUtil.build(trees);

            result.put("rows", linkTree);
            result.put("total", links.size());
        } catch (Exception e) {
            log.error("获取链接列表失败", e);
            result.put("rows", null);
            result.put("total", 0);
        }
        return result;
    }

    @Override
    public List<Link> findLinks(Link link, QueryRequest request) {
        Example example = new Example(Link.class);
        Example.Criteria criteria = example.createCriteria();
//        if (StringUtils.isNotBlank(link.getLinkName()))
//            criteria.andCondition("link_name=", link.getLinkName());
//
//        if (StringUtils.isNotBlank(link.getCreateTimeFrom()) && StringUtils.isNotBlank(link.getCreateTimeTo())) {
//            criteria.andCondition("date_format(CREATE_TIME,'%Y-%m-%d') >=", link.getCreateTimeFrom());
//            criteria.andCondition("date_format(CREATE_TIME,'%Y-%m-%d') <=", link.getCreateTimeTo());
//        }
        FebsUtil.handleSort(request, example, "order_num");
        return this.selectByExample(example);
    }

    @Override
    @Transactional
    public void createLink(Link link) {
//        link.setCreateTime(new Date());
        this.save(link);
    }

    @Override
    @Transactional
    public void updateLink(Link link) {
//        link.setModifyTime(new Date());
        this.updateNotNull(link);
    }

    @Override
    @Transactional
    public void deleteLinks(String[] linkIds) {
        Arrays.stream(linkIds).forEach(linkId -> this.linkMapper.deleteLinks(linkId));
    }

    private void buildTrees(List<Tree<Link>> trees, List<Link> links) {
        links.forEach(link -> {
            Tree<Link> tree = new Tree<>();
            tree.setId(link.getLinkId().toString());
            tree.setKey(tree.getId());
            tree.setText(link.getLinkName());
            tree.setCreateTime(link.getCreateTime());
            tree.setModifyTime(link.getModifyTime());
            tree.setOrder(link.getOrderNum());
            tree.setTitle(tree.getText());
            tree.setValue(tree.getId());
            trees.add(tree);
        });
    }
}
