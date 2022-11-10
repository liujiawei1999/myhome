package com.jia.home.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jia.home.mapper.MenuMapper;
import com.jia.home.model.Menu;
import com.jia.home.utils.PageUtils;
import com.jia.home.utils.RespBean;
import com.jia.home.utils.Query;
import com.jia.home.utils.UUIDUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jiawei
 * @since 2022-03-08
 */
@Service
public class MenuService extends ServiceImpl<MenuMapper, Menu>{

    public PageUtils getAllByPage(Map<String, Object> params) {
        IPage<Menu> page = new Query<Menu>().getPage(params);
        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
        IPage<Menu> menuIPage = this.page(page, menuQueryWrapper);

        return new PageUtils(menuIPage);
    }

    public RespBean addMenu(Menu menu) {
        List<Menu> list = this.list(new QueryWrapper<Menu>()
                .eq("menu_name", menu.getMenuName()));
//                .or()
//                .eq("menu_url", menu.getMenuUrl()));
        if (list!=null && !list.isEmpty()){
            return RespBean.error("菜单已存在");
        }
        menu.setMenuCode(UUIDUtils.uuid());
        this.baseMapper.insert(menu);
        return RespBean.ok();
    }

    public List<Menu> listToTree() {
        List<Menu> menus = this.baseMapper.selectList(null);
        return menus.stream().filter(menu -> {
            return menu.getMenuParentId() == 0;
        }).peek(e -> {
            e.setChildren(getChildrenMenu(e, menus));
        }).sorted((s1,s2)->{
            return (s1.getMenuSort()==null?0:s1.getMenuSort()) - (s2.getMenuSort()==null?0:s2.getMenuSort());
                })
        .collect(Collectors.toList());
    }

    private List<Menu> getChildrenMenu(Menu e, List<Menu> menus) {
        List<Menu> collect = menus.stream().filter(menu -> {
            return Objects.equals(menu.getMenuParentId(), e.getMenuId());
        }).collect(Collectors.toList());
        if(collect.isEmpty()){
            return null;
        }
        return collect;
    }
}
