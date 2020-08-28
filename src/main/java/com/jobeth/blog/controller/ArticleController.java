package com.jobeth.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jobeth.blog.common.enums.ResultEnum;
import com.jobeth.blog.common.exception.ServerException;
import com.jobeth.blog.common.utils.StringUtils;
import com.jobeth.blog.po.Article;
import com.jobeth.blog.service.ArticleService;
import com.jobeth.blog.vo.JsonResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author Jobeth
 * @since 2020-08-10
 */
@RestController
@RequestMapping("/article")
@Slf4j
public class ArticleController extends BaseController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 根据id 查询
     *
     * @param id id
     * @return 查询结果
     */
    @GetMapping("/get/{id}")
    public JsonResultVO<Article> get(@PathVariable Integer id) {
        Article article = articleService.getById(id);
        if (article == null) {
            String exceptionMsg = "【 文章ID => " + id + " 不存在 】";
            throw new ServerException(ResultEnum.SERVER_NO_THIS_SOURCE, exceptionMsg);
        }
        return new JsonResultVO<>(article);
    }

    /**
     * 保存
     *
     * @param article article
     * @return 保存结果
     */
    @PostMapping("/save")
    public JsonResultVO<Object> save(@RequestBody Article article) {
        return articleService.save(article) ? new JsonResultVO<>() : new JsonResultVO<>(ResultEnum.FAIL);
    }

    /**
     * 更新
     *
     * @param article article
     * @return 更新结果
     */
    @PutMapping("/update")
    public JsonResultVO<Object> update(@RequestBody Article article) {
        return articleService.updateById(article) ? new JsonResultVO<>() : new JsonResultVO<>(ResultEnum.FAIL);
    }

    /**
     * 根据id删除
     *
     * @param id id
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public JsonResultVO<Article> delete(@PathVariable Integer id) {
        return articleService.removeById(id) ? new JsonResultVO<>() : new JsonResultVO<>(ResultEnum.FAIL);
    }

    /**
     * 列表查询分页查询
     *
     * @param page    page
     * @param article article
     * @return 列表数据
     */
    @GetMapping("/listByPage")
    public JsonResultVO<Object> listByPage(Page<Article> page, Article article) {
        LambdaQueryWrapper<Article> queryWrapper = new QueryWrapper<Article>().lambda();
        try {
            if (StringUtils.notNullAndEmpty(article.getTitle())) {
                queryWrapper.like(Article::getTitle, article.getTitle());
            }
            IPage<Article> pageData = articleService.page(page, queryWrapper);
            return new JsonResultVO<>(pageData);
        } catch (Exception e) {
            log.error("【 Article 列表查询分页查询 发生错误 】", e);
            throw new ServerException(ResultEnum.INTERNAL_SERVER_ERROR);
        }
    }

}
