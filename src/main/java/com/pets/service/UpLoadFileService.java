package com.pets.service;

import com.pets.pojo.dto.ImagePathDTO;
import com.pets.pojo.vo.ConverseFileUploadVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UpLoadFileService {

    String webUpLoad(MultipartFile file, String dir);

    ConverseFileUploadVO converseUpload(MultipartFile file, String dir, String fileType);

    String deleteFile(ImagePathDTO imagePathDTO);

}
