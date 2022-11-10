package com.jia.home.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jiawei
 * @since 2022-04-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_bed")
public class Bed implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 院名
     */
    private String community;

    /**
     * 楼栋
     */
    private String building;

    /**
     * 楼层/单元
     */
    private String floor;

    /**
     * 房间号
     */
    private String root;

    /**
     * 床位号
     */
    private String bedId;

    /**
     * 客户姓名
     */
    private String customName;

    private Integer customId;

    /**
     * 状态
     */
    private Integer bedStatus;

    /**
     * 入住时间
     */
    private Date startTime;

    /**
     * 退房时间
     */
    private Date endTime;


}
