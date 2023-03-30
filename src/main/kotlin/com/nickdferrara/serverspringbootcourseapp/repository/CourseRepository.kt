package com.nickdferrara.serverspringbootcourseapp.repository

import com.nickdferrara.serverspringbootcourseapp.entity.Course
import org.springframework.data.repository.CrudRepository

interface CourseRepository: CrudRepository<Course, Int> {
}
