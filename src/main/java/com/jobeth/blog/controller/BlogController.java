package com.jobeth.blog.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jobeth.blog.common.Constant;
import com.jobeth.blog.common.enums.ResultEnum;
import com.jobeth.blog.common.exception.ServerException;
import com.jobeth.blog.common.helper.RedisHelper;
import com.jobeth.blog.common.utils.JsonUtil;
import com.jobeth.blog.po.Blog;
import com.jobeth.blog.service.BlogService;
import com.jobeth.blog.vo.JsonResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 博客表 前端控制器
 * </p>
 *
 * @author Jobeth
 * @since 2020-06-30
 */
@RestController
@RequestMapping("/blog")
@Slf4j
public class BlogController extends BaseController {
    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    /**
     * 根据ID 获取文章
     *
     * @param id 文章id
     * @return 获取结果
     */
    @GetMapping("/get/{id}")
    public JsonResultVO<Blog> get(@PathVariable Long id) {
        Blog blog = blogService.getById(id);
        if (blog == null) {
            String exceptionMsg = "【 文章ID => " + id + " 不存在 】";
            throw new ServerException(ResultEnum.SERVER_NO_THIS_SOURCE, exceptionMsg);
        }
        return new JsonResultVO<>(blog);
    }

    /**
     * 新增
     *
     * @param blog blog
     * @return 结果
     */
    @PostMapping("/save")
    public JsonResultVO<Object> save(@RequestBody Blog blog) {
        log.info(JsonUtil.objectToJson(blog));
        return new JsonResultVO<>();
    }

    @PutMapping("/updateById")
    public JsonResultVO<Object> updateById(@RequestBody Blog blog) {
        RedisHelper.sout();
        //根据id进行更新，没有传值的属性就不会更新
        boolean success = blogService.updateById(blog);

        if (success) {
            String key = Constant.BLOG_REDIS_KEY_PREFIX + blog.getId();
            RedisHelper.remove(key);
            Blog blog1 = blogService.getById(blog.getId());
            String s = JsonUtil.objectToJson(blog1);
            log.info(s);
            return new JsonResultVO<>();
        } else {
            return new JsonResultVO<>(ResultEnum.ERROR);
        }
    }

    /**
     * 更新
     *
     * @param blog blog
     * @return 结果
     */
    @PutMapping("/update")
    public JsonResultVO<Object> update(@RequestBody Blog blog) {
        log.info(JsonUtil.objectToJson(blog));
        //方法1
        //根据id更新title字段
        LambdaUpdateWrapper<Blog> update = new UpdateWrapper<Blog>().lambda()
                .eq(Blog::getId, blog.getId());
        //不写set时传入entity
        boolean success = blogService.update(blog, update);

        if (success) {
            String key = Constant.BLOG_REDIS_KEY_PREFIX + blog.getId();
            RedisHelper.remove(key);
            Blog blog1 = blogService.getById(blog.getId());
            String s = JsonUtil.objectToJson(blog1);
            log.info(s);
            return new JsonResultVO<>();
        } else {
            return new JsonResultVO<>(ResultEnum.ERROR);
        }
    }

    /**
     * 根据ID 删除文章
     *
     * @param id 文章id
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public JsonResultVO<Object> delete(@PathVariable Integer id) {
        return new JsonResultVO<>();
    }
}
