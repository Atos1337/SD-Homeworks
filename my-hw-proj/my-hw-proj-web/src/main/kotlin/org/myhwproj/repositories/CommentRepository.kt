package org.myhwproj.repositories

import org.myhwproj.models.Comment
import org.myhwproj.models.Submission
import org.springframework.data.repository.CrudRepository

interface CommentRepository : CrudRepository<Comment, Long> {
    fun findAllBySubmission(submission: Submission)
}
