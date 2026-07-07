package com.example.jpatest

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository

@Entity
@Table(name = "student_cards")
class StudentCard(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var cardNumber: String = "",

    @OneToOne
    @JoinColumn(name = "student_id")
    var student: Student? = null
)

interface StudentCardRepository : JpaRepository<StudentCard, Long>