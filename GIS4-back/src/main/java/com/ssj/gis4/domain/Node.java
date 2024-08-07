package com.ssj.gis4.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 【请填写功能名称】对象 node1
 *
 * @author ruoyi
 * @date 2024-06-06
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Node {

    /**
     * 状态编号
     */
    private Long id;
    /**
     * 无人设备型号
     */
    private String vehicleId;
    /**
     * 无人设备名称
     */
    private String vehicleName;
    /**
     * 时间戳
     */
    private Double createTime;

    /**
     * 实时速度（米/秒）
     */
    private Double speed;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 高度（千米）
     */
    private Double height;

    /**
     * 方向角
     */
    private Double directionAngle;

    /**
     * 俯仰角
     */
    private Double pitchAngle;

    /**
     * 滚转角
     */
    private Double rollAngle;

    /**
     * 剩余航程（千米）
     */
    private Double surplusVoyage;

    /**
     * 剩余时间（秒）
     */
    private Double surplusTime;

    /**
     * 剩余能量
     */
    private Double surplusEnergy;

    /**
     * 任务编号
     */
    private String taskId;

    /**
     * 所在集群
     */
    private String clusterId;

    /**
     * 通信数量
     */
    private int communicationsNumber;

    /**
     * 与该节点建立通信链路的所有设备id
     */
    private List<String> communicationNodes;

    /**
     * 通信情况0断开；1正常通信
     */
    private String communicationSituation;

    /**
     * 损伤情况
     * 0完好；1轻度毁伤；
     * 2重度毁伤；3摧毁
     */
    private String damageSituation;

    /**
     * 电子干扰状态 0未干扰；1被干扰
     */
    private String electronicInterferenceStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public Double getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Double createTime) {
        this.createTime = createTime;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getDirectionAngle() {
        return directionAngle;
    }

    public void setDirectionAngle(Double directionAngle) {
        this.directionAngle = directionAngle;
    }

    public Double getPitchAngle() {
        return pitchAngle;
    }

    public void setPitchAngle(Double pitchAngle) {
        this.pitchAngle = pitchAngle;
    }

    public Double getRollAngle() {
        return rollAngle;
    }

    public void setRollAngle(Double rollAngle) {
        this.rollAngle = rollAngle;
    }

    public Double getSurplusVoyage() {
        return surplusVoyage;
    }

    public void setSurplusVoyage(Double surplusVoyage) {
        this.surplusVoyage = surplusVoyage;
    }

    public Double getSurplusTime() {
        return surplusTime;
    }

    public void setSurplusTime(Double surplusTime) {
        this.surplusTime = surplusTime;
    }

    public Double getSurplusEnergy() {
        return surplusEnergy;
    }

    public void setSurplusEnergy(Double surplusEnergy) {
        this.surplusEnergy = surplusEnergy;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public int getCommunicationsNumber() {
        return communicationsNumber;
    }

    public void setCommunicationsNumber(int communicationsNumber) {
        this.communicationsNumber = communicationsNumber;
    }

    public List<String> getCommunicationNodes() {
        return communicationNodes;
    }

    public void setCommunicationNodes(List<String> communicationNodes) {
        this.communicationNodes = communicationNodes;
    }

    public String getCommunicationSituation() {
        return communicationSituation;
    }

    public void setCommunicationSituation(String communicationSituation) {
        this.communicationSituation = communicationSituation;
    }

    public String getDamageSituation() {
        return damageSituation;
    }

    public void setDamageSituation(String damageSituation) {
        this.damageSituation = damageSituation;
    }

    public String getElectronicInterferenceStatus() {
        return electronicInterferenceStatus;
    }

    public void setElectronicInterferenceStatus(String electronicInterferenceStatus) {
        this.electronicInterferenceStatus = electronicInterferenceStatus;
    }
}
