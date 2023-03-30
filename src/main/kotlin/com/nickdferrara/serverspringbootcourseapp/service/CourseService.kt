package com.nickdferrara.serverspringbootcourseapp.service

import com.nickdferrara.serverspringbootcourseapp.dto.CourseDto
import com.nickdferrara.serverspringbootcourseapp.extension.toCourse
import com.nickdferrara.serverspringbootcourseapp.extension.toDto
import com.nickdferrara.serverspringbootcourseapp.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class CourseService(
    val courseRepository: CourseRepository
) {
    companion object: KLogging()

    fun addCourse(courseDto: CourseDto): CourseDto {
        val courseEntity = courseDto.toCourse()
        courseRepository.save(courseEntity)
        logger.info { "Saved course: $courseEntity" }
        return courseEntity.toDto()
    }
}
