package com.fjh.file.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
public class FileController {

    @Value("${file.dir}")
    private String fileStorageDir;

    @ApiOperation(value = "上传文件", notes = "上传文件")
    @PostMapping(value = "/upload", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    @ResponseBody
    public String fileUpload(@ApiParam(required = true, name = "file", value = "上传文件流") @RequestParam("file") MultipartFile file) {
        String filePath = storeFile(file);
        return filePath;
    }

    /**
     * 存储文件到系统
     *
     * @param file 文件
     * @return 文件名
     */
    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path fileStorageLocation = Paths.get(fileStorageDir).toAbsolutePath().normalize();
        try {
            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
//            log.error("Could not store file " + fileName + ". Please try again!", ex);
        }
        return null;
    }


    /**
     * 加载文件
     *
     * @param fileName 文件名
     * @return 文件
     */
    public Resource loadFileAsResource(String fileName) {
        try {
            Path fileStorageLocation = Paths.get(fileStorageDir).toAbsolutePath().normalize();
            Path filePath = fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
//                log.error("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
//            log.error("File not found " + fileName, ex);
        }
        return null;
    }
}
