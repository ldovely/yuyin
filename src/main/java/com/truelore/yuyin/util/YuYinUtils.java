package com.truelore.yuyin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Objects;

/**
 * 语音格式转换
 * @author lisbo
 * @description
 * @since 2018-2-1 15:19
 */

public class YuYinUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(YuYinUtils.class);

    private YuYinUtils() {
    }

    public static void ffmpeg(String inputFile){
        if (Objects.equals(null,inputFile)){
            LOGGER.error("输入文件为空");
        }

        if (!checkfile(inputFile)){
            LOGGER.error("路径错误！："+inputFile);
        }else {
            YuYinUtils.process(inputFile);
        }

    }

    private static Boolean process(String filePath){
        // 文件命名
        Calendar c = Calendar.getInstance();
        String savename = String.valueOf(c.getTimeInMillis())+ Math.round(Math.random() * 100000);
        savename += ".pcm";

        try {
            Runtime runtime = Runtime.getRuntime();
            Process proce;
            String cmd = "";
            String cut = "D:\\20170104_soft\\语音识别\\ffmpeg-latest-win64-shared\\bin\\ffmpeg.exe   -i   "
                    + filePath
                    //输出pcm音频 单声道 16000 采样率 16bits编码 pcm文件
                    + " -f s16le -ac 1 -ar 16000  "
                    + savename ;
            String cutCmd = cmd + cut;
            proce = runtime.exec(cutCmd);

            int exitVal = proce.waitFor();
            LOGGER.info("Process exitValue: " + exitVal);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  false;
    }

    private static boolean checkfile(String path) {
        File file = new File(path);
        if (!file.isFile()) {
            return false;
        }
        return true;
    }

    /**
     * 加载文件
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead;
        while (offset < bytes.length
                && (numRead = is.read(bytes , offset , bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            is.close();
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
        return bytes;
    }
}
