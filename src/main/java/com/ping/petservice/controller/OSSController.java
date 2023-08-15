package com.ping.petservice.controller;

import com.ping.petservice.resp.CommonResp;
import com.ping.petservice.service.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/oss/file")
public class OSSController {

    @Resource
    private FileService fileService;

    /**
     * 文件上传
     * @param file
     * @param module
     * @return
     */
    @PostMapping("/upload")
    public CommonResp upload(MultipartFile file, String module){
        //返回上传到oss的路径
        CommonResp<Object> resp = new CommonResp<>();
        String url = fileService.upload(file,module);
        resp.setContent(url);
        resp.setMessage("文件上传成功");
        return resp;
    }

    //WangEditor上传图片
    @PostMapping(value = "/uploadImg")
    @ResponseBody
    public Map<String, Object> uploadImg(@RequestParam(value="myFileName") MultipartFile file, HttpServletRequest request) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            Map<String, String> data = new HashMap<>();
            String filename = file.getOriginalFilename();//获取图片名
            String module = filename;
            String url = fileService.upload(file,module);
            data.put("url",url);//这里应该是项目路径，返回前台url
            data.put("alt",null);
            data.put("href",null);
            map.put("errno",0);
            map.put("data",data);
            return map;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return  null;
        }
    }

}