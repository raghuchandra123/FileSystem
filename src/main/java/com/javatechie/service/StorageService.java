package com.javatechie.service;

import com.javatechie.entity.FileData;
import com.javatechie.respository.FileDataRepository;
import com.javatechie.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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



    public byte[] downloadImage(String fileName) {
        //Optional<ImageData> dbImageData = repository.findByName(fileName);
        byte[] images = new byte[1];//ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }


    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        String filePath=FOLDER_PATH+file.getOriginalFilename();

        //FileData fileData=fileDataRepository.save(FileData.builder()
        //        .name(file.getOriginalFilename())
        //        .type(file.getContentType())
        //        .filePath(filePath).build());

        file.transferTo(new File(filePath));

        //if (fileData != null) {
        //    return "file uploaded successfully : " + filePath;
        //}
        return null;
    }

    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        //Optional<FileData> fileData = fileDataRepository.findByName(fileName);
        //String filePath=fileData.get().getFilePath();
        byte[] images = new byte[1];//Files.readAllBytes(new File(filePath).toPath());
        return images;
    }



}
