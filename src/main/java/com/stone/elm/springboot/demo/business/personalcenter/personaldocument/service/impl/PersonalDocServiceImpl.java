package com.stone.elm.springboot.demo.business.personalcenter.personaldocument.service.impl;

import com.stone.elm.springboot.demo.attachment.model.ao.AttachDtlAO;
import com.stone.elm.springboot.demo.attachment.service.IAttachFileService;
import com.stone.elm.springboot.demo.basictech.common.constant.NumberConstant;
import com.stone.elm.springboot.demo.basictech.common.exception.BusinessException;
import com.stone.elm.springboot.demo.basictech.common.response.ResponseConstant;
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

import java.util.*;
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
        LOGGER.info("根据条件查询个人文档表信息列表出参:{}", JsonUtil.convertObjectToJson(resultData));

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

        Map<Long, String> idNameMap = updatePersonalDocList.stream().collect(Collectors.toMap(PersonalDocAO::getPersonalDocID, PersonalDocAO::getPersonalDocName));

        PersonalDocAO personalDocAO = new PersonalDocAO();
        personalDocAO.setPersonalDocIDList(updatePersonalDocList.stream().map(PersonalDocAO::getPersonalDocID).distinct().collect(Collectors.toList()));
        List<PersonalDocVO> fullUpdateList = selectPersonalDocList(personalDocAO).getData().stream().filter(ao -> Objects.nonNull(ao.getAttachDtlID())).collect(Collectors.toList());

        Integer row = personalDocMapper.updatePersonalDocList(updatePersonalDocList);
        LOGGER.info("成功执行{}条数据", row);

        ArrayList<AttachDtlAO> updateAttachDtlList = new ArrayList<>();
        for (PersonalDocVO personalDoc : fullUpdateList) {
            AttachDtlAO attachDtlAO = new AttachDtlAO();
            attachDtlAO.setAttachDtlID(personalDoc.getAttachDtlID());
            attachDtlAO.setAttachDtlName(idNameMap.get(personalDoc.getPersonalDocID()));
            updateAttachDtlList.add(attachDtlAO);
        }

        iAttachFileService.updateAttachDtlList(updateAttachDtlList);

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
        List<Long> attachDtlIDList = allDeleteDocList.stream().map(PersonalDocAO::getAttachDtlID).distinct().filter(id -> Objects.nonNull(id)).collect(Collectors.toList());
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

    /**
     * 查询当前文档路径ServiceImpl
     * @param personalDocAO
     * @return
     */
    @Override
    public ResponseResult<List<PersonalDocVO>> selectPersonalDocPath(PersonalDocAO personalDocAO) {
        LOGGER.info("查询当前文档路径入参:{}", JsonUtil.convertObjectToJson(personalDocAO));

        if (Objects.isNull(personalDocAO.getParentDocID()) && Objects.isNull(personalDocAO.getPersonalDocID())) {
            throw new BusinessException("文档标识或上级文档标识不能同时为空!", ResponseConstant.FAIL);
        }

        if (Objects.isNull(personalDocAO.getParentDocID())) {
            List<PersonalDocVO> personalDocList = personalDocMapper.selectPersonalDocList(personalDocAO);
            if (CollectionUtils.isNotEmpty(personalDocList)) {
                PersonalDocVO personalDocVO = personalDocList.stream().findFirst().get();
                personalDocAO.setParentDocID(personalDocVO.getParentDocID());
            }
        }

        ArrayList<PersonalDocVO> resultData = new ArrayList<>();
        initPersonalDocPath(resultData, personalDocAO.getParentDocID());

        Collections.reverse(resultData);

        ResponseResult<List<PersonalDocVO>> result = ResultUtils.wrapResult(resultData);

        LOGGER.info("查询当前文档路径出参:{}", JsonUtil.convertObjectToJson(result));
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
                docAO.setParentDocID(personalDocVO.getPersonalDocID());
                List<PersonalDocVO> tempList = personalDocMapper.selectPersonalDocList(docAO);

                getAllDeleteDocList(allDeleteDocList, tempList);
            }
        }
    }

    private void initPersonalDocPath(ArrayList<PersonalDocVO> dataList, Long parentDocID) {
        if (Objects.isNull(parentDocID) || NumberConstant.LONG_ONE.equals(parentDocID)) {
            return;
        }

        PersonalDocAO personalDocAO = new PersonalDocAO();
        personalDocAO.setPersonalDocID(parentDocID);
        List<PersonalDocVO> personalDocList = personalDocMapper.selectPersonalDocList(personalDocAO);

        if (CollectionUtils.isNotEmpty(personalDocList)) {
            PersonalDocVO personalDocVO = personalDocList.stream().findFirst().get();
            dataList.add(personalDocVO);
            initPersonalDocPath(dataList, personalDocVO.getParentDocID());
        }
    }

}