package cc.mrbird.febs.system.service;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.service.IService;
import cc.mrbird.febs.system.domain.Resume;

import java.util.List;
import java.util.Map;

public interface ResumeService extends IService<Resume> {

    Map<String, Object> findResumes(QueryRequest request, Resume resume);

    List<Resume> findResumes(Resume resume, QueryRequest request);

    void createResume(Resume resume);

    void updateResume(Resume resume);

    void deleteResumes(String[] resumeIds);
}
