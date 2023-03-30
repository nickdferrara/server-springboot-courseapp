package com.nickdferrara.serverspringbootcourseapp.extension

import com.nickdferrara.serverspringbootcourseapp.dto.CourseDto
import com.nickdferrara.serverspringbootcourseapp.entity.Course

fun Course.toDto(): CourseDto =
    CourseDto(
        id = this.id,
        name = this.name,
        category = this.category
    )

fun CourseDto.toCourse(): Course =
    Course(
        id = this.id,
        name = this.name,
        category = this.category
    )