package com.ssj.gis4.service.impl;

import com.ssj.gis4.domain.Node;
import com.ssj.gis4.domain.NodeNewData;
import com.ssj.gis4.mapper.NodeMapper;
import com.ssj.gis4.mapper.NodeNewDataMapper;
import com.ssj.gis4.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2024-06-06
 */
@Service
public class NodeServiceImpl implements NodeService {
    private List<Node> nodeList = new ArrayList<>();

    @Autowired(required = false)
    private NodeMapper nodeMapper;

    @Autowired
    private NodeNewDataMapper nodeNewDataMapper;


    public List<Node> nodelist() {
        return nodeMapper.nodeList();
    }


    @Override
    public List<Node> selectNowNodeList() {
        nodeList.add(nodeMapper.selectNowNode());
        return nodeList;
    }


    //改了调用的函数！！  2024年7月17日 02点19分 ：测试当前ROS
    @Override
    public List<Node> getLatestNodeData() {
        List<Node> nodelist = new ArrayList<>();
        for (int i = 1; i <= 16; i++) {
            String nodeId = String.valueOf(i);
            Node node = nodeMapper.getDataByNodeId(nodeId);
//            Node node = nodeMapper.getLatestNodeDataByNodeId(nodeId);
//            Node node = nodeMapper.getDataByNodeIdTestRos(nodeId);
            String communicationSituation = node.getCommunicationSituation();
            String damageSituation = node.getDamageSituation();
            String electronicInterferenceStatus = node.getElectronicInterferenceStatus();
            switch (communicationSituation) {
                case "0" -> node.setCommunicationSituation("通信断开");
                case "1" -> node.setCommunicationSituation("正常通信");
                default -> node.setCommunicationSituation("正常通信");
            }
            switch (damageSituation) {
                case "0" -> node.setDamageSituation("设备完好");
                case "1" -> node.setDamageSituation("轻度损伤");
                case "2" -> node.setDamageSituation("重度损伤");
                case "3" -> node.setDamageSituation("设备损毁");
                default -> node.setDamageSituation("设备完好");
            }
            switch (electronicInterferenceStatus) {
                case "0" -> node.setElectronicInterferenceStatus("未干扰");
                case "1" -> node.setElectronicInterferenceStatus("被干扰");
                default -> node.setElectronicInterferenceStatus("未干扰");
            }
            nodelist.add(node);
        }
        System.out.println(nodelist);
        return nodelist;
    }

    //    2024年7月17日 02点19分 ：测试当前ROS
    @Override
    public List<Node> getLatestNodeDataByNodeIdTestRos() {
        List<Node> nodelist = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            String nodeId = String.valueOf(i);
            Node node = nodeMapper.getDataByNodeIdTestRos(nodeId);
            String communicationSituation = node.getCommunicationSituation();
            String damageSituation = node.getDamageSituation();
            String electronicInterferenceStatus = node.getElectronicInterferenceStatus();
            switch (communicationSituation) {
                case "0" -> node.setCommunicationSituation("通信断开");
                case "1" -> node.setCommunicationSituation("正常通信");
                default -> node.setCommunicationSituation("正常通信");
            }
            switch (damageSituation) {
                case "0" -> node.setDamageSituation("设备完好");
                case "1" -> node.setDamageSituation("轻度损伤");
                case "2" -> node.setDamageSituation("重度损伤");
                case "3" -> node.setDamageSituation("设备损毁");
                default -> node.setDamageSituation("设备完好");
            }
            switch (electronicInterferenceStatus) {
                case "0" -> node.setElectronicInterferenceStatus("未干扰");
                case "1" -> node.setElectronicInterferenceStatus("被干扰");
                default -> node.setElectronicInterferenceStatus("未干扰");
            }
            nodelist.add(node);
        }
        System.out.println(nodelist);
        return nodelist;
    }

    //获取各个节点通信状态的新数据
    public List<NodeNewData> getLatestNodeNewData() {
        List<NodeNewData> nodeNewDataList = new ArrayList<>();
        for (int i = 1; i <= 16; i++) {
            String nodeId = String.valueOf(i);
            NodeNewData nodeNewData = nodeNewDataMapper.getNewDataByNodeId(nodeId);
            if (nodeNewData != null) {
                String entityCommType = nodeNewData.getEntityCommType();
                String commState = nodeNewData.getCommState();
                switch (entityCommType) {
                    case "1" -> nodeNewData.setEntityCommType("数据发送节点");
                    case "0" -> nodeNewData.setEntityCommType("数据接收节点");
                }
                switch (commState) {
                    case "0" -> nodeNewData.setCommState("通信断开");
                    case "1" -> nodeNewData.setCommState("正常通信");
                    default -> nodeNewData.setCommState("正常通信");
                }
                nodeNewDataList.add(nodeNewData);
            }
        }
        System.out.println(nodeNewDataList);
        return nodeNewDataList;
    }


    public List<Node> getNodeList() {
        List<Node> nodelist = getLatestNodeData();
        return nodelist;
    }


    @Override
    public List<Node> RandomData() {
        List<Node> nodeList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Node node = new Node();
            Double longitude = Math.random() * 2 + 119;
            Double latitude = Math.random() * 0.5 + 24;
            node.setLatitude(new BigDecimal(latitude));
            node.setLongitude(new BigDecimal(longitude));
            nodeList.add(node);
        }
        return nodeList;
    }

//
//    /**
//     * 查询【请填写功能名称】
//     *
//     * @param id 【请填写功能名称】主键
//     * @return 【请填写功能名称】
//     */
//    @Override
//    public Node selectNodeById(Long id)
//    {
//        return nodeMapper.selectNodeById(id);
//    }
//
//    /**
//     * 查询【请填写功能名称】列表
//     *
//     * @param node 【请填写功能名称】
//     * @return 【请填写功能名称】
//     */

//
//    /**
//     * 新增【请填写功能名称】
//     *
//     * @param node 【请填写功能名称】
//     * @return 结果
//     */
//    @Override
//    public int insertNode(Node node)
//    {
//
////        long l = System.currentTimeMillis();
////        node.setCreateTime(DateUtils.getNowDate());
//
//        return nodeMapper.insertNode(node);
//    }
//
//    /**
//     * 修改【请填写功能名称】
//     *
//     * @param node 【请填写功能名称】
//     * @return 结果
//     */
//    @Override
//    public int updateNode(Node node)
//    {
//        return nodeMapper.updateNode(node);
//    }
//
//    /**
//     * 批量删除【请填写功能名称】
//     *
//     * @param ids 需要删除的【请填写功能名称】主键
//     * @return 结果
//     */
//    @Override
//    public int deleteNodeByIds(Long[] ids)
//    {
//        return nodeMapper.deleteNodeByIds(ids);
//    }
//
//    /**
//     * 删除【请填写功能名称】信息
//     *
//     * @param id 【请填写功能名称】主键
//     * @return 结果
//     */
//    @Override
//    public int deleteNodeById(Long id)
//    {
//        return nodeMapper.deleteNodeById(id);
//    }
}
