package com.stone.elm.springboot.demo.business.personalcenter.personaldocument.service.impl;

import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.basictech.common.response.ResultUtils;
import com.stone.elm.springboot.demo.basictech.common.service.IPrimaryKeyService;
import com.stone.elm.springboot.demo.basictech.common.utils.BeanCopyUtil;
import com.stone.elm.springboot.demo.basictech.common.utils.JsonUtil;
import com.stone.elm.springboot.demo.business.personalcenter.personaldocument.mapper.PersonalDocMapper;
import com.stone.elm.springboot.demo.business.personalcenter.personaldocument.model.ao.PersonalDocAO;
import com.stone.elm.springboot.demo.business.personalcenter.personaldocument.model.vo.PersonalDocVO;
import com.stone.elm.springboot.demo.business.personalcenter.personaldocument.service.IPersonalDocService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonalDocServiceImpl implements IPersonalDocService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonalDocServiceImpl.class);

    @Autowired
    private IPrimaryKeyService iPrimaryKeyService;

    @Autowired
    private PersonalDocMapper personalDocMapper;

    /**
     * 查询个人文档表列表ServiceImpl
     * @param personalDocAO
     * @return
     */
    @Override
    public ResponseResult<List<PersonalDocVO>> selectPersonalDocList(PersonalDocAO personalDocAO) {
        LOGGER.info("查询个人文档表入参:{}", JsonUtil.convertObjectToJson(personalDocAO));

        List<PersonalDocVO> resultData = personalDocMapper.selectPersonalDocList(personalDocAO);
        LOGGER.info("根据条件查询个人文档表信息列表出差:{}", JsonUtil.convertObjectToJson(resultData));

        Integer countPersonalDocAll = personalDocMapper.countPersonalDocAll(personalDocAO);
        LOGGER.info("根据条件统计结果:{}", countPersonalDocAll);

        ResponseResult<List<PersonalDocVO>> result = ResultUtils.wrapResult(resultData);
        result.setTotal(countPersonalDocAll);

        LOGGER.info("查询个人文档表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

    /**
     * 创建个人文档表列表ServiceImpl
     * @param createPersonalDocList
     * @return
     */
    @Override
    public ResponseResult<List<PersonalDocVO>> createPersonalDocList(List<PersonalDocAO> createPersonalDocList) {
        LOGGER.info("创建个人文档表入参:{}", JsonUtil.convertObjectToJson(createPersonalDocList));

        if (CollectionUtils.isEmpty(createPersonalDocList)) {
            return ResultUtils.wrapResult();
        }

        for (PersonalDocAO personalDocAO : createPersonalDocList) {
            personalDocAO.setPersonalDocID(iPrimaryKeyService.getPrimaryKey());
        }

        Integer row = personalDocMapper.createPersonalDocList(createPersonalDocList);
        LOGGER.info("成功执行{}条数据", row);

        List<PersonalDocVO> resultData = new ArrayList<>();
        BeanCopyUtil.copyList(createPersonalDocList, resultData, PersonalDocVO.class);

        ResponseResult<List<PersonalDocVO>> result = ResultUtils.wrapResult(resultData);

        LOGGER.info("创建个人文档表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

    /**
     * 更新个人文档表列表ServiceImpl
     * @param updatePersonalDocList
     * @return
     */
    @Override
    public ResponseResult<List<PersonalDocVO>> updatePersonalDocList(List<PersonalDocAO> updatePersonalDocList) {
        LOGGER.info("更新个人文档表入参:{}", JsonUtil.convertObjectToJson(updatePersonalDocList));

        if (CollectionUtils.isEmpty(updatePersonalDocList)) {
            return ResultUtils.wrapResult();
        }

        Integer row = personalDocMapper.updatePersonalDocList(updatePersonalDocList);
        LOGGER.info("成功执行{}条数据", row);

        List<PersonalDocVO> resultData = new ArrayList<>();
        BeanCopyUtil.copyList(updatePersonalDocList, resultData, PersonalDocVO.class);

        ResponseResult<List<PersonalDocVO>> result = ResultUtils.wrapResult(resultData);

        LOGGER.info("更新个人文档表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

    /**
     * 删除个人文档表列表ServiceImpl
     * @param deletePersonalDocList
     * @return
     */
    @Override
    public ResponseResult<List<PersonalDocVO>> deletePersonalDocList(List<PersonalDocAO> deletePersonalDocList) {
        LOGGER.info("删除个人文档表入参:{}", JsonUtil.convertObjectToJson(deletePersonalDocList));

        if (CollectionUtils.isEmpty(deletePersonalDocList)) {
            return ResultUtils.wrapResult();
        }

        Integer row = personalDocMapper.deletePersonalDocList(deletePersonalDocList);
        LOGGER.info("成功执行{}条数据", row);

        List<PersonalDocVO> resultData = new ArrayList<>();
        BeanCopyUtil.copyList(deletePersonalDocList, resultData, PersonalDocVO.class);

        ResponseResult<List<PersonalDocVO>> result = ResultUtils.wrapResult(resultData);

        LOGGER.info("删除个人文档表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

}