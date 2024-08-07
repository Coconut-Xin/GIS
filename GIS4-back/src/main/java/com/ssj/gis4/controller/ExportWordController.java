package com.ssj.gis4.controller;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.policy.HackLoopTableRenderPolicy;
import com.ssj.gis4.domain.Cluster;
import com.ssj.gis4.domain.Node;
import com.ssj.gis4.domain.Task;
import com.ssj.gis4.service.ClusterService;
import com.ssj.gis4.service.NodeService;
import com.ssj.gis4.service.TaskService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/GIS/download")
public class ExportWordController {
    @Autowired
    NodeService nodeService;

    @Autowired
    private ClusterService clusterService;

    @Autowired
    private TaskService taskService;


    @RequestMapping("/exportDataWord")
    public void exportDataWord(HttpServletResponse response) throws IOException {
        System.out.println("http://localhost:8080/GIS/download/exportDataWord");

        try {
            // 从数据库获取数据
            List<Node> nodeList = nodeService.getLatestNodeData();
            List<Cluster> clusterlist = clusterService.clusterList();
            List<Task> tasklist = taskService.taskList();

            //时间
            LocalDateTime ldt = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String now = dateTimeFormatter.format(ldt);

            //组装表格列表数据
            List<Map<String, Object>> taskInfo = new ArrayList<Map<String, Object>>();
            for (Task task : tasklist) {
                Map<String, Object> taskDetailMap = new HashMap<String, Object>();
                taskDetailMap.put("id", task.getTaskId());
                taskDetailMap.put("type", task.getTaskType());
                taskDetailMap.put("desc", task.getTaskDescription());
                taskDetailMap.put("latitude", task.getTaskLocationLatitude());
                taskDetailMap.put("longitude", task.getTaskLocationLongitude());
                taskDetailMap.put("altitude", task.getTaskLocationAltitude());
                taskDetailMap.put("finishflag", task.getTaskFinishFlag());
                taskDetailMap.put("cluster", task.getClustersList());
                taskInfo.add(taskDetailMap);
            }

            //组装表格列表数据
            List<Map<String, Object>> clusterInfo = new ArrayList<Map<String, Object>>();
            for (Cluster cluster : clusterlist) {
                Map<String, Object> clusterDetailMap = new HashMap<String, Object>();
                clusterDetailMap.put("id", cluster.getClusterId());
                clusterDetailMap.put("name", cluster.getClusterName());
                clusterDetailMap.put("status", cluster.getClusterStatus());
                clusterDetailMap.put("lostnum", cluster.getLostNodesNumber());
                clusterDetailMap.put("damagenum", cluster.getDamagedNodesNumber());
                clusterDetailMap.put("comstatus", cluster.getCommunicationStationStatus());
                clusterDetailMap.put("interstatus", cluster.getElectronicInterferenceStatus());
                clusterDetailMap.put("node", cluster.getNodesList());
                clusterInfo.add(clusterDetailMap);
            }

            Map<String, Object> data = new HashMap<>() {
                {
                    put("taskInfo", taskInfo);
                    put("clusterInfo", clusterInfo);
                    put("time", now);
                }
            };

            //获取word模板地址
            ClassPathResource classPathResource = new ClassPathResource("templates/infoDetail.docx");
            String resource = URLDecoder.decode(classPathResource.getURL().getPath(), StandardCharsets.UTF_8);//解决中文路径编解码问题
//            System.out.println("resource:"+resource);
            //渲染表格  动态行
            HackLoopTableRenderPolicy policy = new HackLoopTableRenderPolicy();
            Configure config = Configure.builder().bind("taskInfo", policy).bind("clusterInfo", policy).build();
            XWPFTemplate template = XWPFTemplate.compile(resource, config).render(data);

            //生成文件名
            Long time = new Date().getTime();
            String formatSuffix = "outInfo.docx";
            // 拼接后的文件名
            String fileName = time + formatSuffix;

            //=================本地存储生成word测试=================
//            String temDir="E:\\mytemp\\"; //文件存放地址
//            FileOutputStream fos = new FileOutputStream(temDir+fileName);
//            template.write(fos);
//            fos.close();
//            template.close();

            //=================生成word到设置浏览默认下载地址=================
            // 设置文件名
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document;charset=UTF-8");
            OutputStream out = response.getOutputStream();
            template.write(out);
            out.flush();
            out.close();
            template.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
