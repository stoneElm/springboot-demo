package com.stone.elm.springboot.demo.business.personalcenter.personaldocument.service.impl;

import com.stone.elm.springboot.demo.attachment.model.ao.AttachDtlAO;
import com.stone.elm.springboot.demo.attachment.service.IAttachFileService;
import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.basictech.common.response.ResultUtils;
import com.stone.elm.springboot.demo.basictech.common.service.IPrimaryKeyService;
import com.stone.elm.springboot.demo.basictech.common.utils.AuthenticationUtil;
import com.stone.elm.springboot.demo.basictech.common.utils.BeanCopyUtil;
import com.stone.elm.springboot.demo.basictech.common.utils.DateUtils;
import com.stone.elm.springboot.demo.basictech.common.utils.JsonUtil;
import com.stone.elm.springboot.demo.business.personalcenter.personaldocument.mapper.PersonalDocMapper;
import com.stone.elm.springboot.demo.business.personalcenter.personaldocument.model.ao.PersonalDocAO;
import com.stone.elm.springboot.demo.business.personalcenter.personaldocument.model.enums.DocTypeEnum;
import com.stone.elm.springboot.demo.business.personalcenter.personaldocument.model.vo.PersonalDocVO;
import com.stone.elm.springboot.demo.business.personalcenter.personaldocument.service.IPersonalDocService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonalDocServiceImpl implements IPersonalDocService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonalDocServiceImpl.class);

    @Autowired
    private IPrimaryKeyService iPrimaryKeyService;

    @Autowired
    private IAttachFileService iAttachFileService;

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

        personalDocAO.setCreateStf(AuthenticationUtil.getUserAndRoleInfo().getUserName());

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

        String currentFormat = DateUtils.getCurrentFormat();
        // 获取当前登陆人信息
        String userName = AuthenticationUtil.getUserAndRoleInfo().getUserName();

        for (PersonalDocAO personalDocAO : createPersonalDocList) {
            personalDocAO.setPersonalDocID(iPrimaryKeyService.getPrimaryKey());
            personalDocAO.setUpdateDate(currentFormat);
            personalDocAO.setCreateDate(currentFormat);
            personalDocAO.setCreateStf(userName);
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

        PersonalDocAO personalDocAO = new PersonalDocAO();
        personalDocAO.setPersonalDocIDList(deletePersonalDocList.stream().map(PersonalDocAO::getPersonalDocID).distinct().collect(Collectors.toList()));
        List<PersonalDocVO> tempList = selectPersonalDocList(personalDocAO).getData();

        // 递归获取所有文件和文件夹
        List<PersonalDocAO> allDeleteDocList = new ArrayList<>();
        getAllDeleteDocList(allDeleteDocList, tempList);

        Integer row = personalDocMapper.deletePersonalDocList(allDeleteDocList);
        LOGGER.info("成功执行{}条数据", row);


        ArrayList<AttachDtlAO> deleteAttachDtlList = new ArrayList<>();
        List<Long> attachDtlIDList = allDeleteDocList.stream().map(PersonalDocAO::getAttachDtlID).distinct().collect(Collectors.toList());
        for (Long attachDtlID : attachDtlIDList) {
            AttachDtlAO attachDtlAO = new AttachDtlAO();
            attachDtlAO.setAttachDtlID(attachDtlID);
            deleteAttachDtlList.add(attachDtlAO);
        }
        iAttachFileService.deleteAttachDtlList(deleteAttachDtlList, true);

        List<PersonalDocVO> resultData = new ArrayList<>();
        BeanCopyUtil.copyList(deletePersonalDocList, resultData, PersonalDocVO.class);

        ResponseResult<List<PersonalDocVO>> result = ResultUtils.wrapResult(resultData);

        LOGGER.info("删除个人文档表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

    private void getAllDeleteDocList(List<PersonalDocAO> allDeleteDocList, List<PersonalDocVO> deletePersonalDocList) {
        if (CollectionUtils.isEmpty(deletePersonalDocList)) {
            return;
        }

        for (PersonalDocVO personalDocVO : deletePersonalDocList) {
            PersonalDocAO ao = new PersonalDocAO();
            BeanCopyUtil.copy(personalDocVO, ao);
            allDeleteDocList.add(ao);

            if (StringUtils.equals(personalDocVO.getPersonalDocType(), DocTypeEnum.FOLDER.getTypeCode())) {
                PersonalDocAO docAO = new PersonalDocAO();
                docAO.setPersonalDocID(personalDocVO.getPersonalDocID());
                List<PersonalDocVO> tempList = personalDocMapper.selectPersonalDocList(docAO);

                getAllDeleteDocList(allDeleteDocList, tempList);
            }
        }
    }

}