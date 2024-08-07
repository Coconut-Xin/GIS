package com.ssj.gis4.mapper;

import com.ssj.gis4.domain.Task;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskMapper {
    //获取最新的集群信息
    public List<Task> selectTaskList();

}
