package com.example.gymweb.Services;

import com.example.gymweb.Entities.User;
import com.example.gymweb.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
//This class might be used in the future
//Class which helps to upload images in the web
public class UploadFileService {
    private static final Path IMAGES_FOLDER = Paths.get(System.getProperty("user.dir"), "images");

    private static final Path IMAGENES_CARPETA = Paths.get(System.getProperty("user.dir"), "imagenes");

    public String saveImage(MultipartFile archivo) throws IOException {

        String nombreOriginal = archivo.getOriginalFilename();

        if (!nombreOriginal.matches(".*\\.(jpg|jpeg|gif|png)")) {
            throw new RuntimeException("El archivo no es una imagen");
        }

        String nombreAleatorio = "imagen_" + UUID.randomUUID() + "_" + nombreOriginal;

        Path rutaImagen = IMAGENES_CARPETA.resolve(nombreAleatorio);

        archivo.transferTo(rutaImagen);

        return nombreAleatorio;
    }

}