package com.t1.profile.controller;

import com.t1.profile.dto.UserDto;
import com.t1.profile.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.file.*;
import java.net.MalformedURLException;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000") // Добавьте эту строку
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    List<UserDto> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    UserDto getUser(@PathVariable Integer id) {
        return userService.findById(id);
    }

    //для фото
    @PostMapping("/{id}/upload-photo")
    public ResponseEntity<?> uploadPhoto(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        try {
            userService.uploadUserPhoto(id, file);
            return ResponseEntity.ok().body("Фотография успешно загружена");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка при загрузке фотографии: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/photo")
    public ResponseEntity<Resource> getUserPhoto(@PathVariable Integer id) {
        UserDto user = userService.findById(id);
        if (user == null || user.getPhotoPath() == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            Path filePath = Paths.get(user.getPhotoPath());
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                // Определяем MIME-тип файла динамически
                String contentType = Files.probeContentType(filePath);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                throw new RuntimeException("Не удается прочитать файл: " + user.getPhotoPath());
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Ошибка при получении файла: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при определении MIME-типа: " + e.getMessage());
        }
    }


}
