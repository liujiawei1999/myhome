package com.jia.home.model;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("d_bed_building")
public class BedBuilding {
    public Integer id;
    public String building;
}
