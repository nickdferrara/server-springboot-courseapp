package com.nickdferrara.serverspringbootcourseapp.controller

import com.nickdferrara.serverspringbootcourseapp.dto.CourseDto
import com.nickdferrara.serverspringbootcourseapp.service.CourseService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/courses")
@Validated
class CourseController(val courseService: CourseService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody @Valid courseDto: CourseDto): CourseDto {
        return courseService.addCourse(courseDto)
    }

    @GetMapping
    fun retrieveAllCourses(): List<CourseDto> = courseService.retrieveAllCourses()

    @PutMapping("/{course_id}")
    fun updateCourse(@RequestBody courseDto: CourseDto,
                     @PathVariable("course_id") courseId: Int) =
        courseService.updateCourse(courseId, courseDto)

    @DeleteMapping("/{course_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCourse(@PathVariable("course_id") courseId: Int) =
        courseService.deleteCourse(courseId)

}