package com.jobeth.blog.controller;


import com.jobeth.blog.po.ArticleComment;
import com.jobeth.blog.vo.JsonResultVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 根据id 查询
     *
     * @param id id
     * @return 查询结果
     */
    @GetMapping("/get/{id}")
    public JsonResultVO<ArticleComment> get(@PathVariable Integer id) {
        return new JsonResultVO<>();
    }

    /**
     * 保存
     *
     * @param articleComment articleComment
     * @return 保存结果
     */
    @PostMapping("/save")
    public JsonResultVO<Object> save(@RequestBody ArticleComment articleComment) {
        return new JsonResultVO<>();
    }

    /**
     * 更新
     *
     * @param articleComment articleComment
     * @return 更新结果
     */
    @PutMapping("/update")
    public JsonResultVO<Object> update(@RequestBody ArticleComment articleComment) {
        return new JsonResultVO<>();
    }

    /**
     * 根据id删除
     *
     * @param id id
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public JsonResultVO<ArticleComment> delete(@PathVariable Integer id) {
        return new JsonResultVO<>();
    }

    /**
     * 列表查询分页查询
     *
     * @param articleComment articleComment
     * @return 列表数据
     */
    @GetMapping("/list")
    public JsonResultVO<List<ArticleComment>> list(@RequestBody ArticleComment articleComment) {
        return new JsonResultVO<>();
    }

}
