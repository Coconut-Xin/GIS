package com.ssj.gis4.mapper;

import com.ssj.gis4.domain.Node;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author ruoyi
 * @date 2024-06-06
 */

@Mapper
public interface NodeMapper
{


    @Select("select * from node")
    public List<Node> nodeList();


    @Select("select * from node where node.create_time = (select MAX(create_time) from node)")
    public Node selectNowNode();

    /*
    根据表名获取各个表的最新数据
     */
    public Node getDataByNodeId(String nodeId);

    /*
    跟ROS配合---根据表名获取各个表的最新数据
    区别：2024年7月17日 02点08分
    现在的ROS中给的Node表中的create_time 是Double 类型，为了展示，构建这个方法
    */
    public Node getDataByNodeIdTestRos(String nodeId);

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */

    public Node selectNodeById(Long id);


    /**
     * 查询【请填写功能名称】列表
     *
     * @param node 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Node> selectNodeList(Node node);

    /**
     * 新增【请填写功能名称】
     *
     * @param node 【请填写功能名称】
     * @return 结果
     */
    public int insertNode(Node node);

    /**
     * 修改【请填写功能名称】
     *
     * @param node 【请填写功能名称】
     * @return 结果
     */
    public int updateNode(Node node);

    /**
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteNodeById(Long id);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNodeByIds(Long[] ids);

}
