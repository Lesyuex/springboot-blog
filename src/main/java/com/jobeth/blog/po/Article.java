package com.jobeth.blog.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author Jobeth
 * @since 2020-08-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     *  文章标题
     */
    private String title;

    /**
     *  url地址
     */
    private String slug;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 作者ID
     */
    private Integer authorId;

    /**
     * 文章类型
     */
    private String type;

    /**
     * 分类
     */
    private String categories;

    /**
     * 缩略图地址
     */
    @TableField("thumbImg")
    private String thumbImg;

    /**
     * 文章点击量
     */
    private Integer hits;

    /**
     * 评论数量
     */
    private Integer commentsNum;

    /**
     * 允许评论
     */
    private Integer allowComment;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 文章状态
     */
    private Integer status;


}
