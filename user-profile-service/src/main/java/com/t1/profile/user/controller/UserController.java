package com.t1.profile.user.controller;

import com.t1.profile.common.web.security.details.UserDetailsImpl;
import com.t1.profile.user.dto.UserDto;
import com.t1.profile.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${app.photo.storage.path}")
    private String uploadDir;

    @GetMapping("/all")
    List<UserDto> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    UserDto getUser(@PathVariable Integer id) {
        return userService.findById(id);
    }

    private Integer getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetailsImpl) {
                Integer id = ((UserDetailsImpl) principal).getId();
                System.out.println("Authenticated user ID: " + id); // Логирование ID пользователя
                return id;
            }
        }
        System.err.println("Authentication failed or user not authenticated.");
        return null;
    }

    @PostMapping(value = "/{id}/upload-photo", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadPhoto(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        Integer currentUserId = getAuthenticatedUserId();
        if (currentUserId == null || !currentUserId.equals(id)) {
            System.err.println("Доступ запрещен для пользователя ID: " + id + ", текущий пользователь ID: " + currentUserId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Доступ запрещен");
        }

        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("Файл пустой");
            }

            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            if (!isImageFile(fileName)) {
                throw new IllegalArgumentException("Неверный формат файла");
            }

            String fileExtension = getFileExtension(fileName);
            String newFileName = "user_" + id + "." + fileExtension;
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);
            Path filePath = uploadPath.resolve(newFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            userService.updateUserPhotoPath(id, newFileName);

            System.out.println("Фото успешно загружено для пользователя ID: " + id);
            return ResponseEntity.ok().body("Фотография успешно загружена");
        } catch (Exception e) {
            System.err.println("Ошибка при загрузке фото для пользователя ID: " + id + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка при загрузке фотографии: " + e.getMessage());
        }
    }


    @GetMapping("/current/photo")
    public ResponseEntity<Resource> getCurrentUserPhoto() {
        Integer currentUserId = getAuthenticatedUserId();
        if (currentUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return getUserPhoto(currentUserId); // Используем общий метод для получения фото
    }

    @GetMapping("/{id}/photo")
    public ResponseEntity<Resource> getUserPhoto(@PathVariable Integer id) {
        try {
            String photoPath = userService.getUserPhotoPath(id);
            Path filePath = Paths.get(uploadDir).resolve(photoPath).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                System.out.println("Photo found for user ID: " + id);
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                System.err.println("Photo not found for user ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (MalformedURLException e) {
            System.err.println("Malformed URL error for user photo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private boolean isImageFile(String fileName) {
        String fileExtension = getFileExtension(fileName).toLowerCase();
        return fileExtension.equals("jpg") || fileExtension.equals("jpeg") || fileExtension.equals("png");
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
