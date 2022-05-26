package org.myhwproj.repositories

import org.myhwproj.models.Homework
import org.springframework.data.repository.CrudRepository
import java.sql.Timestamp
import java.util.Date

interface HomeworkRepository : CrudRepository<Homework, Long> {
    fun findAllByDeadlineAfterAndPublicationDateBeforeOrderByDeadline(deadline: String, publicationDate: String): Iterable<Homework>
}
