package com.example.spring.user

import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun getProfile(userId: Long): UserResponse {
        val user = userRepository.findById(userId).orElseThrow()

        return UserResponse(
            id = user.id!!,
            email = user.email
        )
    }
}
