package com.nickdferrara.serverspringbootcourseapp.dto

import jakarta.validation.constraints.NotBlank

data class CourseDto(
    val id: Int?,
    @get:NotBlank(message = "courseDTO.name can not be blank")
    val name: String,
    @get:NotBlank(message = "courseDTO.category can not be blank")
    val category: String
)
