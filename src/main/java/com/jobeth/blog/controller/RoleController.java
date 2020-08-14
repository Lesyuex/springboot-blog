package com.jobeth.blog.controller;


import com.jobeth.blog.po.Meta;
import com.jobeth.blog.po.Role;
import com.jobeth.blog.vo.JsonResultVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author Jobeth
 * @since 2020-06-30
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    /**
     * 根据id 查询
     *
     * @param id id
     * @return 查询结果
     */
    @GetMapping("/get/{id}")
    public JsonResultVO<Role> get(@PathVariable Integer id) {
        return new JsonResultVO<>();
    }

    /**
     * 保存
     *
     * @param role role
     * @return 保存结果
     */
    @PostMapping("/save")
    public JsonResultVO<Object> save(@RequestBody Role role) {
        return new JsonResultVO<>();
    }

    /**
     * 更新
     *
     * @param role role
     * @return 更新结果
     */
    @PutMapping("/update")
    public JsonResultVO<Object> update(@RequestBody  Role role) {
        return new JsonResultVO<>();
    }

    /**
     * 根据id删除
     *
     * @param id id
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public JsonResultVO<Meta> delete(@PathVariable Integer id) {
        return new JsonResultVO<>();
    }

    /**
     * 列表查询分页查询
     *
     * @param role role
     * @return 列表数据
     */
    @GetMapping("/list")
    public JsonResultVO<List<Role>> list(@RequestBody  Role role) {
        return new JsonResultVO<>();
    }
}
