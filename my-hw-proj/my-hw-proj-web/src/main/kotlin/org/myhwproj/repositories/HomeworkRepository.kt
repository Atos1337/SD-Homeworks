package org.myhwproj.repositories

import org.myhwproj.models.Homework
import org.springframework.data.repository.CrudRepository
import java.util.Date

interface HomeworkRepository : CrudRepository<Homework, Long> {
    fun findAllByDeadlineAfterAndPublicationDateBeforeOrderByDeadline(deadline: Date, publicationDate: Date): Iterable<Homework>
}
