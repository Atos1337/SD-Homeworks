package org.myhwproj.controllers

import org.myhwproj.models.Homework
import org.myhwproj.services.TeacherService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/teacher")
class TeacherController @Autowired constructor(
    private val teacherService: TeacherService
) {
    @PostMapping
    fun createHomework(@RequestBody homework: Homework) = teacherService.createHomework(homework)
}
