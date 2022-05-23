package org.myhwproj.services

import org.myhwproj.repositories.HomeworkRepository
import org.myhwproj.repositories.SubmissionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StudentService @Autowired constructor(
    private val homeworkRepository: HomeworkRepository,
    private val submissionRepository: SubmissionRepository
) {

}