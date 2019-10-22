package com.tbst.kjd.web.rest

import com.tbst.kjd.KotlinJhipsterDemoApp
import com.tbst.kjd.config.DEFAULT_LANGUAGE
import com.tbst.kjd.domain.Authority
import com.tbst.kjd.domain.User
import com.tbst.kjd.repository.AuthorityRepository
import com.tbst.kjd.repository.UserRepository
import com.tbst.kjd.security.ADMIN
import com.tbst.kjd.security.USER
import com.tbst.kjd.service.MailService
import com.tbst.kjd.service.UserService
import com.tbst.kjd.service.dto.PasswordChangeDTO
import com.tbst.kjd.service.dto.UserDTO
import com.tbst.kjd.web.rest.errors.ExceptionTranslator
import com.tbst.kjd.web.rest.vm.KeyAndPasswordVM
import com.tbst.kjd.web.rest.vm.ManagedUserVM
import org.apache.commons.lang3.RandomStringUtils

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional

import java.time.Instant
import java.util.Optional

import org.assertj.core.api.Assertions.assertThat
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import com.nhaarman.mockitokotlin2.whenever
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.any

/**
 * Integrations tests for the [AccountResource] REST controller.
 */
@SpringBootTest(classes = [KotlinJhipsterDemoApp::class])
class AccountResourceIT {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var authorityRepository: AuthorityRepository

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var httpMessageConverters: Array<HttpMessageConverter<*>>

    @Autowired
    private lateinit var exceptionTranslator: ExceptionTranslator

    @Mock
    private lateinit var mockUserService: UserService

    @Mock
    private lateinit var mockMailService: MailService

    private lateinit var restMvc: MockMvc

    private lateinit var restUserMockMvc: MockMvc

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
        doNothing().whenever(mockMailService).sendActivationEmail(any())
        val accountResource = AccountResource(userRepository, userService, mockMailService)

        val accountUserMockResource =
            AccountResource(userRepository, mockUserService, mockMailService)
        this.restMvc = MockMvcBuilders.standaloneSetup(accountResource)
            .setMessageConverters(*httpMessageConverters)
            .setControllerAdvice(exceptionTranslator)
            .build()
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(accountUserMockResource)
            .setControllerAdvice(exceptionTranslator)
            .build()
    }

    @Test
    @Throws(Exception::class)
    fun testNonAuthenticatedUser() {
        restUserMockMvc.perform(
            get("/api/authenticate")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().string(""))
    }

    @Test
    @Throws(Exception::class)
    fun testAuthenticatedUser() {
        restUserMockMvc.perform(get("/api/authenticate")
            .with { request ->
                request.remoteUser = "test"
                request
            }
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().string("test"))
    }

    @Test
    @Throws(Exception::class)
    fun testGetExistingAccount() {
        val authorities = mutableSetOf(Authority(name = ADMIN))

        val user = User(
            login = "test",
            firstName = "john",
            lastName = "doe",
            email = "john.doe@jhipster.com",
            imageUrl = "http://placehold.it/50x50",
            langKey = "en",
            authorities = authorities
        )
        whenever(mockUserService.getUserWithAuthorities()).thenReturn(Optional.of(user))

        restUserMockMvc.perform(
            get("/api/account")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("\$.login").value("test"))
            .andExpect(jsonPath("\$.firstName").value("john"))
            .andExpect(jsonPath("\$.lastName").value("doe"))
            .andExpect(jsonPath("\$.email").value("john.doe@jhipster.com"))
            .andExpect(jsonPath("\$.imageUrl").value("http://placehold.it/50x50"))
            .andExpect(jsonPath("\$.langKey").value("en"))
            .andExpect(jsonPath("\$.authorities").value(ADMIN))
    }

    @Test
    @Throws(Exception::class)
    fun testGetUnknownAccount() {
        whenever(mockUserService.getUserWithAuthorities()).thenReturn(Optional.empty())

        restUserMockMvc.perform(
            get("/api/account")
                .accept(MediaType.APPLICATION_PROBLEM_JSON)
        )
            .andExpect(status().isInternalServerError)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun testRegisterValid() {
        val validUser = ManagedUserVM().apply {
            login = "test-register-valid"
            password = "password"
            firstName = "Alice"
            lastName = "Test"
            email = "test-register-valid@example.com"
            imageUrl = "http://placehold.it/50x50"
            langKey = DEFAULT_LANGUAGE
            authorities = setOf(USER)
        }
        assertThat(userRepository.findOneByLogin("test-register-valid").isPresent).isFalse()

        restMvc.perform(
            post("/api/register")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(validUser))
        )
            .andExpect(status().isCreated)

        assertThat(userRepository.findOneByLogin("test-register-valid").isPresent).isTrue()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun testRegisterInvalidLogin() {
        val invalidUser = ManagedUserVM().apply {
            login = "funky-log!n" // <-- invalid
            password = "password"
            firstName = "Funky"
            lastName = "One"
            email = "funky@example.com"
            activated = true
            imageUrl = "http://placehold.it/50x50"
            langKey = DEFAULT_LANGUAGE
            authorities = setOf(USER)
        }

        restUserMockMvc.perform(
            post("/api/register")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(invalidUser))
        )
            .andExpect(status().isBadRequest)

        val user = userRepository.findOneByEmailIgnoreCase("funky@example.com")
        assertThat(user.isPresent).isFalse()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun testRegisterInvalidEmail() {
        val invalidUser = ManagedUserVM().apply {
            login = "bob"
            password = "password"
            firstName = "Bob"
            lastName = "Green"
            email = "invalid" // <-- invalid
            activated = true
            imageUrl = "http://placehold.it/50x50"
            langKey = DEFAULT_LANGUAGE
            authorities = setOf(USER)
        }

        restUserMockMvc.perform(
            post("/api/register")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(invalidUser))
        )
            .andExpect(status().isBadRequest)

        val user = userRepository.findOneByLogin("bob")
        assertThat(user.isPresent).isFalse()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun testRegisterInvalidPassword() {
        val invalidUser = ManagedUserVM().apply {
            login = "bob"
            password = "123" // password with only 3 digits
            firstName = "Bob"
            lastName = "Green"
            email = "bob@example.com"
            activated = true
            imageUrl = "http://placehold.it/50x50"
            langKey = DEFAULT_LANGUAGE
            authorities = setOf(USER)
        }

        restUserMockMvc.perform(
            post("/api/register")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(invalidUser))
        )
            .andExpect(status().isBadRequest)

        val user = userRepository.findOneByLogin("bob")
        assertThat(user.isPresent).isFalse()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun testRegisterNullPassword() {
        val invalidUser = ManagedUserVM().apply {
            login = "bob"
            password = null // invalid null password
            firstName = "Bob"
            lastName = "Green"
            email = "bob@example.com"
            activated = true
            imageUrl = "http://placehold.it/50x50"
            langKey = DEFAULT_LANGUAGE
            authorities = setOf(USER)
        }

        restUserMockMvc.perform(
            post("/api/register")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(invalidUser))
        )
            .andExpect(status().isBadRequest)

        val user = userRepository.findOneByLogin("bob")
        assertThat(user.isPresent).isFalse()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun testRegisterDuplicateLogin() {
        // First registration
        val firstUser = ManagedUserVM().apply {
            login = "alice"
            password = "password"
            firstName = "Alice"
            lastName = "Something"
            email = "alice@example.com"
            imageUrl = "http://placehold.it/50x50"
            langKey = DEFAULT_LANGUAGE
            authorities = setOf(USER)
        }

        // Duplicate login, different email
        val secondUser = ManagedUserVM().apply {
            login = firstUser.login
            password = firstUser.password
            firstName = firstUser.firstName
            lastName = firstUser.lastName
            email = "alice2@example.com"
            imageUrl = firstUser.imageUrl
            langKey = firstUser.langKey
            createdBy = firstUser.createdBy
            createdDate = firstUser.createdDate
            lastModifiedBy = firstUser.lastModifiedBy
            lastModifiedDate = firstUser.lastModifiedDate
            authorities = firstUser.authorities?.toMutableSet()
        }

        // First user
        restMvc.perform(
            post("/api/register")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(firstUser))
        )
            .andExpect(status().isCreated)

        // Second (non activated) user
        restMvc.perform(
            post("/api/register")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(secondUser))
        )
            .andExpect(status().isCreated)

        val testUser = userRepository.findOneByEmailIgnoreCase("alice2@example.com")
        assertThat(testUser.isPresent).isTrue()
        testUser.get().activated = true
        userRepository.save(testUser.get())

        // Second (already activated) user
        restMvc.perform(
            post("/api/register")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(secondUser))
        )
            .andExpect(status().is4xxClientError)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun testRegisterDuplicateEmail() {
        // First user
        val firstUser = ManagedUserVM().apply {
            login = "test-register-duplicate-email"
            password = "password"
            firstName = "Alice"
            lastName = "Test"
            email = "test-register-duplicate-email@example.com"
            imageUrl = "http://placehold.it/50x50"
            langKey = DEFAULT_LANGUAGE
            authorities = setOf(USER)
        }

        // Register first user
        restMvc.perform(
            post("/api/register")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(firstUser))
        )
            .andExpect(status().isCreated)

        val testUser1 = userRepository.findOneByLogin("test-register-duplicate-email")
        assertThat(testUser1.isPresent).isTrue()

        // Duplicate email, different login
        val secondUser = ManagedUserVM().apply {
            login = "test-register-duplicate-email-2"
            password = firstUser.password
            firstName = firstUser.firstName
            lastName = firstUser.lastName
            email = firstUser.email
            imageUrl = firstUser.imageUrl
            langKey = firstUser.langKey
            authorities = firstUser.authorities?.toMutableSet()
        }

        // Register second (non activated) user
        restMvc.perform(
            post("/api/register")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(secondUser))
        )
            .andExpect(status().isCreated)

        val testUser2 = userRepository.findOneByLogin("test-register-duplicate-email")
        assertThat(testUser2.isPresent).isFalse()

        val testUser3 = userRepository.findOneByLogin("test-register-duplicate-email-2")
        assertThat(testUser3.isPresent).isTrue()

        // Duplicate email - with uppercase email address
        val userWithUpperCaseEmail = ManagedUserVM().apply {
            id = firstUser.id
            login = "test-register-duplicate-email-3"
            password = firstUser.password
            firstName = firstUser.firstName
            lastName = firstUser.lastName
            email = "TEST-register-duplicate-email@example.com"
            imageUrl = firstUser.imageUrl
            langKey = firstUser.langKey
            authorities = firstUser.authorities?.toMutableSet()
        }

        // Register third (not activated) user
        restMvc.perform(
            post("/api/register")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(userWithUpperCaseEmail))
        )
            .andExpect(status().isCreated)

        val testUser4 = userRepository.findOneByLogin("test-register-duplicate-email-3")
        assertThat(testUser4.isPresent).isTrue()
        assertThat(testUser4.get().email).isEqualTo("test-register-duplicate-email@example.com")

        testUser4.get().activated = true
        userService.updateUser((UserDTO(testUser4.get())))

        // Register 4th (already activated) user
        restMvc.perform(
            post("/api/register")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(secondUser))
        )
            .andExpect(status().is4xxClientError)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun testRegisterAdminIsIgnored() {
        val validUser = ManagedUserVM().apply {
            login = "badguy"
            password = "password"
            firstName = "Bad"
            lastName = "Guy"
            email = "badguy@example.com"
            activated = true
            imageUrl = "http://placehold.it/50x50"
            langKey = DEFAULT_LANGUAGE
            authorities = setOf(ADMIN)
        }

        restMvc.perform(
            post("/api/register")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(validUser))
        )
            .andExpect(status().isCreated)

        val userDup = userRepository.findOneByLogin("badguy")
        assertThat(userDup.isPresent).isTrue()
        assertThat(userDup.get().authorities).hasSize(1)
            .containsExactly(authorityRepository.findById(USER).get())
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun testActivateAccount() {
        val activationKey = "some activation key"
        var user = User(
            login = "activate-account",
            email = "activate-account@example.com",
            password = RandomStringUtils.random(60),
            activated = false,
            activationKey = activationKey
        )

        userRepository.saveAndFlush(user)

        restMvc.perform(get("/api/activate?key={activationKey}", activationKey))
            .andExpect(status().isOk)

        user = userRepository.findOneByLogin(user.login!!).orElse(null)
        assertThat(user.activated).isTrue()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun testActivateAccountWithWrongKey() {
        restMvc.perform(get("/api/activate?key=wrongActivationKey"))
            .andExpect(status().isInternalServerError)
    }

    @Test
    @Transactional
    @WithMockUser("save-account")
    @Throws(Exception::class)
    fun testSaveAccount() {
        val user = User(
            login = "save-account",
            email = "save-account@example.com",
            password = RandomStringUtils.random(60),
            activated = true
        )

        userRepository.saveAndFlush(user)

        val userDTO = UserDTO(
            login = "not-used",
            firstName = "firstname",
            lastName = "lastname",
            email = "save-account@example.com",
            activated = false,
            imageUrl = "http://placehold.it/50x50",
            langKey = DEFAULT_LANGUAGE,
            authorities = setOf(ADMIN)
        )

        restMvc.perform(
            post("/api/account")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(userDTO))
        )
            .andExpect(status().isOk)

        val updatedUser = userRepository.findOneByLogin(user.login!!).orElse(null)
        assertThat(updatedUser.firstName).isEqualTo(userDTO.firstName)
        assertThat(updatedUser.lastName).isEqualTo(userDTO.lastName)
        assertThat(updatedUser.email).isEqualTo(userDTO.email)
        assertThat(updatedUser.langKey).isEqualTo(userDTO.langKey)
        assertThat(updatedUser.password).isEqualTo(user.password)
        assertThat(updatedUser.imageUrl).isEqualTo(userDTO.imageUrl)
        assertThat(updatedUser.activated).isEqualTo(true)
        assertThat(updatedUser.authorities).isEmpty()
    }

    @Test
    @Transactional
    @WithMockUser("save-invalid-email")
    @Throws(Exception::class)
    fun testSaveInvalidEmail() {
        val user = User(
            login = "save-invalid-email",
            email = "save-invalid-email@example.com",
            password = RandomStringUtils.random(60),
            activated = true
        )

        userRepository.saveAndFlush(user)

        val userDTO = UserDTO(
            login = "not-used",
            firstName = "firstname",
            lastName = "lastname",
            email = "invalid email",
            activated = false,
            imageUrl = "http://placehold.it/50x50",
            langKey = DEFAULT_LANGUAGE,
            authorities = setOf(ADMIN)
        )

        restMvc.perform(
            post("/api/account")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(userDTO))
        )
            .andExpect(status().isBadRequest)

        assertThat(userRepository.findOneByEmailIgnoreCase("invalid email")).isNotPresent
    }

    @Test
    @Transactional
    @WithMockUser("save-existing-email")
    @Throws(Exception::class)
    fun testSaveExistingEmail() {
        val user = User(
            login = "save-existing-email",
            email = "save-existing-email@example.com",
            password = RandomStringUtils.random(60),
            activated = true
        )

        userRepository.saveAndFlush(user)

        val anotherUser = User(
            login = "save-existing-email2",
            email = "save-existing-email2@example.com",
            password = RandomStringUtils.random(60),
            activated = true
        )

        userRepository.saveAndFlush(anotherUser)

        val userDTO = UserDTO(
            login = "not-used",
            firstName = "firstname",
            lastName = "lastname",
            email = "save-existing-email2@example.com",
            activated = false,
            imageUrl = "http://placehold.it/50x50",
            langKey = DEFAULT_LANGUAGE,
            authorities = setOf(ADMIN)
        )

        restMvc.perform(
            post("/api/account")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(userDTO))
        )
            .andExpect(status().isBadRequest)

        val updatedUser = userRepository.findOneByLogin("save-existing-email").orElse(null)
        assertThat(updatedUser.email).isEqualTo("save-existing-email@example.com")
    }

    @Test
    @Transactional
    @WithMockUser("save-existing-email-and-login")
    @Throws(Exception::class)
    fun testSaveExistingEmailAndLogin() {
        val user = User(
            login = "save-existing-email-and-login",
            email = "save-existing-email-and-login@example.com",
            password = RandomStringUtils.random(60),
            activated = true
        )

        userRepository.saveAndFlush(user)

        val userDTO = UserDTO(
            login = "not-used",
            firstName = "firstname",
            lastName = "lastname",
            email = "save-existing-email-and-login@example.com",
            activated = false,
            imageUrl = "http://placehold.it/50x50",
            langKey = DEFAULT_LANGUAGE,
            authorities = setOf(ADMIN)
        )

        restMvc.perform(
            post("/api/account")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(userDTO))
        )
            .andExpect(status().isOk)

        val updatedUser = userRepository.findOneByLogin("save-existing-email-and-login").orElse(null)
        assertThat(updatedUser.email).isEqualTo("save-existing-email-and-login@example.com")
    }

    @Test
    @Transactional
    @WithMockUser("change-password-wrong-existing-password")
    @Throws(Exception::class)
    fun testChangePasswordWrongExistingPassword() {
        val currentPassword = RandomStringUtils.random(60)
        val user = User(
            password = passwordEncoder.encode(currentPassword),
            login = "change-password-wrong-existing-password",
            email = "change-password-wrong-existing-password@example.com"
        )

        userRepository.saveAndFlush(user)

        restMvc.perform(
            post("/api/account/change-password")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(PasswordChangeDTO("1$currentPassword", "new password")))
        )
            .andExpect(status().isBadRequest)

        val updatedUser = userRepository.findOneByLogin("change-password-wrong-existing-password").orElse(null)
        assertThat(passwordEncoder.matches("new password", updatedUser.password)).isFalse()
        assertThat(passwordEncoder.matches(currentPassword, updatedUser.password)).isTrue()
    }

    @Test
    @Transactional
    @WithMockUser("change-password")
    @Throws(Exception::class)
    fun testChangePassword() {
        val currentPassword = RandomStringUtils.random(60)
        val user = User(
            password = passwordEncoder.encode(currentPassword),
            login = "change-password",
            email = "change-password@example.com"
        )

        userRepository.saveAndFlush(user)

        restMvc.perform(
            post("/api/account/change-password")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(PasswordChangeDTO(currentPassword, "new password")))
        )
            .andExpect(status().isOk)

        val updatedUser = userRepository.findOneByLogin("change-password").orElse(null)
        assertThat(passwordEncoder.matches("new password", updatedUser.password)).isTrue()
    }

    @Test
    @Transactional
    @WithMockUser("change-password-too-small")
    @Throws(Exception::class)
    fun testChangePasswordTooSmall() {
        val currentPassword = RandomStringUtils.random(60)
        val user = User(
            password = passwordEncoder.encode(currentPassword),
            login = "change-password-too-small",
            email = "change-password-too-small@example.com"
        )

        userRepository.saveAndFlush(user)

        val newPassword = RandomStringUtils.random(ManagedUserVM.PASSWORD_MIN_LENGTH - 1)

        restMvc.perform(
            post("/api/account/change-password")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(PasswordChangeDTO(currentPassword, newPassword)))
        )
            .andExpect(status().isBadRequest)

        val updatedUser = userRepository.findOneByLogin("change-password-too-small").orElse(null)
        assertThat(updatedUser.password).isEqualTo(user.password)
    }

    @Test
    @Transactional
    @WithMockUser("change-password-too-long")
    @Throws(Exception::class)
    fun testChangePasswordTooLong() {
        val currentPassword = RandomStringUtils.random(60)
        val user = User(
            password = passwordEncoder.encode(currentPassword),
            login = "change-password-too-long",
            email = "change-password-too-long@example.com"
        )

        userRepository.saveAndFlush(user)

        val newPassword = RandomStringUtils.random(ManagedUserVM.PASSWORD_MAX_LENGTH + 1)

        restMvc.perform(
            post("/api/account/change-password")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(PasswordChangeDTO(currentPassword, newPassword)))
        )
            .andExpect(status().isBadRequest)

        val updatedUser = userRepository.findOneByLogin("change-password-too-long").orElse(null)
        assertThat(updatedUser.password).isEqualTo(user.password)
    }

    @Test
    @Transactional
    @WithMockUser("change-password-empty")
    @Throws(Exception::class)
    fun testChangePasswordEmpty() {
        val currentPassword = RandomStringUtils.random(60)
        val user = User(
            password = passwordEncoder.encode(currentPassword),
            login = "change-password-empty",
            email = "change-password-empty@example.com"
        )

        userRepository.saveAndFlush(user)

        restMvc.perform(
            post("/api/account/change-password")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(PasswordChangeDTO(currentPassword, "")))
        )
            .andExpect(status().isBadRequest)

        val updatedUser = userRepository.findOneByLogin("change-password-empty").orElse(null)
        assertThat(updatedUser.password).isEqualTo(user.password)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun testRequestPasswordReset() {
        val user = User(
            password = RandomStringUtils.random(60),
            activated = true,
            login = "password-reset",
            email = "password-reset@example.com"
        )

        userRepository.saveAndFlush(user)

        restMvc.perform(
            post("/api/account/reset-password/init")
                .content("password-reset@example.com")
        )
            .andExpect(status().isOk)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun testRequestPasswordResetUpperCaseEmail() {
        val user = User(
            password = RandomStringUtils.random(60),
            activated = true,
            login = "password-reset",
            email = "password-reset@example.com"
        )

        userRepository.saveAndFlush(user)

        restMvc.perform(
            post("/api/account/reset-password/init")
                .content("password-reset@EXAMPLE.COM")
        )
            .andExpect(status().isOk)
    }

    @Test
    @Throws(Exception::class)
    fun testRequestPasswordResetWrongEmail() {
        restMvc.perform(
            post("/api/account/reset-password/init")
                .content("password-reset-wrong-email@example.com")
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun testFinishPasswordReset() {
        val user = User(
            password = RandomStringUtils.random(60),
            login = "finish-password-reset",
            email = "finish-password-reset@example.com",
            resetDate = Instant.now().plusSeconds(60),
            resetKey = "reset key"
        )

        userRepository.saveAndFlush(user)

        val keyAndPassword = KeyAndPasswordVM(key = user.resetKey, newPassword = "new password")

        restMvc.perform(
            post("/api/account/reset-password/finish")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(keyAndPassword))
        )
            .andExpect(status().isOk)

        val updatedUser = userRepository.findOneByLogin(user.login!!).orElse(null)
        assertThat(passwordEncoder.matches(keyAndPassword.newPassword, updatedUser.password)).isTrue()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun testFinishPasswordResetTooSmall() {
        val user = User(
            password = RandomStringUtils.random(60),
            login = "finish-password-reset-too-small",
            email = "finish-password-reset-too-small@example.com",
            resetDate = Instant.now().plusSeconds(60),
            resetKey = "reset key too small"
        )

        userRepository.saveAndFlush(user)

        val keyAndPassword = KeyAndPasswordVM(key = user.resetKey, newPassword = "foo")

        restMvc.perform(
            post("/api/account/reset-password/finish")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(keyAndPassword))
        )
            .andExpect(status().isBadRequest)

        val updatedUser = userRepository.findOneByLogin(user.login!!).orElse(null)
        assertThat(passwordEncoder.matches(keyAndPassword.newPassword, updatedUser.password)).isFalse()
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun testFinishPasswordResetWrongKey() {
        val keyAndPassword = KeyAndPasswordVM(key = "wrong reset key", newPassword = "new password")

        restMvc.perform(
            post("/api/account/reset-password/finish")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(keyAndPassword))
        )
            .andExpect(status().isInternalServerError)
    }
}
