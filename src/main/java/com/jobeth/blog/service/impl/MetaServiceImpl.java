package com.jobeth.blog.service.impl;

import com.jobeth.blog.po.Meta;
import com.jobeth.blog.mapper.MetaMapper;
import com.jobeth.blog.service.MetaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author Jobeth
 * @since 2020-06-30
 */
@Service
public class MetaServiceImpl extends ServiceImpl<MetaMapper, Meta> implements MetaService {

}
