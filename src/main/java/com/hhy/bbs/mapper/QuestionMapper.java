package com.hhy.bbs.mapper;

import com.hhy.bbs.model.Question;
import com.hhy.bbs.model.QuestionExample;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface QuestionMapper {

@Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag)values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);
}
