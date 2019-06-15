package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.system.domain.Article;
import cc.mrbird.febs.system.service.ArticleService;
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
@RequestMapping("blogarticle")
public class BlogArticleController {
    private String message;

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public Map<String, Object> blogArticleList(QueryRequest request, Article blogArticle) {
        return this.articleService.findArticles(request, blogArticle);
    }

    @Log("新增部门")
    @PostMapping
    @RequiresPermissions("blogArticle:add")
    public void addArticle(@Valid Article blogArticle) throws FebsException {
        try {
            this.articleService.createArticle(blogArticle);
        } catch (Exception e) {
            message = "新增失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("删除部门")
    @DeleteMapping("/{blogArticleIds}")
    @RequiresPermissions("blogArticle:delete")
    public void deleteArticles(@NotBlank(message = "{required}") @PathVariable String blogArticleIds) throws FebsException {
        try {
            String[] ids = blogArticleIds.split(",");
            this.articleService.deleteArticles(ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("修改部门")
    @PutMapping
    @RequiresPermissions("blogArticle:update")
    public void updateArticle(@Valid Article blogArticle) throws FebsException {
        try {
            this.articleService.updateArticle(blogArticle);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("blogArticle:export")
    public void export(Article blogArticle, QueryRequest request, HttpServletResponse response) throws FebsException {
        try {
            List<Article> blogArticles = this.articleService.findArticles(blogArticle, request);
            ExcelKit.$Export(Article.class, response).downXlsx(blogArticles, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
