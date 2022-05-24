package org.myhwproj.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import org.myhwproj.models.Submission
import org.myhwproj.models.SubmissionResult
import org.springframework.stereotype.Service
import javax.annotation.PreDestroy

@Service
class WorkersService {
    private val HW_TO_CHECK_QUEUE = "task_queue"
    private val HW_RESULT_QUEUE = "res_queue"
    private val factory: ConnectionFactory = ConnectionFactory().apply { host = "localhost" }
    private val connection: Connection = factory.newConnection()
    private val channelCheck: Channel = connection.createChannel()
    private val channelResult: Channel = connection.createChannel()
    private val mapper = ObjectMapper().registerKotlinModule()

    fun sendSubmission(submission: Submission) {
        channelCheck.queueDeclare(HW_TO_CHECK_QUEUE, false, false, false, null)
        channelCheck.basicPublish("", HW_TO_CHECK_QUEUE, null, mapper.writeValueAsBytes(submission))
    }

    fun startReceivingSubmissionResult(handler: (SubmissionResult) -> Unit) {
        channelResult.queueDeclare(HW_RESULT_QUEUE, false, false, false, null)

        val deliverCallback = DeliverCallback { _, delivery ->
            val submissionResult = mapper.readValue(delivery.body.decodeToString(), SubmissionResult::class.java)
            println("deliverCallback -> submissionResult -> $submissionResult")
            handler(submissionResult)
        }

        channelResult.basicConsume(HW_RESULT_QUEUE, true, deliverCallback) { _ -> }
    }

    @PreDestroy
    private fun onDestroy() {
        channelCheck.close()
        channelResult.close()
        connection.close()
        println("WorkersService is destroyed!")
    }
}
