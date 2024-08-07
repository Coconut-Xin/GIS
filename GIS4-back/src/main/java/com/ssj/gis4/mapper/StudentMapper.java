package com.ssj.gis4.mapper;

import com.ssj.gis4.domain.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentMapper {

//    @Select("select * from student where id = #{id}")
    Student selectStudentById(Long id);

}
