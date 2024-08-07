package com.ssj.gis4.controller;

import com.ssj.gis4.domain.Task;
import com.ssj.gis4.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/GIS/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/taskList")
    public List<Task> list() {
        System.out.println("http://localhost:8080/GIS/task/taskList");
        List<Task> tasklist = taskService.taskList();
        return tasklist;
    }

}
