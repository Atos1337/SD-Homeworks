package org.myhwproj.repositories

import org.myhwproj.models.Homework
import org.springframework.data.repository.CrudRepository

interface HomeworkRepository : CrudRepository<Homework, Long> {
}