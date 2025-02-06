package com.pets.controller;

import com.pets.pojo.dto.ImagePathDTO;
import com.pets.service.UpLoadFileService;
import com.pets.utils.base.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@Slf4j
@RequestMapping("/handlePic")
@RestController
public class UpLoadFileController {

    @Autowired
    UpLoadFileService upLoadFileService;

    @PostMapping("/uploadPic")
    public ResponseData webUpload(@Param("file") MultipartFile file,
                                  @Param("dir") String dir) {
        return ResponseData.OK(upLoadFileService.webUpLoad(file, dir));
    }

    @PostMapping("/converseUpload")
    public ResponseData converseUpload(@Param("file") MultipartFile file,
                                      @Param("dir") String dir,
                                      @Param("fileType") String fileType) {
        return ResponseData.OK(upLoadFileService.converseUpload(file, dir, fileType));
    }

    @PostMapping("/delete")
    public ResponseData deleteFile(@RequestBody ImagePathDTO imagePathDTO){
        return ResponseData.OK(upLoadFileService.deleteFile(imagePathDTO));
    }

}
