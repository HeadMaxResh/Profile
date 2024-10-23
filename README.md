AuthController

1. POST /auth/register registerUser

Вход: RegistrationDto

Возвращает: ApiDto

Описание: Регистрирует нового пользователя.

2. POST /auth/login authenticateUser

Вход: LoginDto

Возвращает: JwtAuthenticationDto

Описание: Аутентифицирует пользователя и возвращает JWT.

HardSkillController

1. POST /hard-skills/add addHardSkill

Вход: HardSkillDto

Возвращает: HardSkill

Описание: Добавляет новый хардскилл.

2. PUT /hard-skills/{hardSkillId}/update updateHardSkill

Вход: hardSkillId, HardSkillDto

Возвращает: HardSkill

Описание: Обновляет существующий хардскилл по ID.

3. DELETE /hard-skills/{hardSkillId}/delete deleteHardSkill

Вход: hardSkillId

Возвращает: Void

Описание: Удаляет хардскилл по ID.

ProfessionController

1. POST /professions/add addProfession

Вход: ProfessionDto

Возвращает: Profession

Описание: Добавляет новую профессию.

2. POST /professions/{professionId}/add-new-hard-skill addHardSkillToProfession

Вход: professionId, HardSkillDto

Возвращает: HardSkill

Описание: Добавляет новый хардскилл к профессии по ID.

3. POST /professions/{professionId}/add-existing-hard-skill/{hardSkillId} addExistingHardSkillToProfession

Вход: professionId, hardSkillId

Возвращает: HardSkill

Описание: Добавляет существующий хардскилл к профессии по ID.

4. DELETE /professions/{professionId}/remove-hard-skills/{hardSkillId} removeHardSkillFromProfession

Вход: professionId, hardSkillId

Возвращает: Void

Описание: Удаляет хардскилл из профессии по ID.

5. DELETE /professions/{professionId}/delete deleteProfession

Вход: professionId

Возвращает: Void

Описание: Удаляет профессию по ID.

6. GET /professions/{professionId}/hard-skills getHardSkillsByProfession

Вход: professionId

Возвращает: Set<HardSkill>

Описание: Получает хардскиллы, связанные с профессией по ID.

SoftSkillController

1.POST /soft-skill/category/add addCategory

Вход: SoftSkillCategoryDto

Возвращает: SoftSkillCategory

Описание: Добавляет новую категорию софт-скиллов.

2. DELETE /soft-skill/category/{categoryId}/delete deleteCategory

Вход: categoryId

Возвращает: Void

Описание: Удаляет категорию софт-скиллов по ID.

3. POST /soft-skill/add addSoftSkill

Вход: SoftSkillDto

Возвращает: SoftSkill

Описание: Добавляет новый софт-скилл.

4. DELETE /soft-skill/{softSkillId}/delete deleteSoftSkill

Вход: softSkillId

Возвращает: Void

Описание: Удаляет софт-скилл по ID.

UserController

1. GET /users/ getAll

Вход: Void

Возвращает: List<UserDto>

Описание: Получает список всех пользователей.

UserHardSkillController

1. GET /users/{userId}/hard-skills/ getHardSkillsByUser

Вход: userId

Возвращает: Set<HardSkill>

Описание: Получает хардскиллы пользователя по ID.

2.POST /users/{userId}/hard-skills/{hardSkillId}/add addHardSkillToUser

Вход: userId, hardSkillId

Возвращает: UserDto

Описание: Добавляет хардскилл пользователю по его ID.

3. DELETE /users/{userId}/hard-skills/{hardSkillId}/delete removeHardSkillFromUser

Вход: userId, hardSkillId

Возвращает: Void

Описание: Удаляет хардскилл у пользователя по его ID.

4. GET /users/{userId}/hard-skills/user-profession getUserAndProfessionHardSkills

Вход: userId

Возвращает: UserHardSkillsDto

Описание: Получает хардскиллы пользователя и его профессии по ID пользователя.

5. GET /users/{userId}/hard-skills/user-profession/{professionId} getUserAndProfessionHardSkills

Вход: userId, professionId

Возвращает: UserHardSkillsDto

Описание: Получает хардскиллы пользователя и его профессии по ID пользователя и профессии.

UserProfessionController

1. POST /users/{userId}/profession/{professionId}/add addProfessionToUser

Вход: userId, professionId

Возвращает: UserDto

Описание: Добавляет профессию пользователю по ID.

2. PUT /users/{userId}/profession/{professionId}/update updateProfessionForUser

Вход: userId, professionId

Возвращает: UserDto

Описание: Обновляет профессию пользователя.

3. UserSoftSkillController

POST /soft-skill-rating/add rateSoftSkill

Вход: SoftSkillRatingDto

Возвращает: SoftSkillRating

Описание: Оценивает софт-скилл.

4. GET /soft-skill-rating/soft-skill/{softSkillId} getRatingBySoftSkill

Вход: softSkillId

Возвращает: List<SoftSkillRating>

Описание: Получает рейтинги по софт-скиллу по ID.
