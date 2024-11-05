package com.t1.profile.user.dto;

import com.t1.profile.user.model.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

/**
 * DTO for {@link com.t1.profile.user.model.User}
 */
@Data
@AllArgsConstructor
public class UserFullDto {

    private final Long id;

    @Size(max = 255, message = "Имя должно содержать до 255 символов")
    @NotBlank(message = "Имя не должно быть пустым")
    private final String firstName;

    @Size(max = 255, message = "Фамилия должна содержать до 255 символов")
    private final String lastName;

    private final LocalDate birthDate;

    private final Gender gender;

    @Size(max = 255, message = "Имя пользователя должно содержать до 255 символов")
    @NotBlank(message = "Имя пользователя не должно быть пустым")
    private final String username;

    @Email(message = "Неверный формат электронной почты")
    @NotBlank(message = "Электронная почта не должна быть пустой")
    private final String email;

    @Size(min = 7, max = 11, message = "Номер телефона должен содержать от 7 до 11 цифр")
    private final String phoneNumber;

    @Size(max = 255, message = "Город должен содержать до 255 символов")
    private final String city;

    @Size(max = 2048, message = "Описание должно содержать до 2048 символов")
    private final String bio;

    @Size(max = 255, message = "Контактная информация должна содержатьдо 255 символов")
    private final String messengerContact;

    @Size(max = 512, message = "Контактная информация должна содержатьдо 512 символов")
    private final String picture;

    private final boolean isVisibility;
}