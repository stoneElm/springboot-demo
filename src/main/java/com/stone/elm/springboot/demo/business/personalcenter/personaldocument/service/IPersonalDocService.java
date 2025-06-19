package com.stone.elm.springboot.demo.business.personalcenter.personaldocument.service;

import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.business.personalcenter.personaldocument.model.ao.PersonalDocAO;
import com.stone.elm.springboot.demo.business.personalcenter.personaldocument.model.vo.PersonalDocVO;

import java.util.List;

public interface IPersonalDocService {

    /**
     * 查询个人文档表列表Service
     * @param personalDocAO
     * @return
     */
    ResponseResult<List<PersonalDocVO>> selectPersonalDocList(PersonalDocAO personalDocAO);

    /**
     * 创建个人文档表列表Service
     * @param personalDocList
     * @return
     */
    ResponseResult<List<PersonalDocVO>> createPersonalDocList(List<PersonalDocAO> personalDocList);

    /**
     * 更新个人文档表列表Service
     * @param personalDocList
     * @return
     */
    ResponseResult<List<PersonalDocVO>> updatePersonalDocList(List<PersonalDocAO> personalDocList);

    /**
     * 删除个人文档表列表Service
     * @param personalDocList
     * @return
     */
    ResponseResult<List<PersonalDocVO>> deletePersonalDocList(List<PersonalDocAO> personalDocList);

    /**
     * 查询当前文档路径Service
     * @param personalDocAO
     * @return
     */
    ResponseResult<List<PersonalDocVO>> selectPersonalDocPath(PersonalDocAO personalDocAO);
}
