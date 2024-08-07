package com.ssj.gis4.service.impl;


import com.ssj.gis4.domain.Task;
import com.ssj.gis4.mapper.TaskMapper;
import com.ssj.gis4.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired(required = false)
    private TaskMapper taskMapper;

    @Override
    public List<Task> taskList() {
        List<Task> tasklist = taskMapper.selectTaskList();
        // 创建map将节点类型数字转换为对应文字描述
        Map<String, String> nodeTypeMapping = new HashMap<>();
        nodeTypeMapping.put("0", "扑翼无人机");
        nodeTypeMapping.put("1", "旋翼无人机");
        nodeTypeMapping.put("2", "无人车");

        for (Task task : tasklist) {
            //转换任务类型
            String taskType = task.getTaskType();
            switch (taskType) {
                case "0" -> task.setTaskType("侦察任务");
                case "1" -> task.setTaskType("打击任务");
                case "2" -> task.setTaskType("察打一体任务");
            }

            //转换节点类型
            List<String> nodesType = task.getNodesType();
            List<String> updatedNodesType = new ArrayList<>();
            for (String nodetype : nodesType) {
                String mappedNodeType = nodeTypeMapping.get(nodetype);
                updatedNodesType.add(mappedNodeType);
            }
            task.setNodesType(updatedNodesType);

            //转换任务完成标识
            String taskFinishFlag = task.getTaskFinishFlag();
            switch (taskFinishFlag) {
                case "0" -> task.setTaskFinishFlag("未完成");
                case "1" -> task.setTaskFinishFlag("完成");
                default -> task.setTaskFinishFlag("未完成");
            }

        }
        System.out.println(tasklist);
        return tasklist;

    }



}
