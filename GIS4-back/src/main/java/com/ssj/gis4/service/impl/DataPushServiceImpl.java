package com.ssj.gis4.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssj.gis4.domain.*;
import com.ssj.gis4.service.ClusterService;
import com.ssj.gis4.service.DataPushService;
import com.ssj.gis4.service.NodeService;
import com.ssj.gis4.service.TaskService;
import com.ssj.gis4.util.EllipseParameters;
import com.ssj.gis4.util.EllipseParameters2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * ClassName: DataPushServiceImpl
 * Packge:
 * Description:
 *
 * @Author:孙世杰
 * @Create 2024/6/27 18:07
 * Version 1.0
 */
@Service
public class DataPushServiceImpl implements DataPushService {
    @Autowired
    NodeService nodeService;

    @Autowired
    private ClusterService clusterService;

    @Autowired
    private TaskService taskService;

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private List<Node> nodeList = null;
    private List<NodeNewData> nodeNewDataList = null;
    private List<Cluster> clusterlist = null;
    private List<Task> tasklist = null;

//    @Autowired
//    private MyWebSocketHandler myWebSocketHandler; // 如果需要的话

    // 注册WebSocketSession
    public void registerSession(WebSocketSession session) {
        sessions.add(session);
    }

    // 注销WebSocketSession
    public void unregisterSession(WebSocketSession session) {
        sessions.remove(session);
    }

    @Scheduled(fixedRate = 3000) // 每3秒执行一次
    public void pushDataToClients() {
        //        数据map
        Map<String,Object> map=new HashMap<>();

        // 从数据库获取数据
        nodeList = nodeService.getLatestNodeData();
        clusterlist = clusterService.clusterList();
        tasklist = taskService.taskList();
        nodeNewDataList = nodeService.getLatestNodeNewData();

        RandomNode(); //对数据库拿到的node进行动态化

        map.put("nodeList",nodeList);
        map.put("nodeNewDataList",nodeNewDataList);
        map.put("clusterlist",clusterlist);
        map.put("tasklist",tasklist);

        //计算得到集群层椭圆
        List<Ellipse> clusterEllipseList = getEllipse();
        map.put("clusterEllipseList",clusterEllipseList);

        //计算得到任务层椭圆
        List<Ellipse> taskEllipseList = getTaskEllipse();
        map.put("taskEllipseList",taskEllipseList);

        String message = null;

        try{
            message = convertToJson(map);
        }catch (Exception e){
            e.printStackTrace();
        }
        // 遍历sessions并发送数据
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    // 将数据转换为JSON或其他格式
                    // 假设的转换方法
                    System.out.println(message);
                    session.sendMessage(new TextMessage(message));
                } catch (Exception e) {
                    // 处理异常
                }
            }
        }
    }

    // ... 其他方法 ...


    // 假设的JSON转换方法
    private String convertToJson(Map<String, Object> map) {
        // 使用Jackson或其他库将实体转换为JSON字符串

        String jsonString = null; // 将数据列表转换为JSON字符串

        try {
            jsonString = objectMapper.writeValueAsString(map);
            //     jsonString = data.toString();
        } catch (JsonProcessingException e) {
            e.printStackTrace();

            System.out.println("数据列表转换为JSON字符串失败");

            throw new RuntimeException(e);
        }

        LocalDateTime now = LocalDateTime.now();

        return jsonString;
    }


    //获取椭圆形状 --时间复杂度为nlog（m）
    private List<Ellipse> getEllipse(){
        //      cluster与索引的映射
        Map<String,Integer> indexMap=new HashMap<>();

        //构建椭圆形状数组
        List<Ellipse> ellipseList = new ArrayList<>();

        double[][][] positionArray=new double[clusterlist.size()][][];
        int[] nowPosition = new int[clusterlist.size()];

        //构建索引与创造对应数组
        for (int i = 0; i < clusterlist.size(); i++) {
            positionArray[i] = new double[clusterlist.get(i).getNodesNumber()][2];
            indexMap.put(clusterlist.get(i).getClusterId(),i);
        }

        //将数据写入数组
        for (Node node : nodeList) {
            String clusterId = node.getClusterId();
            if(!indexMap.containsKey(clusterId)){
                continue;
            }



            int index=indexMap.get(clusterId);

            if(!clusterlist.get(index).getNodesList().contains(node.getVehicleId())){
                 continue;
            }

            positionArray[index][ nowPosition[index] ][0]=node.getLongitude().doubleValue();
            positionArray[index][ nowPosition[index] ][1]=node.getLatitude().doubleValue();
            nowPosition[index]++;
        }

        for (int i = 0; i < clusterlist.size(); i++) {
           //  System.out.println("index:"+i);
            ellipseList.add(EllipseParameters2.findEllipse(positionArray[i]));
        }

        System.out.println("cluster："+ellipseList);
        return  ellipseList ;
    }


//    对数据进行一定的随机化，模拟动态
    private void RandomNode(){
        Map<String,Double> clusterIndexMap=new HashMap<>();

        for (int i = 0; i < clusterlist.size(); i++) {
            Double random =0.1* (int)(Math.random()*4);
            clusterIndexMap.put(clusterlist.get(i).getClusterId(),random);
        }
        for (int i = 0; i < nodeList.size(); i++) {
            Node node = nodeList.get(i);
            String clusterId = node.getClusterId();
            if(!clusterId.isEmpty()){ //增加一层判空处理，防止有节点不在集群中
                Double x = clusterIndexMap.get(clusterId);
//            Double x= indexMap.get(node.getClusterId());
                node.setLatitude(new BigDecimal(node.getLatitude().doubleValue()+x));
                node.setLongitude(new BigDecimal(node.getLongitude().doubleValue()+x));
//            nodeList.set(i,node);
            }else{//独立于集群的节点
                Double random =0.1* (int)(Math.random()*4);
                node.setLatitude(new BigDecimal(node.getLatitude().doubleValue()+random));
                node.setLongitude(new BigDecimal(node.getLongitude().doubleValue()+random));
            }
        }

    }

    // 任务层椭圆形状
    private List<Ellipse> getTaskEllipse(){
        // task与索引的映射
        Map<String,Integer> indexMap=new HashMap<>();

        //构建椭圆形状数组
        List<Ellipse> ellipseList = new ArrayList<>();

        double[][][] positionArray=new double[tasklist.size()][][];//[tasknum][nodenum][position]
        int[] nowPosition = new int[tasklist.size()];

        //构建索引与创造对应数组
        int nodesNumber =0;
        for (int i = 0; i < tasklist.size(); i++) {
            nodesNumber = tasklist.get(i).getNodesList().size();
            positionArray[i] = new double[nodesNumber][2];
            indexMap.put(tasklist.get(i).getTaskId(),i);
        }

        //将数据写入数组
        for (Node node : nodeList) {
            String taskId = node.getTaskId();
            if(!indexMap.containsKey(taskId)){
                continue;
            }

            int index=indexMap.get(taskId);

            if(!tasklist.get(index).getNodesList().contains(node.getVehicleId())){
                continue;
            }

            positionArray[index][ nowPosition[index] ][0]=node.getLongitude().doubleValue();
            positionArray[index][ nowPosition[index] ][1]=node.getLatitude().doubleValue();
            nowPosition[index]++;
        }

        for (int i = 0; i < tasklist.size(); i++) {
            //  System.out.println("index:"+i);
            ellipseList.add(EllipseParameters2.findEllipse(positionArray[i]));
        }

        System.out.println("task："+ellipseList);

        return  ellipseList ;
    }




}
