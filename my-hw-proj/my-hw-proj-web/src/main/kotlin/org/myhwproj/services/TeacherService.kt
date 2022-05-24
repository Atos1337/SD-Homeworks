package org.myhwproj.services

import org.myhwproj.models.Homework
import org.myhwproj.repositories.HomeworkRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TeacherService @Autowired constructor(
    private val homeworkRepository: HomeworkRepository,
) {
    fun createHomework(homework: Homework) = homeworkRepository.save(homework)
}
