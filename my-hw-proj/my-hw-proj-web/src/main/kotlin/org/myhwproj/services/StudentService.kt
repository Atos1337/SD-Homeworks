package org.myhwproj.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import org.myhwproj.models.Comment
import org.myhwproj.models.Submission
import org.myhwproj.models.SubmissionResult
import org.myhwproj.repositories.CommentRepository
import org.myhwproj.repositories.HomeworkRepository
import org.myhwproj.repositories.SubmissionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Timestamp
import javax.annotation.PreDestroy

@Service
class StudentService @Autowired constructor(
    private val homeworkRepository: HomeworkRepository,
    private val commentRepository: CommentRepository,
    private val submissionRepository: SubmissionRepository
) {
    private val HW_TO_CHECK_QUEUE = "task_queue"
    private val HW_RESULT_QUEUE = "res_queue"
    private val factory: ConnectionFactory = ConnectionFactory().apply { host = "localhost" }
    private val connection: Connection = factory.newConnection()
    private val channelCheck: Channel = connection.createChannel()
    private val channelResult: Channel = connection.createChannel()
    private val mapper = ObjectMapper().registerKotlinModule()
    private val deliverCallback = DeliverCallback { _, delivery ->
        val submissionResult = mapper.readValue(delivery.body.decodeToString(), SubmissionResult::class.java)
        submissionRepository.findById(submissionResult.submissionId).ifPresent {
            it.mark = submissionResult.mark
            submissionRepository.save(it)
            commentRepository.save(Comment(it, submissionResult.comment))
        }
    }

    init {
        recvSubmissionResult()
    }

    fun createSubmission(submission: Submission): Submission {
        val submissionInDb = submissionRepository.save(submission)
        sendSubmission(submission)
        return submissionInDb
    }

    fun getActualHomeworks() = Timestamp(System.currentTimeMillis()).let {
        homeworkRepository.findAllByDeadlineAfterAndPublicationDateBeforeOrderByDeadline(it, it)
    }

    private fun sendSubmission(submission: Submission) {
        channelCheck.queueDeclare(HW_TO_CHECK_QUEUE, false, false, false, null)
        channelCheck.basicPublish("", HW_TO_CHECK_QUEUE, null, submission.solution.toByteArray())
    }

    private fun recvSubmissionResult() {
        channelResult.queueDeclare(HW_RESULT_QUEUE, false, false, false, null)
        channelResult.basicConsume(HW_RESULT_QUEUE, true, deliverCallback) { _ -> }
    }

    @PreDestroy
    private fun onDestroy() {
        channelCheck.close()
        channelResult.close()
        connection.close()
        println("Spring Container is destroyed!")
    }
}
