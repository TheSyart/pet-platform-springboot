package com.pets.utils.common;

import com.pets.pojo.entity.ProcessVideoFile;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Component
public class VideoUtil {
    private final CommonUtils commonUtils = new CommonUtils();

    // 假设音频是16K采样率、16位PCM编码、立体声（你可根据实际情况修改这些参数）
    private static final int SAMPLE_RATE_IN_HZ = 16000;
    private static final int AUDIO_ENCODING = 16; // 对应16位PCM编码，这里简单用数字表示方便计算
    private static final int NUM_CHANNELS = 2; // 立体声，双声道

    // 处理MultipartFile并返回包含视频时长和第一帧缩略图文件的对象
    public ProcessVideoFile processVideoFile(String path) {
        try {
            File videoFile = new File(path);
            if (!videoFile.exists()) {
                // 文件不存在则直接返回null，可以根据实际情况决定是否抛出异常等更合适的处理方式
                return null;
            }
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoFile);
            grabber.start();
            double duration = grabber.getLengthInTime() / (double) 1000000;  // 转换为秒
            // 使用Math.round方法进行四舍五入取整
            long roundedDuration = Math.round(duration);



            Frame frame = grabber.grabImage();
            File thumbnailFile = null;
            if (frame != null) {
                Java2DFrameConverter converter = new Java2DFrameConverter();
                BufferedImage bufferedImage = converter.getBufferedImage(frame);
                thumbnailFile = File.createTempFile("thumbnail", ".jpg");
                ImageIO.write(bufferedImage, "jpg", thumbnailFile);
            }
            grabber.stop();
            grabber.release();

            ProcessVideoFile processVideoFileObj = new ProcessVideoFile();
            processVideoFileObj.setFile(thumbnailFile);
            processVideoFileObj.setVideoLength(commonUtils.convertMillisToTimeFormat(roundedDuration));
            return processVideoFileObj;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 计算MultipartFile类型的PCM音频文件长度（单位：秒）
     *
     * @param path 传入的PCM音频的路径
     * @return 音频文件长度（秒），若文件不存在返回 -1，表示无效的时长，若出现其他异常则返回 "0s"
     */
    public String calculateLength(String path) {
        try {
            File audioFile = new File(path);
            if (!audioFile.exists()) {
                // 文件不存在返回 -1，表示无效的时长
                return null;
            }

            // 读取文件获取采样点数
            int numSamples = getSamplePoints(audioFile);

            // 根据采样率、声道数、采样点数计算音频长度
            double length = (double) numSamples / (SAMPLE_RATE_IN_HZ * NUM_CHANNELS);
            // 使用Math.round方法进行四舍五入取整
            long roundedDuration = Math.round(length);

            return String.valueOf(roundedDuration) + "S";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从PCM音频文件中获取采样点数
     *
     * @param file PCM音频文件
     * @return 采样点数
     * @throws IOException 若读取文件出现异常则抛出
     */
    private static int getSamplePoints(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            // 计算文件总字节数，这里假设文件只包含音频数据部分（无文件头等复杂结构）
            long fileLength = file.length();
            // 根据编码位数和声道数确定每个采样点占用的字节数
            int bytesPerSample = AUDIO_ENCODING / 8 * NUM_CHANNELS;
            return (int) (fileLength / bytesPerSample);
        }
    }

}