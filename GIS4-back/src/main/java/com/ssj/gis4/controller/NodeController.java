package com.ssj.gis4.controller;


import com.ssj.gis4.domain.Node;
import com.ssj.gis4.domain.NodeNewData;
import com.ssj.gis4.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 【请填写功能名称】Controller
 *
 * @author ruoyi
 * @date 2024-06-06
 */
@RestController
@RequestMapping("/GIS/node")
public class NodeController {
    @Autowired
    private NodeService nodeService;


    @GetMapping("/list2")
    public List<Node> list2(Node node) {
        System.out.println("http://localhost:8080/GIS/node/list2");

        return nodeService.nodelist();
    }

    @GetMapping("/list3")
    public String list3() {

        System.out.println("http://localhost:8080/GIS/node/list3");
        return "list3 hello, are you shi ge sha  yyuyuyuyu~~~~~~";
    }

    /**
     * 查询【请填写功能名称】列表
     */

    @GetMapping("/list")
    public List<Node> list() {
        System.out.println("http://localhost:8080/GIS/node/list");
        List<Node> list = nodeService.selectNowNodeList();
        return list;
    }


    @GetMapping("/LatestNodelist")
    public List<Node> LatestNodelist() {

        System.out.println("http://localhost:8080/GIS/node/LatestNodelist");
        List<Node> latestnodelist = nodeService.getLatestNodeData();
        return latestnodelist;
    }


    @GetMapping("/RandomData")
    public List<Node> RandomData_list() {
        System.out.println("http://localhost:8080/GIS/node/RandomData");
        List<Node> list = nodeService.RandomData();
        return list;
    }


    @GetMapping("/LatestNewNodeDatalist")
    public List<NodeNewData> LatestNewNodeDatalist() {

        System.out.println("http://localhost:8080/GIS/node/LatestNewNodeDatalist");
        List<NodeNewData> latestnodelist = nodeService.getLatestNodeNewData();
        return latestnodelist;
    }


//
//    /**
//     * 获取【请填写功能名称】详细信息
//     */
//
//    @GetMapping(value = "/{id}")
//    public Node getInfo(@PathVariable("id") Long id)
//    {
//        return  nodeService.selectNodeById(id);
//    }


//    /**
//     * 新增【请填写功能名称】
//     */
//
//    @PostMapping
//    public AjaxResult add(@RequestBody Node node)
//    {
//        return toAjax(nodeService.insertNode(node));
//    }
//
//
//    /**
//     * 修改【请填写功能名称】
//     */
//
//    @PutMapping
//    public AjaxResult edit(@RequestBody Node node)
//    {
//        return toAjax(nodeService.updateNode(node));
//    }

//    /**
//     * 删除【请填写功能名称】
//     */
//
//	@DeleteMapping("/{ids}")
//    public Boolean remove(@PathVariable Long[] ids)
//    {
//        return nodeService.deleteNodeByIds(ids);
//    }


}
