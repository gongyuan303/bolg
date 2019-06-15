package cc.mrbird.febs.system.dao;

import cc.mrbird.febs.common.config.MyMapper;
import cc.mrbird.febs.system.domain.Link;

import java.util.List;

public interface LinkMapper extends MyMapper<Link> {

	/**
	 * 递归删除部门
	 *
	 * @param deptId deptId
	 */
	void deleteLinks(String linkId);
}