package com.tracetech.minioserver.controller;

import cn.hutool.core.util.StrUtil;
import com.tracetech.minioserver.common.response.CommonResult;
import com.tracetech.minioserver.entity.OperationFileObject;
import com.tracetech.minioserver.utils.MinioHelper;
import io.minio.messages.Bucket;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/file")
@Api(tags = "文件服务")
public class FileController {
    @Autowired
    private MinioHelper minioHelper;

    @PostMapping("/createBucket")
    @ApiOperation(value = "1.创建bucket")
    public CommonResult<Object> createBucket(@RequestParam String bucketName) {
        try {
            if (minioHelper.bucketExists(bucketName)) {
                return CommonResult.error("bucket已存在");
            }

            if (minioHelper.makeBucket(bucketName)) {
                return CommonResult.success("创建成功");
            } else {
                return CommonResult.success("创建失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/bucketList")
    @ApiOperation(value = "2. 获取bucket列表")
    public CommonResult<List<String>> bucketList() {
        List<String> buckets = null;
        try {
            buckets = minioHelper.listBuckets().stream().map(Bucket::name).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return CommonResult.success(buckets);
    }

    @PostMapping("/deleteBucket")
    @ApiOperation(value = "2. 删除bucket")
    public CommonResult<Object> deleteBucket(@RequestParam @ApiParam("bucket名称") String bucketName) {
        try {
            if (minioHelper.removeBucket(bucketName)) {
                return CommonResult.success("bucket删除成功");
            } else {
                return CommonResult.error("bucket删除失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/upload")
    @ApiOperation(value = "3. 文件上传")
    public CommonResult<Object> upload(@RequestParam("bucketName") @ApiParam("bucket名称") String bucketName,
                                       @RequestParam("file") @ApiParam("文件上传对象") MultipartFile multipartFile) {
        try {
            if (!minioHelper.bucketExists(bucketName)) {
                return CommonResult.error("bucket不存在");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (StrUtil.isBlank(multipartFile.getOriginalFilename())) {
            return CommonResult.error("文件名为空");
        }
        // 获取文件后缀
        String extension = multipartFile.getOriginalFilename()
                .substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + extension;
        String uploadUrl = null;
        try {
            uploadUrl = minioHelper.putObject(bucketName, multipartFile, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CommonResult.success(uploadUrl);
    }

    @PostMapping("/download")
    @ApiOperation(value = "4. 下载")
    public CommonResult<Object> download(@RequestBody OperationFileObject operationFileObject,
                                         HttpServletResponse response) {
        try {
            if (!minioHelper.bucketExists(operationFileObject.getBucketName())) {
                return CommonResult.error("bucket不存在");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            ServletOutputStream outputStream = response.getOutputStream();
            String extension = operationFileObject.getObjectName().substring(operationFileObject.getObjectName().lastIndexOf("."));
            InputStream fileInputStream = minioHelper.getObject(operationFileObject.getBucketName(),
                    operationFileObject.getObjectName());
            response.setContentType("application/octet-stream");
            response.setHeader("content-Disposition", "inline;filename=" +
                    java.net.URLEncoder.encode(UUID.randomUUID().toString() + extension, "UTF-8"));
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = fileInputStream.read(buffer)) >= 0 ) {
                outputStream.write(buffer, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.success("下载失败:", e.getMessage());
        }

        return null;
    }

    @PostMapping("/removeObject")
    @ApiOperation(value = "5. 删除文件")
    public CommonResult<Object> removeObject(@RequestBody OperationFileObject operationFileObject) {
        try {
            if (!minioHelper.bucketExists(operationFileObject.getBucketName())) {
                return CommonResult.error("bucket不存在");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            if (minioHelper.removeObject(operationFileObject.getBucketName(), operationFileObject.getObjectName())) {
                return CommonResult.success("删除成功");
            } else {
                return CommonResult.error("删除失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/objectList")
    @ApiOperation(value = "6. 根据bucketName获取所有上传文件名称")
    public CommonResult<List<String>> getObjectNameList(@RequestParam @ApiParam("bucket名称") String bucketName) {
        try {
            List<String> list = minioHelper.listObjectNames(bucketName);

            return CommonResult.success(list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
