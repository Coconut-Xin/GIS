package com.ssj.gis4.service;

import com.ssj.gis4.domain.Node;
import com.ssj.gis4.domain.NodeNewData;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author ruoyi
 * @date 2024-06-06
 */
public interface NodeService
{

    public List<Node> nodelist();


    public List<Node> selectNowNodeList();


//    public  Node getNodeDataByNodeId(String droneId);
    public List<Node> getLatestNodeData();

    //    2024年7月17日 02点19分 ：测试当前ROS
    public List<Node>  getLatestNodeDataByNodeIdTestRos();

    public List<NodeNewData> getLatestNodeNewData();


    public List<Node> getNodeList();


    public  List<Node> RandomData();





//    /**
//     * 查询【请填写功能名称】
//     *
//     * @param id 【请填写功能名称】主键
//     * @return 【请填写功能名称】
//     */
//    public Node selectNodeById(Long id);
//
//    /**
//     * 查询【请填写功能名称】列表
//     *
//     * @param node 【请填写功能名称】
//     * @return 【请填写功能名称】集合
//     */
//    public List<Node> selectNodeList(Node node);
//
//    /**
//     * 新增【请填写功能名称】
//     *
//     * @param node 【请填写功能名称】
//     * @return 结果
//     */
//    public int insertNode(Node node);
//
//    /**
//     * 修改【请填写功能名称】
//     *
//     * @param node 【请填写功能名称】
//     * @return 结果
//     */
//    public int updateNode(Node node);
//
//    /**
//     * 批量删除【请填写功能名称】
//     *
//     * @param ids 需要删除的【请填写功能名称】主键集合
//     * @return 结果
//     */
//    public int deleteNodeByIds(Long[] ids);
//
//    /**
//     * 删除【请填写功能名称】信息
//     *
//     * @param id 【请填写功能名称】主键
//     * @return 结果
//     */
//    public int deleteNodeById(Long id);

}
