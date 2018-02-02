package com.truelore.yuyin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.truelore.yuyin.entity.BaiDuParm;
import com.truelore.yuyin.util.YuYinUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lisbo
 * @description
 * @since 2018-2-2 10:44
 */

@Controller
@RequestMapping("index")
public class YuYinController {

    private static final Logger log = LoggerFactory.getLogger(YuYinController.class);

    @Autowired
    private BaiDuParm baiDuParm;

    @RequestMapping(value = "/yanshi")
    public String yanShi(ModelMap map){
        String filePath =baiDuParm.getFilePath() ;
        log.info(filePath);
        map.addAttribute("filePath",filePath);

        return "yanshi";
    }

}
