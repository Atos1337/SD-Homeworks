package org.myhwproj.models

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class Submission(
    @ManyToOne var homework: Homework,
    var submissionTime: Date,
    var solution: String,
    var mark: Int? = null,
    @Id @GeneratedValue var id: Long? = null
)
