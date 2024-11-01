package com.t1.profile.service;

import com.t1.profile.dto.UserDto;
import com.t1.profile.exeption.ResourceNotFoundException;
import com.t1.profile.mapper.UserMapper;
import com.t1.profile.model.Profession;
import com.t1.profile.model.User;
import com.t1.profile.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserMapper userMapper;

    private final String uploadDir = "C:\\photos"; // Путь до папки с фотографиями

    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.toDtoList(userRepo.findAll());
    }

    @Override
    public UserDto findByEmail(String email) {
        User user =  userRepo.findByEmail(email);
        return user != null ? userMapper.toDto(user) : null;
    }

    @Override
    public List<UserDto> findByProfession(Profession profession) {
        List<User> users = userRepo.findByProfession(profession);
        return userMapper.toDtoList(users);
    }

    @Override
    public UserDto findById(Integer id) {
        return userRepo.findById(id)
                .map(userMapper::toDto)
                .orElse(null);
    }


    //далее код для фото

    @Override
    public void uploadUserPhoto(Integer userId, MultipartFile file) throws IOException {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден с id " + userId));

        // Проверка файла
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Файл пустой");
        }

        // Дополнительные проверки (тип файла, размер и т.д.)
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (!isImageFile(fileName)) {
            throw new IllegalArgumentException("Неверный формат файла");
        }

        // Сохранение файла
        String fileExtension = getFileExtension(fileName);
        String newFileName = "user_" + userId + "." + fileExtension;
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve(newFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Обновление пути к файлу в сущности пользователя
        user.setPhotoPath(filePath.toString());
        userRepo.save(user);
    }

    private boolean isImageFile(String fileName) {
        String[] extensions = { "jpg", "jpeg", "png", "gif" };
        for (String ext : extensions) {
            if (fileName.toLowerCase().endsWith("." + ext)) {
                return true;
            }
        }
        return false;
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
