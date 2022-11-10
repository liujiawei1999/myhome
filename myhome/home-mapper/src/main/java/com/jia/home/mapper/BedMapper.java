package com.jia.home.mapper;

import com.jia.home.model.Bed;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jiawei
 * @since 2022-04-27
 */
public interface BedMapper extends BaseMapper<Bed> {

    boolean updateStatus(Integer integer);

    boolean updateBed(Bed bed);
}
