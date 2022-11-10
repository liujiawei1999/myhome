package com.jia.home.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jia.home.mapper.BedBuildingMapper;
import com.jia.home.mapper.BedCommunityMapper;
import com.jia.home.model.BedBuilding;
import com.jia.home.model.BedCommunity;
import com.jia.home.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DictionaryService {

    @Autowired
    private BedBuildingMapper buildingMapper;
    @Autowired
    private BedCommunityMapper bedCommunityMapper;

    public RespBean getBedBuilding() {
        List<BedBuilding> bedBuildings = buildingMapper.selectList(null);
        List<String> collect = bedBuildings.stream().map(BedBuilding::getBuilding).collect(Collectors.toList());
        return RespBean.ok(collect);
    }

    public RespBean getCommunity() {
        List<BedCommunity> bedCommunities = bedCommunityMapper.selectList(null);
        List<String> collect = bedCommunities.stream().map(BedCommunity::getCommunity).collect(Collectors.toList());
        return RespBean.ok(bedCommunities);
    }
}
