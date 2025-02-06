package com.pets.service.impl;

import com.pets.mapper.UpLoadFileMapper;
import com.pets.pojo.dto.ImagePathDTO;
import com.pets.pojo.vo.ConverseFileUploadVO;
import com.pets.service.UpLoadFileService;
import com.pets.utils.base.ResponseData;
import com.pets.utils.pic.HandlePic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(rollbackFor = Exception.class)
public class UpLoadFileImpl implements UpLoadFileService {

    @Autowired
    HandlePic handlePic;

    @Autowired
    UpLoadFileMapper upLoadFileMapper;

    @Override
    public String webUpLoad(MultipartFile file, String dir) {
        // 将 dir 中的双引号替换为空字符串，并将结果赋值给 dir
        dir = dir.replace("\"", "");
        // 将图片存入本地文件夹，获取路径
        String path = handlePic.upLoadFile(file, dir);
        return path;
    }

    @Override
    public ConverseFileUploadVO converseUpload(MultipartFile file, String dir, String fileType) {
        return handlePic.upLoadFile(file, dir, fileType);
    }

    @Override
    public String deleteFile(ImagePathDTO imagePathDTO) {
        return handlePic.deleteSavePath(imagePathDTO.getDir());
    }
}
