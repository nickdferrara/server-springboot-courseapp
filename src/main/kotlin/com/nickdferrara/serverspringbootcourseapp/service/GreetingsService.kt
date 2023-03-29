package com.nickdferrara.serverspringbootcourseapp.service

import org.springframework.stereotype.Service

@Service
class GreetingsService {
    fun retrieveGreeting(name: String) = "Hello $name"
}