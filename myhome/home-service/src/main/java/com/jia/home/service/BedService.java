package com.jia.home.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jia.home.model.Bed;
import com.jia.home.mapper.BedMapper;
import com.jia.home.utils.JWStringUtils;
import com.jia.home.utils.RespBean;
import com.jia.home.utils.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jiawei
 * @since 2022-04-27
 */
@Service
public class BedService extends ServiceImpl<BedMapper, Bed> {

    @Autowired
    private BedMapper bedMapper;

    public RespPageBean getByPage(Map<String, Object> params) {
        String building = params.get("building").toString();
        String community = params.get("community").toString();
        String search = params.get("searched").toString();
        String page = (String) params.get("page");
        String limit = (String) params.get("limit");
        QueryWrapper<Bed> wrapper = new QueryWrapper<Bed>()
                .eq(JWStringUtils.isNotEmpty(building),"building",building)
                .eq(JWStringUtils.isNotEmpty(community),"community",community)
                .and(JWStringUtils.isNotEmpty(search),
                        e->e.eq("custom_name",search)
                        .or()
                        .eq("custom_id",search));
        if (JWStringUtils.isEmpty(limit)){
            List<Bed> beds = baseMapper.selectList(wrapper);
            return new RespPageBean((long) beds.size(),beds);
        }
        Page<Bed> bedPage = bedMapper.selectPage(new Page<>(Integer.parseInt(page), Integer.parseInt(limit)), wrapper);
        return new RespPageBean(bedPage.getTotal(),bedPage.getRecords());
    }

    public RespBean updateStatus(Integer integer) {
        boolean updateRes =  bedMapper.updateStatus(integer);
        if (updateRes){
            return RespBean.ok();
        }
        return RespBean.error("插入错误!");
    }

    public RespBean updateBed(Bed bed) {
        boolean updateRes =  bedMapper.updateBed(bed);
        return RespBean.ok();
    }
}
