package com.torinosrc.controller.upload;

import com.torinosrc.commons.utils.TorinoSrcServiceExceptionUtils;
import com.torinosrc.model.view.file.FileView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by Lenovo on 2018/4/18.
 */
@Api(value = "[Torino Source]上传接口",tags = "[Torino Source]上传接口",description = "")
@RestController
@RequestMapping(value = "/api")
public class UploadController {
    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(UploadController.class);

    @Value("${pic.save.path}")
    private String savePathConfig;

    @Value("${pic.save.url}")
    private String saveUrlConfig;

    @ApiOperation(value = "上传正面身份证图", notes = "上传正面身份证图")
    @RequiresPermissions(value = {"sys:users:upload"})
    @RequestMapping(value = "/v1/upload/users/fontpic/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = {RequestMethod.POST})
    @CrossOrigin
    public ResponseEntity<?> uploadFontImage( @ApiParam(value = "用户id", required = true) @PathVariable(value = "id") Long id,HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        return upload(request, response, map, "user"+id);
    }

//    @ApiOperation(value = "上传反面身份证图", notes = "上传反面身份证图")
//    @RequiresPermissions(value = {"sys:users:upload"})
//    @RequestMapping(value = "/v1/upload/users/behindpic/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = {RequestMethod.POST})
//    @CrossOrigin
//    public ResponseEntity<?> uploadBehindImage(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
//        return upload(request, response, map, "user");
//    }

    @ApiOperation(value = "上传意见反馈图", notes = "上传意见反馈图")
    @RequiresPermissions(value = {"sys:feedback:upload"})
    @RequestMapping(value = "/v1/upload/users/feedback/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = {RequestMethod.POST})
    @CrossOrigin
    public ResponseEntity<?> uploadBehindImage(@ApiParam(value = "用户id", required = true) @PathVariable(value = "id")Long id,HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        return upload(request, response, map, "feedbackimages/feedback"+id);
    }


    @ApiOperation(value = "上传商品图", notes = "上传商品图")
    @RequiresPermissions(value = {"sys:product:upload"})
    @RequestMapping(value = "/v1/upload/product/pic", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = {RequestMethod.POST})
    @CrossOrigin
    public ResponseEntity<?> uploadProductImage(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        return upload(request, response, map, "product");
    }

    @ApiOperation(value = "上传商品分类图", notes = "上传商品分类图")
    @RequiresPermissions(value = {"sys:category:upload"})
    @RequestMapping(value = "/v1/upload/category/pic", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = {RequestMethod.POST})
    @CrossOrigin
    public ResponseEntity<?> uploadCategoryImage(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        return upload(request, response, map, "category");
    }


    /**
     * 具体保存操作
     *
     * @param request
     * @param response
     * @param map
     * @param type
     * @return
     */
    private ResponseEntity<?> upload(HttpServletRequest request, HttpServletResponse response, ModelMap map, String type) {
        MultipartHttpServletRequest mhs = (MultipartHttpServletRequest) request;
        MultipartFile file = mhs.getFile("file");
        ServletContext application = request.getSession().getServletContext();
        LOG.info("pic url: " + application.getRealPath("/"));
        String savePath = savePathConfig + "/";
        String filePath = "images/" + type + "/";
        String newFileName = "";
        try {
            if (file != null && !file.isEmpty()) {

                //检查是否有文件夹
                String folderName = savePath + "images/" + type;
                File folder = new File(folderName);
                //没有则创建
                if (!folder.exists() && !folder.isDirectory()) {
                    LOG.info("创建文件夹：" + folderName);
                    folder.mkdir();
                }

                SimpleDateFormat bartDateFormat = new SimpleDateFormat
                        ("yyyyMMddHHmmss");
                newFileName = String.valueOf(bartDateFormat.format(new Date()));
                filePath = filePath + newFileName + "_" + Math.round(Math.random()*1000);
                LOG.info("上传文件： " + filePath);
                FileUtils.writeByteArrayToFile(new File(savePath + filePath), file.getBytes());

            }
        } catch (Exception e) {
            LOG.error("Fail to upload file ",e);
        }
        FileView fileVo = new FileView();
        fileVo.setFilePath(saveUrlConfig + filePath);
        try {
            return new ResponseEntity<>(fileVo, HttpStatus.OK);
        } catch (Throwable t) {
            String error = "Failed to upload file ! upload type: " + type;
            LOG.error(error, t);
            return TorinoSrcServiceExceptionUtils.getHttpStatusWithResponseMessage(error, t);
        }

    }

}
