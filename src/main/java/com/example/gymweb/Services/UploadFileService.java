package com.example.gymweb.Services;

import com.example.gymweb.Entities.Lesson;
import com.example.gymweb.Repositories.LessonsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Transactional
public class UploadFileService {
    private static final Path FILE_FOLDER = Paths.get(System.getProperty("user.dir"), "files");
    @Autowired
    private LessonsRepository lessonsRepository;
    public String createFile(MultipartFile multipartFile, Lesson lesson) {

        String originalName = multipartFile.getOriginalFilename();

        String fileName = "file_" + UUID.randomUUID() + "_" + originalName;

        Path filePath = FILE_FOLDER.resolve(fileName);
        try {
            multipartFile.transferTo(filePath);
        } catch (Exception ex) {
            System.err.println(ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't save file locally", ex);
        }
        lesson.setFilePath(fileName);
        lessonsRepository.save(lesson);

        return fileName;
    }

    public org.springframework.core.io.Resource getFile(String fileName) {
        Path filePath = FILE_FOLDER.resolve(fileName);
        try {
            return new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Can't get local file");
        }
    }

    public void deleteFile(String file_url) {
        String[] tokens = file_url.split("/");
        String file_name = tokens[tokens.length -1 ];

        try {
            FILE_FOLDER.resolve(file_name).toFile().delete();
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Can't delete local file");
        }
    }
}
