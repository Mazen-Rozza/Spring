package com.example.jpatest

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.JpaRepository

@Entity
@Table(name = "students")
class Student(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String,

    @OneToMany(mappedBy = "student")
    var courses : MutableList<Course> = mutableListOf()
)

interface StudentRepository : JpaRepository<Student, Long>

@Configuration
class TestRunner {

    @Bean
    fun run(
        studentRepository: StudentRepository,
        studentCardRepository: StudentCardRepository
    ) = CommandLineRunner {
        val student = Student(name = "Mazen")
        studentRepository.save(student)

        val card = StudentCard(
            cardNumber = "CARD-001",
            student = student
        )
        studentCardRepository.save(card)
    }
}