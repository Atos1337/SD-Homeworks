package org.myhwproj.repositories

import org.myhwproj.models.Comment
import org.springframework.data.repository.CrudRepository

interface CommentRepository : CrudRepository<Comment, Long> {
}