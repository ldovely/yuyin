package com.truelore.yuyin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.truelore.yuyin.entity.BaiDuParm;
import com.truelore.yuyin.util.YuYinUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Encoder;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
@EnableAutoConfiguration
@RestController
@RequestMapping ("/")
public class YuyinApplication {

    private static final Logger log = LoggerFactory.getLogger(YuyinApplication.class);

    @Autowired
    private BaiDuParm baiDuParm;

    public static void main(String[] args) {
        SpringApplication.run(YuyinApplication.class , args);

//		String file = "C:\\Users\\Administrator.PC-20161105QIRJ\\Desktop\\input\\fangyan.m4a";
//		YuYinUtils.ffmpeg(file);
    }


    @RequestMapping (value = "/getBaiDuToken", method = RequestMethod.GET)
    public String getBaiDuToken() {
        String getTokenUrl = baiDuParm.getAuthUrl() + "?grant_type=client_credentials&client_id=" + baiDuParm.getApiKey() + "&client_secret=" + baiDuParm.getSecretkey();
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(2000);
        factory.setReadTimeout(2000);
        RestTemplate restTemplate = new RestTemplate(factory);
        Map map = restTemplate.getForObject(getTokenUrl , Map.class);

        return String.valueOf(map.get("access_token"));
    }

    @RequestMapping (value = "/yuYinShiBie", method = RequestMethod.GET)
    public String yuYinShiBie() {
        File file = new File(baiDuParm.getFilePath());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType("audio/wav;rate=16000"));
        //设置参数
        Map < String, Object > hashMap = new HashMap <>();
        //中文=zh、粤语=ct、英文=en
        hashMap.put("lan" , "zh");
        hashMap.put("format" , "wav");
        hashMap.put("rate" , "16000");
        hashMap.put("channel" , "1");
        hashMap.put("len" , file.length());
        hashMap.put("cuid" , baiDuParm.getCuid());
        hashMap.put("token" , "24.147eec030f9de8a2ad8749c233afd623.2592000.1520066664.282335-10781516");

        try {
            hashMap.put("speech" , DatatypeConverter.printBase64Binary(YuYinUtils.loadFile(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpEntity < String > requestEntity = new HttpEntity <>(JSON.toJSONString(hashMap) , httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1 , new StringHttpMessageConverter(StandardCharsets.UTF_8));
        ResponseEntity < String > resp = restTemplate.exchange(baiDuParm.getYuYinUrl() , HttpMethod.POST , requestEntity , String.class);

        log.info(resp.getBody());
        String result = resp.getBody();
        JSONObject re = JSON.parseObject(result);

        String word = re.get("result").toString().replace("[\"","").replace("\"]","");
        //获得返回值
        return word;

    }

    @RequestMapping (value = "/fangyan", method = RequestMethod.GET)
    public String fangYan() {
        File file = new File(baiDuParm.getFilePath());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType("audio/pcm;rate=16000"));
        //设置参数
        Map< String, Object > hashMap = new HashMap<>();
        //中文=zh、粤语=ct、英文=en
        hashMap.put("lan" , "zh");
        hashMap.put("format" , "pcm");
        hashMap.put("rate" , "16000");
        hashMap.put("channel" , "1");
        hashMap.put("len" , file.length());
        hashMap.put("cuid" , baiDuParm.getCuid());
        hashMap.put("token" , "24.147eec030f9de8a2ad8749c233afd623.2592000.1520066664.282335-10781516");

        try {
            hashMap.put("speech" , DatatypeConverter.printBase64Binary(YuYinUtils.loadFile(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpEntity< String > requestEntity = new HttpEntity <>(JSON.toJSONString(hashMap) , httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1 , new StringHttpMessageConverter(StandardCharsets.UTF_8));
        ResponseEntity< String > resp = restTemplate.exchange(baiDuParm.getYuYinUrl() , HttpMethod.POST , requestEntity , String.class);

        log.info(resp.getBody());
        String result = resp.getBody();
        JSONObject re = JSON.parseObject(result);

        String word = re.get("result").toString().replace("[\"","").replace("\"]","");
        //获得返回值
        return word;

    }


}
