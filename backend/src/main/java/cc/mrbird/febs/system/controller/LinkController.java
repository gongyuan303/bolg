package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.system.domain.Link;
import cc.mrbird.febs.system.service.LinkService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Slf4j
@Validated
@RestController
@RequestMapping("blog/link")
public class LinkController {

    private String message;

    @Autowired
    private LinkService linkService;
    /**
     * 根据ID查询用户
     * @param id
     * @return
     */
    @ApiOperation(value="获取链接列表信息", notes="根据查询参数获取链接列表信息")
    @GetMapping
    public Map<String, Object> linkList(QueryRequest request, Link link) {
        return this.linkService.findLinks(request, link);
    }

    @ApiOperation(value="新增链接信息", notes="新增链接信息")
    @Log("新增链接")
    @PostMapping
    @RequiresPermissions("link:add")
    public void addLink(@Valid Link link) throws FebsException {
        try {
            this.linkService.createLink(link);
        } catch (Exception e) {
            message = "新增链接失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @ApiOperation(value="删除链接信息", notes="根据删除链接集合删除信息")
    @Log("删除链接")
    @DeleteMapping("/{linkIds}")
    @RequiresPermissions("link:delete")
    public void deleteLinks(@NotBlank(message = "{required}") @PathVariable String linkIds) throws FebsException {
        try {
            String[] ids = linkIds.split(",");
            this.linkService.deleteLinks(ids);
        } catch (Exception e) {
            message = "删除链接失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @ApiOperation(value="修改链接信息", notes="修改信息")
    @Log("修改链接")
    @PutMapping
    @RequiresPermissions("link:update")
    public void updateLink(@Valid Link link) throws FebsException {
        try {
            this.linkService.updateLink(link);
        } catch (Exception e) {
            message = "修改链接失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @ApiOperation(value="导出链接信息", notes="导出链接信息")
    @PostMapping("excel")
    @RequiresPermissions("link:export")
    public void export(Link link, QueryRequest request, HttpServletResponse response) throws FebsException {
        try {
            List<Link> links = this.linkService.findLinks(link, request);
            ExcelKit.$Export(Link.class, response).downXlsx(links, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
