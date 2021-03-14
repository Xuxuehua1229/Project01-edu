package com.guli.edu.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.guli.common.constants.R;
import com.guli.edu.handler.ConstantPropertiesUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author snowflake
 * @create 2021-01-15 15:00
 */
@RestController
@RequestMapping("/edu/oss")
@CrossOrigin
public class FileUploadController {

    /**
     * 上传讲师头像
     * 注意：如果参数前写 @RequestParam("file") 用swagger测试该接口时不会显示上传文件的操作，而是接收file类型时只显示string
     * @param file
     * @return
     */
    @PostMapping("uploadImage")
    @ApiOperation(value = "图片上传")
    public R uploadTeacherImage(@ApiParam(value = "上传讲师头像",required = true)
                                    @RequestPart("file") MultipartFile file,
                                @RequestParam(value = "host",required = false) String host){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtil.ENDPOINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtil.ACCESSKEYID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESSKEYSECRET;
        // 阿里云oss bucket名，即存储的文件名
        String bucketName = ConstantPropertiesUtil.BUCKETNAME;

        try {
            // 获取文件名称
            String filename = file.getOriginalFilename();

            //获取文件名称后缀名
            String suffix = filename.split("\\.")[1];

            //使用uuid拼接文件名
            filename = UUID.randomUUID() + "." + suffix;

            //使用日期来命名存储头像的文件名
            String fileDate = new DateTime().toString("yyyy/MM/dd");
            filename = fileDate + "/" + host + "/" + filename;

            // 获取文件流
            InputStream inputStream = file.getInputStream();

            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流。(参数：① BucketName ② 要上传的文件名 ③ 文件输入流)
            ossClient.putObject(bucketName, filename, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            // 返回上传之后oss存储路径
            // https://edu-teacher0114.oss-cn-beijing.aliyuncs.com/6a6ee5b928b9e060eb7dda844751d929.jpg
            String path = "http://" + bucketName + "." + endpoint + "/" + filename;

            return R.ok().data("imgUrl",path);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }
}
