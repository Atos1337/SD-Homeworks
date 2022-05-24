package org.myhwproj.repositories

import org.myhwproj.models.Homework
import org.springframework.data.repository.CrudRepository
import java.sql.Timestamp

interface HomeworkRepository : CrudRepository<Homework, Long> {
    fun findAllByDeadlineAfterAndPublicationDateBeforeOrderByDeadline(deadline: Timestamp, publicationDate: Timestamp)
}
