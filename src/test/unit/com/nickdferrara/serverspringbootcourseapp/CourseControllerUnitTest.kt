package com.nickdferrara.serverspringbootcourseapp

import com.nickdferrara.serverspringbootcourseapp.controller.CourseController
import com.nickdferrara.serverspringbootcourseapp.dto.CourseDto
import com.nickdferrara.serverspringbootcourseapp.service.CourseService
import com.nickdferrara.serverspringbootcourseapp.util.mockCourseDto
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [CourseController::class])
@AutoConfigureWebTestClient
class CourseControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseServiceMock: CourseService

    @Test
    fun addCourse() {
        val courseDto = CourseDto(
            id = null,
            name = "The Road",
            category = "Fiction"
        )

        every { courseServiceMock.addCourse(any()) } returns  mockCourseDto(id = 1)

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
        every { courseServiceMock.retrieveAllCourses() }.returnsMany(
            listOf(
                mockCourseDto(id = 1),
                mockCourseDto(id = 2, "Hardware and Design")
            )
        )

        val courseDtos = webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDto::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals(2, courseDtos!!.size)
    }
}