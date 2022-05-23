package org.myhwproj.repositories

import org.myhwproj.models.Submission
import org.springframework.data.repository.CrudRepository

interface SubmissionRepository : CrudRepository<Submission, Long> {
    fun findAllByOrderBySubmissionTime(): Iterable<Submission>
}
