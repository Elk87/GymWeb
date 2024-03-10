package com.example.gymweb.Services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Transactional
public class UploadFileService {
    private final String folder="/img/";
    public String saveImage(MultipartFile image) throws IOException {
        if(!image.isEmpty()){
            byte[] bytes= image.getBytes();
            Path path= Paths.get(folder+image.getOriginalFilename());
            Files.write(path,bytes);
            return image.getOriginalFilename();
        }
        return "default.jpg";
    }
    public void deleteImage(String image) throws IOException {
        Path path= Paths.get(folder+image);
        Files.deleteIfExists(path);
    }
}