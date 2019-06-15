package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.system.domain.ArticleTag;
import cc.mrbird.febs.system.service.ArticleTagService;
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

@Slf4j
@Validated
@RestController
@RequestMapping("articletag")
public class ArticleTagController {
    private String message;

    @Autowired
    private ArticleTagService articleTagService;

    @GetMapping
    public Map<String, Object> articleTagList(QueryRequest request, ArticleTag articleTag) {
        return this.articleTagService.findArticleTags(request, articleTag);
    }

    @Log("新增部门")
    @PostMapping
    @RequiresPermissions("articleTag:add")
    public void addArticleTag(@Valid ArticleTag articleTag) throws FebsException {
        try {
            this.articleTagService.createArticleTag(articleTag);
        } catch (Exception e) {
            message = "新增失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("删除部门")
    @DeleteMapping("/{articleTagIds}")
    @RequiresPermissions("articleTag:delete")
    public void deleteArticleTags(@NotBlank(message = "{required}") @PathVariable String articleTagIds) throws FebsException {
        try {
            String[] ids = articleTagIds.split(",");
            this.articleTagService.deleteArticleTags(ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("修改部门")
    @PutMapping
    @RequiresPermissions("articleTag:update")
    public void updateArticleTag(@Valid ArticleTag articleTag) throws FebsException {
        try {
            this.articleTagService.updateArticleTag(articleTag);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("articleTag:export")
    public void export(ArticleTag articleTag, QueryRequest request, HttpServletResponse response) throws FebsException {
        try {
            List<ArticleTag> articleTags = this.articleTagService.findArticleTags(articleTag, request);
            ExcelKit.$Export(ArticleTag.class, response).downXlsx(articleTags, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
