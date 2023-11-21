package com.clean.code.springBoot2.servece;

import com.clean.code.springBoot2.domMain.FileStorAge;
import com.clean.code.springBoot2.domMain.FileStorageStatus;
import com.clean.code.springBoot2.repository.FileStorageRepository;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MultipartFilter;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class FileStorageSevice {
    private final FileStorageRepository fileStorageRepository;

    @Value("${upload.folder}")
    private String uploadFolder;

    private final Hashids hashids;

    public FileStorageSevice(FileStorageRepository fileStorageRepository) {
        this.fileStorageRepository = fileStorageRepository;
        this.hashids=new Hashids(getClass().getName(),6);
    }
    public void save(MultipartFile multipartFile){
        FileStorAge fileStorAge=new FileStorAge();
        fileStorAge.setName(multipartFile.getOriginalFilename());
        fileStorAge.setExtansion(getExt(multipartFile.getOriginalFilename()));
        fileStorAge.setFileSize(multipartFile.getSize());
        fileStorAge.setContentType(multipartFile.getContentType());
        fileStorAge.setFileStorageStatus(FileStorageStatus.DRAFT);
        fileStorageRepository.save(fileStorAge);

        Date now=new Date();
        File uploadFolder=new File(String.format("%s/upload_files/%d/%d/%d",this.uploadFolder,
                1900+now.getYear(),1+now.getMonth(),now.getDate()));
        if (!uploadFolder.exists()&&uploadFolder.mkdirs()){
            System.out.println("Files have   already created");
        }
        fileStorAge.setHashId(hashids.encode(fileStorAge.getId()));
        fileStorAge.setUploadPath(String.format("upload_files/%d/%d/%d/%s.%s",
                1900+now.getYear()
                ,1+now.getMonth()
                ,now.getDate()
        ,fileStorAge.getHashId(),fileStorAge.getExtansion()));
        fileStorageRepository.save(fileStorAge);
        uploadFolder=uploadFolder.getAbsoluteFile();
        File file=new File(uploadFolder,String.format("%s.%s",fileStorAge.getHashId(),fileStorAge.getExtansion()));

        try{
            multipartFile.transferTo(file);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void  deleteAllDraft(){
        List<FileStorAge> fileStorAgeList=fileStorageRepository.findAllByFileStorageStatus(FileStorageStatus.DRAFT);
        fileStorAgeList.forEach(fileStorAge -> delete(fileStorAge.getHashId()));
    }
    public void delete(String hashId){
        FileStorAge fileStorAge =findByHashId(hashId);
        File file=new File(String.format("%s/%s",this.uploadFolder,fileStorAge.getUploadPath()));
        if (file.delete()){
            fileStorageRepository.delete(fileStorAge);
        }
    }
    @Transactional(readOnly = true)

    public FileStorAge findByHashId(String hashId){
        return fileStorageRepository.findByHashId(hashId);
    }

    private String getExt(String fileName){
        String ext=null;
        if(fileName!=null&&!fileName.isEmpty()){
            int dot =fileName.lastIndexOf('.');
            if(dot>0&&dot<=fileName.length()-2){
                ext=fileName.substring(dot+1);
            }
        }
        return ext;

    }


}
