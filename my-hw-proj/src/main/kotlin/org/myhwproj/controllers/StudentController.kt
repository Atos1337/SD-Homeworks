package org.myhwproj.controllers

import org.myhwproj.models.Submission
import org.myhwproj.services.StudentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/student")
class StudentController @Autowired constructor(
    private val studentService: StudentService
) {
    @PostMapping("/submission")
    fun createSubmission(@RequestBody submission: Submission) = studentService.createSubmission(submission)

    @GetMapping("/homework")
    fun getHomeworks() = studentService.getActualHomeworks()
}
