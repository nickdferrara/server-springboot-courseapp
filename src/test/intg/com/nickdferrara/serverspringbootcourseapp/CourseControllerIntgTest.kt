package com.nickdferrara.serverspringbootcourseapp

import com.nickdferrara.serverspringbootcourseapp.dto.CourseDto
import com.nickdferrara.serverspringbootcourseapp.entity.Course
import com.nickdferrara.serverspringbootcourseapp.repository.CourseRepository
import com.nickdferrara.serverspringbootcourseapp.util.courseEntityList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntgTest() {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository

    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll();
        val courses = courseEntityList()
        courseRepository.saveAll(courses)
    }

    @Test
    fun addCourse() {
        val courseDto = CourseDto(
            id = null,
            name = "The Road",
            category = "Fiction"
        )

        val savedCourseDto = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDto)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDto::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue{
            savedCourseDto!!.id != null
        }
    }

    @Test
    fun retrieveAllCourses() {
        val courseDtos = webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDto::class.java)
            .returnResult()
            .responseBody

        assertEquals(3, courseDtos!!.size)
    }

    @Test
    fun updateCourse() {
        val course = Course(
            null,
            "Algorithms And Data Structures",
            "Non-Fiction"
        )

        courseRepository.save(course)

        val updatedCourseDto = CourseDto(
            null,
            "Algorithms And Data Structures Part 2",
            "Non-Fiction"
        )

        val updatedCourse = webTestClient
            .put()
            .uri("/v1/courses/{courseId}", course.id)
            .bodyValue(updatedCourseDto)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDto::class.java)
            .returnResult()
            .responseBody

        assertEquals("Algorithms And Data Structures Part 2", updatedCourse?.name)
    }

    @Test
    fun deleteCourse() {
        val course = Course(
            null,
            "Algorithms And Data Structures",
            "Non-Fiction"
        )

        courseRepository.save(course)

        webTestClient
            .delete()
            .uri("/v1/courses/{courseId}", course.id)
            .exchange()
            .expectStatus().isNoContent
    }
}