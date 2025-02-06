package com.pets.utils.pic;

import com.pets.pojo.entity.ProcessVideoFile;
import com.pets.pojo.vo.ConverseFileUploadVO;
import com.pets.utils.base.BaseErrorException;
import com.pets.utils.common.GetLocalIPAddress;
import com.pets.utils.common.VideoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class HandlePic {

    @Value("${server-ip}")
    private String serverIpAddress;

    @Value("${spring.web.resources.static-locations}")
    private String uploadDir;

    @Value("${server.port}")
    private String port;

    @Autowired
    GetLocalIPAddress getLocalIPAddress;

    @Autowired
    VideoUtil videoUtil;
    /**
     * 照片/视频
     * 数据库存储路径: /server-resource/emp/66b8276b-b7c1-4398-81e9-b56461a4d06f.jpg
     * 前端得到之后路径: http://36.7.64.148:8080/server-resource/emp/79b1323f-617a-4266-975d-6b5282a11327.jpg
     * linux存储地址: /data/server-resource
     * windows存储地址: D:\work\server-resource
     */


    /**
     * 多种类型复杂的 存入文件
     */
    public ConverseFileUploadVO upLoadFile(MultipartFile file, String endpoint, String fileType) {
        String resultPath = "";
        String path = uploadDir;
        // 使用 Paths.get 构造 uploadDir，避免重复拼接
        String uploadPath = Paths.get(path, endpoint).toString();

        // 生成唯一文件名
        String fileName = UUID.randomUUID().toString() + fileType; // 根据图片格式设置文件扩展名
        String filePath = Paths.get(uploadPath, fileName).toString();

        try {
            // 创建目录（如果不存在）
            File uploadDirFile = new File(uploadPath);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs(); // 创建目录
            }

            // 将 MultipartFile 写入文件
            file.transferTo(new File(filePath));
            resultPath = transformSavePath(filePath);
            System.out.println("成功将照片存储至 ：" + resultPath);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


        ConverseFileUploadVO converseFileUploadVO = new ConverseFileUploadVO();
        converseFileUploadVO.setMessage(fixPath(resultPath));

        if (".jpg".equals(fileType)) {
            converseFileUploadVO.setMessageCover(null);
            converseFileUploadVO.setMessageLength(null);
        } else if (".mp4".equals(fileType)) {
            // MultipartFile 的 MP4文件
            ProcessVideoFile processVideoFile = videoUtil.processVideoFile(filePath);
            converseFileUploadVO.setMessageLength(processVideoFile.getVideoLength());
            String savePath = saveCommonFile(processVideoFile.getFile(), "converse/video/cover", ".jpg");
            converseFileUploadVO.setMessageCover(fixPath(savePath));
        } else if (".pcm".equals(fileType)) {
            converseFileUploadVO.setMessageCover(null);
            converseFileUploadVO.setMessageLength(String.valueOf(videoUtil.calculateLength(filePath)));
        }
        return converseFileUploadVO;
    }


    /**
     * 通用简单的 存入MultipartFile文件，存储文件
     */
    public String upLoadFile(MultipartFile file, String dir) {
        // 获取上传目录
        String path = uploadDir;
        String uploadPath = Paths.get(path, dir).toString(); // 使用 Paths.get 构造路径
        System.out.println("路径: " + uploadPath);
        // 生成唯一文件名
        String fileName = UUID.randomUUID().toString() + ".jpg"; // 假设上传的是 JPG 格式
        String filePath = Paths.get(uploadPath, fileName).toString();

        // 保存文件
        try {
            // 创建目录（如果不存在）
            File uploadDirFile = new File(uploadPath);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs(); // 创建目录
            }

            // 将 MultipartFile 写入文件
            file.transferTo(new File(filePath));
            String resultPath = transformSavePath(filePath);
            System.out.println("成功将照片存储至 ：" + resultPath);
            return resultPath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通用简单的 file，存储文件
     */
    public String saveCommonFile(File file, String dir, String fileType) {
        // 获取上传目录
        String path = uploadDir;
        String uploadPath = Paths.get(path, dir).toString(); // 使用 Paths.get 构造路径
        System.out.println("路径: " + uploadPath);

        // 生成唯一文件名
        String fileName = UUID.randomUUID().toString() + fileType; // 假设上传的是 JPG 格式
        String filePath = Paths.get(uploadPath, fileName).toString();

        // 保存文件
        try {
            // 创建目录（如果不存在）
            File uploadDirFile = new File(uploadPath);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs(); // 创建目录
            }

            // 将file存储
            Path targetPath = Paths.get(filePath);
            Files.copy(file.toPath(), targetPath); // 复制文件内容到目标路径
            String resultPath = targetPath.toString();
            System.out.println("成功将照片存储至 ：" + resultPath);
            return transformSavePath(resultPath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 返回给前端照片路径时，补全路径
     */
    public String fixPath(String path) {
//        return !StringUtils.isEmpty(path) ? "http://" + serverIpAddress + ":" + this.port + path : "";
        return path;
    }

    /**
     * 截取一部分路径，保留到数据库
     */
    public String transformSavePath(String path) {
        if (path == null || path.isEmpty()) {
            throw new BaseErrorException("路径不能为空");
        }

        // 将路径中的 \ 转换为 /
        path = path.replace("\\", "/");

        // 去除路径前面的多余部分，只保留 "/server-resource/" 之后的部分
        if (path.contains("/server-resource")) {
            path = path.substring(path.indexOf("/server-resource"));
        } else {
            throw new BaseErrorException("路径不规范");
        }

        // 返回处理后的路径
        return path;
    }

    /**
     * 传递路径，删除文件中存储照片，
     */
    public String deleteSavePath(String path) {
        if (path == null || path.isEmpty()) {
            throw new BaseErrorException("路径不能为空");
        }

        // 判断路径是否包含 "/server-resource"，如果包含则进行处理
        if (path.contains("/server-resource")) {



            // 查找 "/server-resource/" 的结束位置
            int startIndex = path.indexOf("/server-resource") + "/server-resource".length();
            // 截取从 "/server-resource/" 之后的字符串
            path = path.substring(startIndex);
            System.out.println("截取路径：" + path);

        } else {
            throw new BaseErrorException("路径不规范");
        }

        // 根据操作系统拼接正确的存储路径
        String savePath;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            // Windows路径

            // 将路径中的 / 转换为 \
            path = path.replace("/", "\\");

            savePath = uploadDir + path;
        } else {
            // Linux路径
            savePath = uploadDir + path;
        }

        System.out.println("存储路径：" + savePath);

        // 删除文件操作（可以使用 File 类来实现）
        File file = new File(savePath);
        if (file.exists() && file.isFile()) {
            boolean deleted = file.delete();
            if (deleted) {
                System.out.println("文件删除成功！");
            } else {
                throw new BaseErrorException("删除文件失败");
            }
        } else {
            throw new BaseErrorException("文件不存在");
        }

        return null;
    }
}
