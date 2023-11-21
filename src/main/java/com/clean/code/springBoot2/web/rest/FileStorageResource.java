package com.clean.code.springBoot2.web.rest;


import com.clean.code.springBoot2.domMain.FileStorAge;
import com.clean.code.springBoot2.servece.FileStorageSevice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api")
public class FileStorageResource {
    private final FileStorageSevice fileStorageSevice;

    @Value("${upload.folder}")
    private String uploadFolder;

    public FileStorageResource(FileStorageSevice fileStorageSevice) {
        this.fileStorageSevice = fileStorageSevice;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file")MultipartFile multipartFile){
        fileStorageSevice.save(multipartFile);
        return ResponseEntity.ok(multipartFile.getOriginalFilename()+" file is save");
    }

    @GetMapping("/preview/{hashId}")
    public ResponseEntity previewFile(@PathVariable String hashId) throws IOException {
        FileStorAge fileStorAge=fileStorageSevice.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"inline; fileName= \""+ URLEncoder.encode(fileStorAge.getName()))
                .contentType(MediaType.parseMediaType(fileStorAge.getContentType()))
                .contentLength(fileStorAge.getFileSize())
                .body(new FileUrlResource(String.format("%s/%s",uploadFolder,fileStorAge.getUploadPath())));
    }
    @GetMapping("/downloaod/{hashId}")
    public ResponseEntity downloaodFile(@PathVariable String hashId) throws IOException {
        FileStorAge fileStorAge=fileStorageSevice.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName= \""+ URLEncoder.encode(fileStorAge.getName()))
                .contentType(MediaType.parseMediaType(fileStorAge.getContentType()))
                .contentLength(fileStorAge.getFileSize())
                .body(new FileUrlResource(String.format("%s/%s",uploadFolder,fileStorAge.getUploadPath())));
    }
    @DeleteMapping("/delete/{hashId}")
    public ResponseEntity<String>delete(@PathVariable String hashId){
        fileStorageSevice.delete(hashId);
        return ResponseEntity.ok("file is delete");
    }
}
