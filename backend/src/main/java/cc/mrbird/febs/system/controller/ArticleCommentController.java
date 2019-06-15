package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.system.domain.ArticleComment;
import cc.mrbird.febs.system.service.ArticleCommentService;
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
@RequestMapping("articlecomment")
public class ArticleCommentController {
    private String message;

    @Autowired
    private ArticleCommentService articleCommentService;

    @GetMapping
    public Map<String, Object> articleCommentList(QueryRequest request, ArticleComment articleComment) {
        return this.articleCommentService.findArticleComments(request, articleComment);
    }

    @Log("新增部门")
    @PostMapping
    @RequiresPermissions("articleComment:add")
    public void addArticleComment(@Valid ArticleComment articleComment) throws FebsException {
        try {
            this.articleCommentService.createArticleComment(articleComment);
        } catch (Exception e) {
            message = "新增失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("删除部门")
    @DeleteMapping("/{articleCommentIds}")
    @RequiresPermissions("articleComment:delete")
    public void deleteArticleComments(@NotBlank(message = "{required}") @PathVariable String articleCommentIds) throws FebsException {
        try {
            String[] ids = articleCommentIds.split(",");
            this.articleCommentService.deleteArticleComments(ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("修改部门")
    @PutMapping
    @RequiresPermissions("articleComment:update")
    public void updateArticleComment(@Valid ArticleComment articleComment) throws FebsException {
        try {
            this.articleCommentService.updateArticleComment(articleComment);
        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("articleComment:export")
    public void export(ArticleComment articleComment, QueryRequest request, HttpServletResponse response) throws FebsException {
        try {
            List<ArticleComment> articleComments = this.articleCommentService.findArticleComments(articleComment, request);
            ExcelKit.$Export(ArticleComment.class, response).downXlsx(articleComments, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
