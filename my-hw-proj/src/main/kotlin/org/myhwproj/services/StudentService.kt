package org.myhwproj.services

import org.myhwproj.models.Submission
import org.myhwproj.repositories.HomeworkRepository
import org.myhwproj.repositories.SubmissionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Timestamp

@Service
class StudentService @Autowired constructor(
    private val homeworkRepository: HomeworkRepository,
    private val submissionRepository: SubmissionRepository
) {
    fun createSubmission(submission: Submission) = submissionRepository.save(submission)
    fun getActualHomeworks() = Timestamp(System.currentTimeMillis()).let {
        homeworkRepository.findAllByDeadlineAfterAndPublicationDateBeforeOrderByDeadline(it, it)
    }
}
