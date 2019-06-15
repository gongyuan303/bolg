package cc.mrbird.febs.system.service;


import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.service.IService;
import cc.mrbird.febs.system.domain.Link;

import java.util.List;
import java.util.Map;

public interface LinkService extends IService<Link> {

    Map<String, Object> findLinks(QueryRequest request, Link link);

    List<Link> findLinks(Link link, QueryRequest request);

    void createLink(Link link);

    void updateLink(Link link);

    void deleteLinks(String[] linkIds);
}
