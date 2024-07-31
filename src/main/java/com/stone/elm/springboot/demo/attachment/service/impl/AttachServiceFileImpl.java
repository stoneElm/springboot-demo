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
import com.stone.elm.springboot.demo.basictech.common.utils.*;
import com.stone.elm.springboot.demo.business.user.model.vo.UserInfoVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.IOUtils;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AttachServiceFileImpl implements IAttachFileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttachServiceFileImpl.class);

    @Value("${local.storage.folder}")
    private String fileFolder;

    @Value("${file-server.address}")
    private String fileServerAddress;

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
    public ResponseResult<List<AttachDtlRoot>> batchUpload(AttachAO attachAO, MultipartFile... files) {
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

        return ResultUtils.wrapResult(attachDtlList);
    }

    @Override
    public void download(HttpServletRequest request, HttpServletResponse response, AttachDtlAO attachDtlAO) {
        LOGGER.info("附件下载入参:{}", JsonUtil.convertObjectToJson(attachDtlAO));

        AttachDtlVO attachDtl = getAttachDtlOne(attachDtlAO);
        Resource resource = getResourceByAttachDtl(attachDtl);

        String fileName = StoneStringUtil.strUTFToISO(attachDtl.getAttachDtlName());

        InputStream inputStream = null;
        ServletOutputStream outputStream = null;
        try {
            inputStream = resource.getInputStream();
            outputStream = response.getOutputStream();
            IOUtils.copy(inputStream, outputStream);

            response.setContentType("application/force-download");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                if (outputStream != null){
                    outputStream.close();
                }
                if (inputStream != null){
                    inputStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    @Override
    public void download(HttpServletRequest request, HttpServletResponse response, Long attachDtlID) {
        AttachDtlAO attachDtlAO = new AttachDtlAO();
        attachDtlAO.setAttachDtlID(attachDtlID);
        download(request, response, attachDtlAO);
    }

    @Override
    public ResponseEntity<Resource> filePreview(Long attachDtlID) {
        LOGGER.info("附件预览入参:{}", attachDtlID);

        AttachDtlAO attachDtlAO = new AttachDtlAO();
        attachDtlAO.setAttachDtlID(attachDtlID);

        AttachDtlVO attachDtl = getAttachDtlOne(attachDtlAO);
        Resource resource = getResourceByAttachDtl(attachDtl);

        String fileName = StoneStringUtil.strUTFToISO(attachDtl.getAttachDtlName());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachDtl.getAttachDtlContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @Override
    public ResponseResult<AttachDtlVO> getDownloadUrl(AttachDtlAO attachAO) {
        LOGGER.info("获取附件预下载地址入参:{}", JsonUtil.convertObjectToJson(attachAO));

        AttachDtlVO attachDtl = getAttachDtlOne(attachAO);
        String savePath = fileFolder + attachDtl.getAttachDtlPath();

        return null;
    }

    @Override
    public ResponseResult<List<AttachDtlVO>> selectAttachDtlList(AttachDtlAO attachAO) {
        List<AttachDtlVO> resultData = getAttachDtlAll(attachAO);
        return ResultUtils.wrapResult(resultData);
    }

    @Override
    public ResponseResult<List<AttachDtlVO>> deleteAttachDtlByID(AttachDtlAO attachAO) {
        LOGGER.info("通过文件详情标识删除文件信息入参:{}", JsonUtil.convertObjectToJson(attachAO));

        if (Objects.isNull(attachAO.getAttachDtlID())) {
            throw new BusinessException("文件详情唯一标识不能为空！", ResponseConstant.FAIL);
        }

        AttachDtlVO attachDtl = getAttachDtlOne(attachAO);

        // 删除数据库
        ArrayList<AttachDtlAO> deleteAttachDtlList = new ArrayList<>();
        deleteAttachDtlList.add(attachAO);
        LOGGER.info("删除文件详情信息信息入参:{}", JsonUtil.convertObjectToJson(deleteAttachDtlList));
        Integer integer = iAttachMapper.deleteAttachDtlList(deleteAttachDtlList);
        LOGGER.info("成功删除数据:{}条", integer);

        deleteFileByPath(attachAO.getAttachDtlPath());

        AttachDtlVO attachDtlVO = new AttachDtlVO();
        BeanCopyUtil.copy(attachAO, attachDtlVO);
        ArrayList<AttachDtlVO> resultData = new ArrayList<>();
        resultData.add(attachDtlVO);
        return ResultUtils.wrapResult(resultData);
    }

    @Override
    public ResponseResult<List<AttachDtlVO>> deleteAttachDtlList(List<AttachDtlAO> deleteAttachDtlList, Boolean deleteFileFlag) {
        LOGGER.info("删除附件详情信息入参:{}", JsonUtil.convertObjectToJson(deleteAttachDtlList));

        if (CollectionUtils.isEmpty(deleteAttachDtlList)) {
            return ResultUtils.wrapResult();
        }

        List<String> deleteFilePathList = new ArrayList<>();
        if (deleteFileFlag) {
            AttachDtlAO attachDtlAO = new AttachDtlAO();
            attachDtlAO.setAttachDtlIDList(deleteAttachDtlList.stream().map(AttachDtlAO::getAttachDtlID).distinct().collect(Collectors.toList()));
            List<AttachDtlVO> data = selectAttachDtlList(attachDtlAO).getData();
            deleteFilePathList = data.stream().map(AttachDtlVO::getAttachDtlPath).distinct().collect(Collectors.toList());
        }

        Integer row = iAttachMapper.deleteAttachDtlList(deleteAttachDtlList);
        LOGGER.info("成功执行{}条数据", row);

        if (CollectionUtils.isNotEmpty(deleteFilePathList)) {
            for (String deleteFilePath : deleteFilePathList) {
                deleteFileByPath(deleteFilePath);
            }
        }

        List<AttachDtlVO> resultData = new ArrayList<>();
        BeanCopyUtil.copyList(deleteAttachDtlList, resultData, AttachDtlVO.class);

        ResponseResult<List<AttachDtlVO>> result = ResultUtils.wrapResult(resultData);

        LOGGER.info("删除附件详情表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

    @Override
    public ResponseResult<List<AttachDtlVO>> updateAttachDtlList(List<AttachDtlAO> updateAttachDtlList) {
        LOGGER.info("更新附件详情表入参:{}", JsonUtil.convertObjectToJson(updateAttachDtlList));

        if (CollectionUtils.isEmpty(updateAttachDtlList)) {
            return ResultUtils.wrapResult();
        }

        Integer row = iAttachMapper.updateAttachDtlList(updateAttachDtlList);
        LOGGER.info("成功执行{}条数据", row);

        List<AttachDtlVO> resultData = new ArrayList<>();
        BeanCopyUtil.copyList(updateAttachDtlList, resultData, AttachDtlVO.class);

        ResponseResult<List<AttachDtlVO>> result = ResultUtils.wrapResult(resultData);

        LOGGER.info("更新附件详情表出参:{}", JsonUtil.convertObjectToJson(result));
        return result;
    }

    private void deleteFileByPath(String deleteFilePath) {
        Path path = Paths.get(fileFolder + deleteFilePath);

        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new BusinessException("文件路径解析失败！", ResponseConstant.FAIL);
        }

        if (resource.exists() || resource.isReadable()) {
            try {
                File file = resource.getFile();
                LOGGER.info("开始删除文件:{}", fileFolder + deleteFilePath);
                file.delete();
            } catch (IOException e) {
                e.printStackTrace();
                throw new BusinessException("从Resource获取文件失败！", ResponseConstant.FAIL);
            }
        }
    }

    private Resource getResourceByAttachDtl(AttachDtlVO attachDtl) {
        String savePath = fileFolder + attachDtl.getAttachDtlPath();

        Path path = Paths.get(savePath);
        try {
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new BusinessException("文件不存在或不可读！", ResponseConstant.FAIL);
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

    private AttachDtlVO getAttachDtlOne(AttachDtlAO attachDtlAO) {

        if (Objects.isNull(attachDtlAO.getAttachDtlID())) {
            throw new BusinessException("附件详情标识不能为空", ResponseConstant.FAIL);
        }

        AttachDtlAO param = new AttachDtlAO();
        param.setAttachDtlID(attachDtlAO.getAttachDtlID());

        List<AttachDtlVO> attachDtlList = getAttachDtlAll(param);

        AttachDtlVO attachDtl = attachDtlList.stream().findFirst().get();

        return attachDtl;
    }

    private List<AttachDtlVO> getAttachDtlAll(AttachDtlAO attachDtlAO) {

        LOGGER.info("查询附件详情信息入参:{}", JsonUtil.convertObjectToJson(attachDtlAO));
        List<AttachDtlVO> attachDtlList = iAttachMapper.selectAttachDtlList(attachDtlAO);
        LOGGER.info("查询附件详情信息出参:{}", JsonUtil.convertObjectToJson(attachDtlList));

        if (CollectionUtils.isEmpty(attachDtlList)) {
            throw new BusinessException("不存在相应的附件详情信息，请检查！", ResponseConstant.FAIL);
        }

        return attachDtlList;
    }
}
