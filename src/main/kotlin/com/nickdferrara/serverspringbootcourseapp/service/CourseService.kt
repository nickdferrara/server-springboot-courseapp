package com.nickdferrara.serverspringbootcourseapp.service

import com.nickdferrara.serverspringbootcourseapp.dto.CourseDto
import com.nickdferrara.serverspringbootcourseapp.entity.Course
import com.nickdferrara.serverspringbootcourseapp.exception.CourseNotFoundException
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

    fun retrieveAllCourses(): List<CourseDto> {
        logger.info { "Retrieving all courses" }
        return courseRepository.findAll()
            .map(Course::toDto)
    }

    fun updateCourse(courseId: Int, courseDto: CourseDto): CourseDto {
        val existingCourse = courseRepository.findById(courseId)

        if (existingCourse.isPresent) {
            existingCourse
                .get()
                .let {
                    it.name = courseDto.name
                    it.category = courseDto.category
                    courseRepository.save(it)
                    return it.toDto()
                }
        } else {
            logger.info { "Course not found: $courseId" }
            throw CourseNotFoundException("Course not found: $courseId")
        }
    }

    fun deleteCourse(courseId: Int) {
        val existingCourse = courseRepository.findById(courseId)

        if (existingCourse.isPresent) {
            existingCourse.get()
                .let {
                    courseRepository.deleteById(courseId)
                }
        } else {
            logger.info { "Course not found: $courseId" }
            throw CourseNotFoundException("No course found $courseId")
        }
    }
}
