package com.stone.elm.springboot.demo.business.personalcenter.personaldocument.mapper;

import com.stone.elm.springboot.demo.business.personalcenter.personaldocument.model.ao.PersonalDocAO;
import com.stone.elm.springboot.demo.business.personalcenter.personaldocument.model.vo.PersonalDocVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PersonalDocMapper {

    /**
     * 查询个人文档表列表Mapper
     * @param personalDocAO
     * @return
     */
    List<PersonalDocVO> selectPersonalDocList(PersonalDocAO personalDocAO);

    /**
     * 统计个人文档表列表Mapper
     * @param personalDocAO
     * @return
     */
    Integer countPersonalDocAll(PersonalDocAO personalDocAO);

    /**
     * 创建个人文档表列表Mapper
     * @param createPersonalDocList
     * @return
     */
    Integer createPersonalDocList(@Param("createPersonalDocList") List<PersonalDocAO> createPersonalDocList);

    /**
     * 修改个人文档表列表Mapper
     * @param updatePersonalDocList
     * @return
     */
    Integer updatePersonalDocList(@Param("updatePersonalDocList") List<PersonalDocAO> updatePersonalDocList);

    /**
     * 删除个人文档表列表Mapper
     * @param deletePersonalDocList
     * @return
     */
    Integer deletePersonalDocList(@Param("deletePersonalDocList") List<PersonalDocAO> deletePersonalDocList);

}
