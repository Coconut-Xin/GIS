package com.ssj.gis4.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 【请填写功能名称】对象 task
 *
 * @author ruoyi
 * @date 2024-06-06
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    /**
     * 状态编号
     */
    private Long id;

    /**
     * 任务编号
     */
    private String taskId;

    /**
     * 时间戳
     */
    private Double createTime;

    /**
     * 任务类型
     * 0：侦察任务；
     * 1：打击任务；
     * 2：察打一体任务
     */
    private String taskType;

    /**
     * 任务信息描述
     */
    private String taskDescription;

    /**
     * 节点组成
     */
    private List<String> nodesList;

    /**
     * 节点类型：
     * 0：扑翼无人机；
     * 1：旋翼无人机；
     * 2：无人车
     */
    private List<String> nodesType;

    /**
     * 集群组成
     */
    private List<String> clustersList;

    /**
     * 执行任务的集群个数
     */
    private int clusterNumber;

    /**
     * 任务目标地点经度
     */
    private BigDecimal taskLocationLongitude;

    /**
     * 任务目标地点纬度
     */
    private BigDecimal taskLocationLatitude;

    /**
     * 任务目标地点高度
     */
    private Double taskLocationAltitude;

    /**
     * 区域半径
     */
    private Double taskAreaRadius;

    /**
     * 任务开始时间
     */
    private Double startTime;

    /**
     * 最大完成时间
     */
    private Double completionTime;

    /**
     * 实际开始时间
     */
    private Double actualStartTime;

    /**
     * 实际完成时间
     */
    private Double actualCompletionTime;

    /**
     * 任务完成标示
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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Double getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Double createTime) {
        this.createTime = createTime;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public List<String> getNodesList() {
        return nodesList;
    }

    public void setNodesList(List<String> nodesList) {
        this.nodesList = nodesList;
    }

    public List<String> getNodesType() {
        return nodesType;
    }

    public void setNodesType(List<String> nodesType) {
        this.nodesType = nodesType;
    }

    public List<String> getClustersList() {
        return clustersList;
    }

    public void setClustersList(List<String> clustersList) {
        this.clustersList = clustersList;
    }

    public int getClusterNumber() {
        return clusterNumber;
    }

    public void setClusterNumber(int clusterNumber) {
        this.clusterNumber = clusterNumber;
    }

    public BigDecimal getTaskLocationLongitude() {
        return taskLocationLongitude;
    }

    public void setTaskLocationLongitude(BigDecimal taskLocationLongitude) {
        this.taskLocationLongitude = taskLocationLongitude;
    }

    public BigDecimal getTaskLocationLatitude() {
        return taskLocationLatitude;
    }

    public void setTaskLocationLatitude(BigDecimal taskLocationLatitude) {
        this.taskLocationLatitude = taskLocationLatitude;
    }

    public Double getTaskLocationAltitude() {
        return taskLocationAltitude;
    }

    public void setTaskLocationAltitude(Double taskLocationAltitude) {
        this.taskLocationAltitude = taskLocationAltitude;
    }

    public Double getTaskAreaRadius() {
        return taskAreaRadius;
    }

    public void setTaskAreaRadius(Double taskAreaRadius) {
        this.taskAreaRadius = taskAreaRadius;
    }

    public Double getStartTime() {
        return startTime;
    }

    public void setStartTime(Double startTime) {
        this.startTime = startTime;
    }

    public Double getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(Double completionTime) {
        this.completionTime = completionTime;
    }

    public Double getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(Double actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public Double getActualCompletionTime() {
        return actualCompletionTime;
    }

    public void setActualCompletionTime(Double actualCompletionTime) {
        this.actualCompletionTime = actualCompletionTime;
    }

    public String getTaskFinishFlag() {
        return taskFinishFlag;
    }

    public void setTaskFinishFlag(String taskFinishFlag) {
        this.taskFinishFlag = taskFinishFlag;
    }
}

