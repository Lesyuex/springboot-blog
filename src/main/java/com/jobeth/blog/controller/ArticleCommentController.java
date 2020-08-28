package com.jobeth.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jobeth.blog.common.enums.ResultEnum;
import com.jobeth.blog.common.exception.ServerException;
import com.jobeth.blog.common.utils.StringUtils;
import com.jobeth.blog.po.ArticleComment;
import com.jobeth.blog.service.ArticleCommentService;
import com.jobeth.blog.vo.JsonResultVO;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 文章评论表 前端控制器
 * </p>
 *
 * @author Jobeth
 * @since 2020-08-10
 */
@RestController
@RequestMapping("/article/comment")
public class ArticleCommentController {
    private final ArticleCommentService articleCommentService;

    public ArticleCommentController(ArticleCommentService articleCommentService) {
        this.articleCommentService = articleCommentService;
    }

    /**
     * 根据id 查询
     *
     * @param id id
     * @return 查询结果
     */
    @GetMapping("/get/{id}")
    public JsonResultVO<ArticleComment> get(@PathVariable Integer id) {
        ArticleComment articleComment = articleCommentService.getById(id);
        if (articleComment == null) {
            String exceptionMsg = "【 评论ID => " + id + " 不存在 】";
            throw new ServerException(ResultEnum.SERVER_NO_THIS_SOURCE, exceptionMsg);
        }
        return new JsonResultVO<>(articleComment);

    }

    /**
     * 保存
     *
     * @param articleComment articleComment
     * @return 保存结果
     */
    @PostMapping("/save")
    public JsonResultVO<Object> save(@RequestBody ArticleComment articleComment) {
        return articleCommentService.save(articleComment) ? new JsonResultVO<>() : new JsonResultVO<>(ResultEnum.FAIL);
    }

    /**
     * 更新
     *
     * @param articleComment articleComment
     * @return 更新结果
     */
    @PutMapping("/update")
    public JsonResultVO<Object> update(@RequestBody ArticleComment articleComment) {
        return articleCommentService.updateById(articleComment) ? new JsonResultVO<>() : new JsonResultVO<>(ResultEnum.FAIL);
    }

    /**
     * 根据id删除
     *
     * @param id id
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public JsonResultVO<ArticleComment> delete(@PathVariable Integer id) {
        return articleCommentService.removeById(id) ? new JsonResultVO<>() : new JsonResultVO<>(ResultEnum.FAIL);
    }

    /**
     * 列表查询分页查询
     *
     * @param articleComment articleComment
     * @return 列表数据
     */
    @GetMapping("/listByPage")
    public JsonResultVO<Object> listByPage(Page<ArticleComment> page, ArticleComment articleComment) {
        LambdaQueryWrapper<ArticleComment> queryWrapper = new QueryWrapper<ArticleComment>().lambda();
        if (StringUtils.notNullAndEmpty(articleComment.getArticleId().toString())) {
            queryWrapper.eq(ArticleComment::getArticleId, articleComment.getArticleId());
        }
        IPage<ArticleComment> pageInfo = articleCommentService.page(page, queryWrapper);
        return new JsonResultVO<>(pageInfo);
    }

}
