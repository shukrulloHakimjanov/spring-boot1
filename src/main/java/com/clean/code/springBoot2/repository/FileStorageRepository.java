package com.clean.code.springBoot2.repository;

import com.clean.code.springBoot2.domMain.FileStorAge;
import com.clean.code.springBoot2.domMain.FileStorageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorAge,Long> {
    FileStorAge findByHashId(String hashid);

    List<FileStorAge>findAllByFileStorageStatus(FileStorageStatus status);
}
