package com.jobeth.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jobeth.blog.common.enums.ResultEnum;
import com.jobeth.blog.common.exception.ServerException;
import com.jobeth.blog.common.utils.JacksonUtil;
import com.jobeth.blog.common.utils.WebHttpUtil;
import com.jobeth.blog.po.Meta;
import com.jobeth.blog.service.MetaService;
import com.jobeth.blog.vo.JsonResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author Jobeth
 * @since 2020-08-10
 */
@RestController
@RequestMapping("/meta")
@Slf4j
public class MetaController extends BaseController {
    private final MetaService metaService;

    public MetaController(MetaService metaService) {
        this.metaService = metaService;
    }

    /**
     * 根据id 查询
     *
     * @param id id
     * @return 查询结果
     */
    @GetMapping("/get/{id}")
    public JsonResultVO<Meta> get(@PathVariable Integer id) {
        Meta meta = metaService.getById(id);
        if (meta == null) {
            String exceptionMsg = "【 标签ID => " + id + " 不存在 】";
            throw new ServerException(ResultEnum.SERVER_NO_THIS_SOURCE, exceptionMsg);
        }
        return new JsonResultVO<>(meta);
    }

    /**
     * 保存
     *
     * @param meta meta
     * @return 保存结果
     */
    @PostMapping("/save")
    public JsonResultVO<Object> save(@RequestBody Meta meta) {
        return metaService.save(meta) ? new JsonResultVO<>() : new JsonResultVO<>(ResultEnum.ERROR);
    }

    /**
     * 更新
     *
     * @param meta meta
     * @return 更新结果
     */
    @PutMapping("/update")
    public JsonResultVO<Object> update(@RequestBody Meta meta) {
        return metaService.updateById(meta) ? new JsonResultVO<>() : new JsonResultVO<>(ResultEnum.ERROR);
    }

    /**
     * 根据id删除
     *
     * @param id id
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public JsonResultVO<Meta> delete(@PathVariable Integer id) {
        return metaService.removeById(id) ? new JsonResultVO<>() : new JsonResultVO<>(ResultEnum.ERROR);
    }

    /**
     * 列表查询分页查询
     *
     * @param meta meta
     * @return 列表数据
     */
    @GetMapping("/list")
    public JsonResultVO<Object> list(Page<Meta> page, Meta meta) {
        LambdaQueryWrapper<Meta> queryWrapper = new QueryWrapper<Meta>().lambda();
        if (meta.getId() != null) {
            queryWrapper.eq(Meta::getId, meta.getId());
        }
        if (meta.getName() != null) {
            queryWrapper.like(Meta::getName, meta.getName());
        }
        IPage<Meta> pageInfo = metaService.page(page, queryWrapper);
        return new JsonResultVO<>(pageInfo);
    }
}
