package com.jia.home.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author jiawei
 * @since 2022-03-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "menu_id", type = IdType.AUTO)
    private Integer menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单code

     */
    private String menuCode;

    private Integer menuParentId;

    /**
     * 菜单地址
     */
    private String menuUrl;

    /**
     * 菜单类型
     */
    private Integer menuType;

    private Integer menuSort;

    private Integer menuLevel;

    private String menuPath;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 描述
     */
    private String menuDes;

    private Integer menuIsDeleted;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableField(exist = false)
    private List<Menu> children;

}
