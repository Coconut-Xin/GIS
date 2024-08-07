package com.ssj.gis4.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 【请填写功能名称】对象 cluster
 *
 * @author ruoyi
 * @date 2024-06-06
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Cluster {

    /**
     * 状态编号
     */
    private Long id;

    /**
     * 集群编号
     */
    private String clusterId;

    /**
     * 集群名称
     */
    private String clusterName;

    /**
     * 时间戳
     */
    private Double createTime;

    /**
     * 任务编号
     */
    private String taskId;

    /**
     * 节点组成
     */
    private List<String> nodesList;

    /**
     * 节点个数
     */
    private int nodesNumber;

    /**
     * 集群状态：
     * 0：集群被攻击状态
     * 1：正常飞行状态
     */
    private String clusterStatus;

    /**
     * 集群内因干扰而通信中断的节点数
     */
    private int lostNodesNumber;

    /**
     * 集群内因损毁而通信中断的节点数
     */
    private int damagedNodesNumber;

    /**
     * 集群与指挥站通信连通情况
     * 1：通信连通;
     * 0：通信丢失
     */
    private String communicationStationStatus;

    /**
     * 集群干扰状态
     * 0：未干扰
     * 1：被干扰
     */
    private String electronicInterferenceStatus;

    /**
     * 集群对应任务完成标示
     * 0：未完成
     * 1：完成
     */
    private String taskFinishFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public Double getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Double createTime) {
        this.createTime = createTime;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<String> getNodesList() {
        return nodesList;
    }

    public void setNodesList(List<String> nodesList) {
        this.nodesList = nodesList;
    }

    public int getNodesNumber() {
        return nodesNumber;
    }

    public void setNodesNumber(int nodesNumber) {
        this.nodesNumber = nodesNumber;
    }

    public String getClusterStatus() {
        return clusterStatus;
    }

    public void setClusterStatus(String clusterStatus) {
        this.clusterStatus = clusterStatus;
    }

    public int getLostNodesNumber() {
        return lostNodesNumber;
    }

    public void setLostNodesNumber(int lostNodesNumber) {
        this.lostNodesNumber = lostNodesNumber;
    }

    public int getDamagedNodesNumber() {
        return damagedNodesNumber;
    }

    public void setDamagedNodesNumber(int damagedNodesNumber) {
        this.damagedNodesNumber = damagedNodesNumber;
    }

    public String getCommunicationStationStatus() {
        return communicationStationStatus;
    }

    public void setCommunicationStationStatus(String communicationStationStatus) {
        this.communicationStationStatus = communicationStationStatus;
    }

    public String getElectronicInterferenceStatus() {
        return electronicInterferenceStatus;
    }

    public void setElectronicInterferenceStatus(String electronicInterferenceStatus) {
        this.electronicInterferenceStatus = electronicInterferenceStatus;
    }

    public String getTaskFinishFlag() {
        return taskFinishFlag;
    }

    public void setTaskFinishFlag(String taskFinishFlag) {
        this.taskFinishFlag = taskFinishFlag;
    }
}

