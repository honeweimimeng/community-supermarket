package com.hone.imgserver.controller;

import com.hone.localcommons.constant.FileConstant;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.FileInputStream;

@RestController
public class MapController {
    @RequestMapping(value = "/ImgMapById",method = RequestMethod.GET,produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] ImgMapById(@RequestParam("mid")String mid) throws Exception{
        File file = new File(FileConstant.IDCARDDIR+mid);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }
}