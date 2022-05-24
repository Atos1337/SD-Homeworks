package org.myhwproj.services

import java.sql.Timestamp
import org.myhwproj.models.Comment
import org.myhwproj.models.Submission
import org.myhwproj.models.SubmissionResult
import org.myhwproj.repositories.CommentRepository
import org.myhwproj.repositories.HomeworkRepository
import org.myhwproj.repositories.SubmissionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StudentService @Autowired constructor(
    private val homeworkRepository: HomeworkRepository,
    private val commentRepository: CommentRepository,
    private val submissionRepository: SubmissionRepository,
    private val workersService: WorkersService,
) {

    init {
        workersService.startReceivingSubmissionResult { recvSubmissionResult(it) }
    }

    fun recvSubmissionResult(submissionResult: SubmissionResult) {
        println("start recvSubmissionResult")
        submissionRepository.findById(submissionResult.submissionId).ifPresent {
            it.mark = submissionResult.mark
            submissionRepository.save(it)
            println("save1")
            commentRepository.save(Comment(it, submissionResult.comment))
            println("save2")
        }
        println("finish recvSubmissionResult")
    }

    fun createSubmission(submission: Submission): Submission {
        val submissionInDb = submissionRepository.save(submission)
        workersService.sendSubmission(submission)
        return submissionInDb
    }

    fun getActualHomeworks() = Timestamp(System.currentTimeMillis()).let {
        homeworkRepository.findAllByDeadlineAfterAndPublicationDateBeforeOrderByDeadline(it, it)
    }
}
