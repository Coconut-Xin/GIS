package com.ssj.gis4.service.impl;

import com.ssj.gis4.domain.Cluster;
import com.ssj.gis4.domain.Node;
import com.ssj.gis4.mapper.ClusterMapper;

import com.ssj.gis4.mapper.NodeMapper;
import com.ssj.gis4.service.ClusterService;
import com.ssj.gis4.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ClusterServiceImpl implements ClusterService {
    @Autowired(required = false)
    private ClusterMapper clusterMapper;


    @Override
    public List<Cluster> clusterList() {
        List<Cluster> clusterlist = clusterMapper.selectClusterList();
        for(Cluster cluster:clusterlist){
            String clusterStatus = cluster.getClusterStatus();
            String communicationStationStatus = cluster.getCommunicationStationStatus();
            String electronicInterferenceStatus = cluster.getElectronicInterferenceStatus();
            String taskFinishFlag = cluster.getTaskFinishFlag();
            switch (clusterStatus){
                case "0" -> cluster.setClusterStatus("集群被攻击状态");
                case "1" -> cluster.setClusterStatus("集群正常飞行状态");
                default -> cluster.setClusterStatus("集群正常飞行状态");
            }
            switch (communicationStationStatus){
                case "0" -> cluster.setCommunicationStationStatus("正常通信");
                case "1" -> cluster.setCommunicationStationStatus("通信丢失");
                default -> cluster.setCommunicationStationStatus("正常通信");
            }
            switch (electronicInterferenceStatus){
                case "0" -> cluster.setElectronicInterferenceStatus("未干扰");
                case "1" -> cluster.setElectronicInterferenceStatus("被干扰");
                default -> cluster.setElectronicInterferenceStatus("未干扰");
            }
            switch (taskFinishFlag){
                case "0" -> cluster.setTaskFinishFlag("未完成");
                case "1" -> cluster.setTaskFinishFlag("完成");
                default -> cluster.setTaskFinishFlag("未完成");
            }
        }
        System.out.println(clusterlist);
        return clusterlist;

    }
}
