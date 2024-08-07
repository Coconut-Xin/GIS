package com.ssj.gis4.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NodeNewData {
    /**
     * 状态编号
     */
    private Long id;
    /**
     * 无人设备型号
     */
    private String vehicleId;

    /**
     * 时间戳
     */
    private Double createTime;

    /**
     * 实体通信类型：
     * 1:数据发送节点；0:数据接收节点
     */
    private String entityCommType;

    /**
     * 实体ID（数据发送节点）
     */
    private String agentId;

    /**
     * 实体上级ID（数据接收节点）
     */
    private String upEntityID;

    /**
     * 通信时延，单位s
     */
    private Double delay;

    /**
     * 吞吐量Kbps
     */
    private Double throughout;

    /**
     * 数据丢包率
     */
    private Double pktLossRatio;

    /**
     * 通信情况 0断开；1正常通信
     */
    private String commState;

    /**
     * 数据优先级，1为最高优先级
     */
    private Integer dataLevel;



}
