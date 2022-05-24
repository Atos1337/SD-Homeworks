package org.myhwproj.services

import org.myhwproj.repositories.CommentRepository
import org.myhwproj.repositories.SubmissionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SubmissionService @Autowired constructor(
    private val submissionRepository: SubmissionRepository,
    private val commentRepository: CommentRepository
) {
}