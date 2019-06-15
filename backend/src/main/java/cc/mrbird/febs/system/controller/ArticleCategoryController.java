package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.system.domain.ArticleCategory;
import cc.mrbird.febs.system.service.ArticleCategoryService;
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
@RequestMapping("articlecategory")
public class ArticleCategoryController {
    private String message;

    @Autowired
    private ArticleCategoryService articleCategoryService;

    @GetMapping
    public Map<String, Object> articleCategoryList(QueryRequest request, ArticleCategory articleCategory) {
        return this.articleCategoryService.findArticleCategorys(request, articleCategory);
    }

    @Log("新增部门")
    @PostMapping
    @RequiresPermissions("articleCategory:add")
    public void addArticleCategory(@Valid ArticleCategory articleCategory) throws FebsException {
        try {
            this.articleCategoryService.createArticleCategory(articleCategory);
        } catch (Exception e) {
            message = "新增失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("删除部门")
    @DeleteMapping("/{articleCategoryIds}")
    @RequiresPermissions("articleCategory:delete")
    public void deleteArticleCategorys(@NotBlank(message = "{required}") @PathVariable String articleCategoryIds) throws FebsException {
        try {
            String[] ids = articleCategoryIds.split(",");
            this.articleCategoryService.deleteArticleCategorys(ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("修改部门")
    @PutMapping
    @RequiresPermissions("articleCategory:update")
    public void updateArticleCategory(@Valid ArticleCategory articleCategory) throws FebsException {
        try {
            this.articleCategoryService.updateArticleCategory(articleCategory);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("articleCategory:export")
    public void export(ArticleCategory articleCategory, QueryRequest request, HttpServletResponse response) throws FebsException {
        try {
            List<ArticleCategory> articleCategorys = this.articleCategoryService.findArticleCategorys(articleCategory, request);
            ExcelKit.$Export(ArticleCategory.class, response).downXlsx(articleCategorys, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
