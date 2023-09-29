package com.typeface.service;

import com.typeface.entity.FileData;
import com.typeface.respository.FileDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class StorageService {

    @Autowired
    private FileDataRepository fileDataRepository;

    @Autowired
    private AWSStorageService awsStorageService;

    private final String FOLDER_PATH="/Users/javatechie/Desktop/MyFIles/";

    public String uploadImage(MultipartFile file) {
        FileData fileData = fileDataRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .size(file.getSize())
                .time(System.currentTimeMillis())
                .build());

        return awsStorageService.uploadFile(fileData.getId(), file);
    }

    public String getMediaType(String fileName) {
        Long fileId = Long.parseLong(fileName);
        Optional<FileData> fileData = fileDataRepository.findById(fileId);
        return fileData.get().getType();
    }

    public byte[] downloadImage(String fileName) {
        byte[] images = awsStorageService.downloadFile(fileName);
        return images;
    }


    public String updateFile(Long fileId, MultipartFile file) {
        Optional<FileData> optionalFileData = fileDataRepository.findById(fileId);
        FileData fileData = optionalFileData.get();

        fileData = fileDataRepository.save(FileData.builder()
                .id(fileData.getId())
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .size(file.getSize())
                .time(System.currentTimeMillis())
                .build());
        awsStorageService.deleteFile(fileId.toString());
        return awsStorageService.uploadFile(fileData.getId(), file);
    }

    public void deleteFile(String fileName) {
        Long fileId = Long.parseLong(fileName);
        fileDataRepository.deleteById(fileId);
        awsStorageService.deleteFile(fileName);
    }
}
