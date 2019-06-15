package cc.mrbird.febs.system.dao;

import cc.mrbird.febs.common.config.MyMapper;
import cc.mrbird.febs.system.domain.Resume;

public interface ResumeMapper extends MyMapper<Resume> {

    /**
     * 递归删除
     *
     * @param resumeId resumeId
     */
    void deleteResumes(String resumeId);
}
