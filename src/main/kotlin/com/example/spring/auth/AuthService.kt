package com.example.spring.auth

import com.example.spring.user.User
import com.example.spring.user.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
) {
    fun register(request: RegisterRequest): Long {
        val email = request.email.trim().lowercase()

        if (userRepository.existsByEmail(email)) {
            throw IllegalArgumentException("Email already registered")
        }

        val hashedPassword = requireNotNull(
            passwordEncoder.encode(request.password)
        ) {
            "Password encoding unexpectedly returned null"
        }

        val user = User(
            email = email,
            passwordHash = hashedPassword
        )

        return userRepository.save(user).id!!
    }


    fun login(request: LoginRequest): String {
        val email = request.email.trim().lowercase()

        val user = userRepository.findByEmail(email)
            ?: throw ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Invalid credentials"
            )

        if (!passwordEncoder.matches(request.password, user.passwordHash)) {
            throw ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Invalid credentials"
            )
        }

        return jwtService.generateToken(user.id!!)
    }

}