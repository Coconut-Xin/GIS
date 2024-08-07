package com.ssj.gis4.mapper;

import com.ssj.gis4.domain.NodeNewData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NodeNewDataMapper {
    /*
    根据表名获取各个表的最新数据
     */
    NodeNewData getNewDataByNodeId(String nodeId);
}
