package com.ssj.gis4.controller;

import com.ssj.gis4.domain.Cluster;

import com.ssj.gis4.service.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/GIS/cluster")
public class ClusterController {
    @Autowired
    private ClusterService clusterService;

    @GetMapping("/clusterList")
    public List<Cluster> list() {
        System.out.println("http://localhost:8080/GIS/cluster/clusterList");
        List<Cluster> clusterlist = clusterService.clusterList();
        return clusterlist;
    }

}
