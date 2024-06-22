package com.stone.elm.springboot.demo.attachment.service.impl;

import com.stone.elm.springboot.demo.attachment.mapper.IAttachMapper;
import com.stone.elm.springboot.demo.attachment.model.ao.AttachAO;
import com.stone.elm.springboot.demo.attachment.model.ao.AttachDtlAO;
import com.stone.elm.springboot.demo.attachment.model.root.AttachDtlRoot;
import com.stone.elm.springboot.demo.attachment.model.vo.AttachDtlVO;
import com.stone.elm.springboot.demo.attachment.model.vo.AttachVO;
import com.stone.elm.springboot.demo.attachment.service.IAttachFileService;
import com.stone.elm.springboot.demo.basictech.common.constant.NumberConstant;
import com.stone.elm.springboot.demo.basictech.common.constant.SymbolConstant;
import com.stone.elm.springboot.demo.basictech.common.exception.BusinessException;
import com.stone.elm.springboot.demo.basictech.common.response.ResponseConstant;
import com.stone.elm.springboot.demo.basictech.common.response.ResponseResult;
import com.stone.elm.springboot.demo.basictech.common.response.ResultUtils;
import com.stone.elm.springboot.demo.basictech.common.service.IPrimaryKeyService;
import com.stone.elm.springboot.demo.basictech.common.utils.AuthenticationUtil;
import com.stone.elm.springboot.demo.basictech.common.utils.BeanCopyUtil;
import com.stone.elm.springboot.demo.basictech.common.utils.DateUtils;
import com.stone.elm.springboot.demo.basictech.common.utils.JsonUtil;
import com.stone.elm.springboot.demo.business.user.model.vo.UserInfoVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AttachServiceFileImpl implements IAttachFileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttachServiceFileImpl.class);

    @Value("${local.storage.folder}")
    private String fileFolder;

    @Autowired
    private IPrimaryKeyService iPrimaryKeyService;

    @Autowired
    private IAttachMapper iAttachMapper;

    /**
     * 文件上传实现类
     * @param files
     * @return
     */
    @Override
    public ResponseResult<List<AttachVO>> batchUpload(AttachAO attachAO, MultipartFile... files) {
        // 获取当前登陆人信息
        UserInfoVO userInfo = AuthenticationUtil.getUserAndRoleInfo();

        if (Objects.isNull(userInfo)) {
            throw new BusinessException("用户登录信息不存在!", ResponseConstant.FAIL);
        }

        if (files.length == NumberConstant.ZERO) {
            throw new BusinessException("请上传所需保存的文件！", ResponseConstant.FAIL);
        }

        String userName = userInfo.getUserName();

        if (Objects.isNull(attachAO) || Objects.isNull(attachAO.getAttachID())) {
            attachAO.setAttachID(iPrimaryKeyService.getPrimaryKey());
            attachAO.setUploadStf(userName);
            attachAO.setUploadDate(DateUtils.getCurrentFormat());

            ArrayList<AttachAO> createAttachList = new ArrayList<>();
            createAttachList.add(attachAO);
            LOGGER.info("保存附件信息入参:{}", JsonUtil.convertObjectToJson(createAttachList));
            iAttachMapper.createAttachList(createAttachList);
        }

        List<AttachDtlRoot> attachDtlList = getAttachDtlListByFiles(files, attachAO.getAttachID(), userName);

        // 保存文件
        try {
            for (AttachDtlRoot attachDtl : attachDtlList) {
                String savePath = fileFolder + attachDtl.getAttachDtlPath();
                File dir = new File(savePath.substring(NumberConstant.ZERO, savePath.lastIndexOf(SymbolConstant.SLASH)));
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                attachDtl.getMultipartFile().transferTo(new File(savePath));
                attachDtl.setMultipartFile(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("文件保存失败!", ResponseConstant.FAIL);
        }

        LOGGER.info("保存附件详情信息入参:{}", JsonUtil.convertObjectToJson(attachDtlList));
        iAttachMapper.createAttachDtlList(attachDtlList);

        AttachVO attachVO = new AttachVO();
        BeanCopyUtil.copy(attachAO, attachVO);
        attachVO.setAttachDtlList(attachDtlList);

        ArrayList<AttachVO> resultData = new ArrayList<>();
        resultData.add(attachVO);

        return ResultUtils.wrapResult(resultData);
    }

    @Override
    public ResponseEntity<Resource> download(HttpServletRequest request, HttpServletResponse response, AttachDtlAO attachDtlAO) {
        LOGGER.info("附件下载入参:{}", JsonUtil.convertObjectToJson(attachDtlAO));

        if (Objects.isNull(attachDtlAO.getAttachDtlID())) {
            throw new BusinessException("附件详情标识不能为空", ResponseConstant.FAIL);
        }

        AttachDtlAO param = new AttachDtlAO();
        param.setAttachDtlID(attachDtlAO.getAttachDtlID());

        LOGGER.info("查询附件详情信息入参:{}", JsonUtil.convertObjectToJson(param));
        List<AttachDtlVO> attachDtlList = iAttachMapper.selectAttachDtlList(param);
        LOGGER.info("查询附件详情信息出参:{}", JsonUtil.convertObjectToJson(attachDtlList));

        if (CollectionUtils.isEmpty(attachDtlList)) {
            throw new BusinessException("不存在相应的附件详情信息，请检查！", ResponseConstant.FAIL);
        }

        AttachDtlVO attachDtl = attachDtlList.stream().findFirst().get();
        String savePath = fileFolder + attachDtl.getAttachDtlPath();

        Path path = Paths.get(savePath);
        try {
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("application/octet-stream"))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachDtl.getAttachDtlName() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("文件不存在或不可读");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage(), ResponseConstant.FAIL);
        }

    }

    private List<AttachDtlRoot> getAttachDtlListByFiles(MultipartFile[] files, Long attachID, String userName) {
        List<AttachDtlRoot> result = new ArrayList<>();

        String eightDigitDay = DateUtils.getCurrentEightDigitDay();

        // 获取到
        String saveFilePath =  SymbolConstant.SLASH + eightDigitDay.substring(NumberConstant.ZERO, NumberConstant.FOUR) + SymbolConstant.SLASH
                + eightDigitDay.substring(NumberConstant.FOUR, NumberConstant.SIX) + SymbolConstant.SLASH;

        for (MultipartFile file : files) {
            AttachDtlRoot attachDtl = new AttachDtlRoot();
            attachDtl.setAttachDtlID(iPrimaryKeyService.getPrimaryKey());
            attachDtl.setAttachID(attachID);
            attachDtl.setUploadStf(userName);
            attachDtl.setUploadDate(DateUtils.getCurrentFormat());

            // 获取文件名称
            String originalFilename = file.getOriginalFilename();
            attachDtl.setAttachDtlName(originalFilename);

            // 获取文件类型
            String contentType = file.getContentType();
            attachDtl.setAttachDtlContentType(file.getContentType());

            // 文件类型
            String fileType = null;
            if (StringUtils.isNotBlank(contentType) && contentType.indexOf(SymbolConstant.SLASH) > NumberConstant.NEGATIVE_ONE) {
                fileType = contentType.split(SymbolConstant.SLASH)[NumberConstant.ZERO];
            }
            attachDtl.setAttachDtlType(fileType);

            // 获取文件大小
            long size = file.getSize();
            attachDtl.setAttachDtlSize(String.valueOf(size));

            // 获取文件名尾缀
            int indexOf = originalFilename.indexOf(SymbolConstant.POINT);
            if (indexOf > NumberConstant.NEGATIVE_ONE && indexOf != originalFilename.length() - NumberConstant.ONE) {
                String fileMmt = originalFilename.substring(originalFilename.indexOf(SymbolConstant.POINT) + NumberConstant.ONE);
                attachDtl.setAttachDtlFmt(fileMmt);
            }

            String savePath = saveFilePath + DateUtils.getCurrentTimeStamp() + SymbolConstant.BLANK + originalFilename;
            attachDtl.setAttachDtlPath(savePath);

            attachDtl.setMultipartFile(file);

            result.add(attachDtl);
        }

        return result;
    }
}
