package org.myhwproj.controllers

import org.myhwproj.models.Submission
import org.myhwproj.services.SubmissionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api")
class SubmissionController @Autowired constructor(
    private val submissionService: SubmissionService
) {
    @GetMapping("/submission")
    fun getSubmissions() = submissionService.getSubmissions()

    @GetMapping("/comment/submission")
    fun getComments(@RequestBody submission: Submission) = submissionService.getComments(submission)
}
