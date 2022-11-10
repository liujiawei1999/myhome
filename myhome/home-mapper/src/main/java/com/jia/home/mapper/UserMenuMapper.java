package com.jia.home.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jia.home.model.Menu;
import com.jia.home.model.UserMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jiawei
 * @since 2022-03-30
 */
public interface UserMenuMapper extends BaseMapper<UserMenu> {

    List<Menu> getMenuById(@Param(value = "id") Integer id);
}
