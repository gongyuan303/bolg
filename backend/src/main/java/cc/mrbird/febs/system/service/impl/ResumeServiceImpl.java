package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.domain.Tree;
import cc.mrbird.febs.common.service.impl.BaseService;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.utils.TreeUtil;
import cc.mrbird.febs.system.dao.ResumeMapper;
import cc.mrbird.febs.system.domain.Resume;
import cc.mrbird.febs.system.service.ResumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Slf4j
@Service("ResumeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ResumeServiceImpl extends BaseService<Resume> implements ResumeService {

    @Autowired
    private ResumeMapper resumeMapper;

    @Override
    public Map<String, Object> findResumes(QueryRequest request, Resume resume) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Resume> resumes = findResumes(resume, request);
            List<Tree<Resume>> trees = new ArrayList<>();
            buildTrees(trees, resumes);
            Tree<Resume> resumeTree = TreeUtil.build(trees);

            result.put("rows", resumeTree);
            result.put("total", resumes.size());
        } catch (Exception e) {
            log.error("获取部门列表失败", e);
            result.put("rows", null);
            result.put("total", 0);
        }
        return result;
    }

    @Override
    public List<Resume> findResumes(Resume resume, QueryRequest request) {
        Example example = new Example(Resume.class);
        Example.Criteria criteria = example.createCriteria();
//        if (StringUtils.isNotBlank(resume.getResumeName()))
//            criteria.andCondition("resume_name=", resume.getResumeName());
//
//        if (StringUtils.isNotBlank(resume.getCreateTimeFrom()) && StringUtils.isNotBlank(resume.getCreateTimeTo())) {
//            criteria.andCondition("date_format(CREATE_TIME,'%Y-%m-%d') >=", resume.getCreateTimeFrom());
//            criteria.andCondition("date_format(CREATE_TIME,'%Y-%m-%d') <=", resume.getCreateTimeTo());
//        }
        FebsUtil.handleSort(request, example, "order_num");
        return this.selectByExample(example);
    }

    @Override
    @Transactional
    public void createResume(Resume resume) {
//        Long parentId = resume.getParentId();
//        if (parentId == null)
//            resume.setParentId(0L);
//        resume.setCreateTime(new Date());
        this.save(resume);
    }

    @Override
    @Transactional
    public void updateResume(Resume resume) {
        resume.setModifyTime(new Date());
        this.updateNotNull(resume);
    }

    @Override
    @Transactional
    public void deleteResumes(String[] resumeIds) {
        Arrays.stream(resumeIds).forEach(resumeId -> this.resumeMapper.deleteResumes(resumeId));
    }

    private void buildTrees(List<Tree<Resume>> trees, List<Resume> resumes) {
        resumes.forEach(resume -> {
            Tree<Resume> tree = new Tree<>();
            tree.setId(resume.getResumeId().toString());
            tree.setKey(tree.getId());
//            tree.setParentId(resume.getParentId().toString());
//            tree.setText(resume.g());
            tree.setCreateTime(resume.getCreateTime());
            tree.setModifyTime(resume.getModifyTime());
            tree.setOrder(resume.getOrderNum());
            tree.setTitle(tree.getText());
            tree.setValue(tree.getId());
            trees.add(tree);
        });
    }
}
