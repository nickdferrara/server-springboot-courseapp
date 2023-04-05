package com.nickdferrara.serverspringbootcourseapp.util

import com.nickdferrara.serverspringbootcourseapp.entity.Course

fun courseEntityList() = listOf(
    Course(
        null,
        "Algorithms And Data Structures",
        "Non-Fiction"),
    Course(
        null,
        "Build Reactive Microservices using Spring WebFlux/SpringBoot",
        "Non-Fiction",
    ),
    Course(null,
        "Wiremock for Kotlin Developers",
        "Non-Fiction" ,
    )
)