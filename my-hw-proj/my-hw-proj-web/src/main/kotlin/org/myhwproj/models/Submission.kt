package org.myhwproj.models

import java.util.Date
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class Submission(
    @ManyToOne var homework: Homework? = null,
    var solution: String,
    var mark: Int? = null,
    var submissionTime: Date = Date(System.currentTimeMillis()),
    @Id @GeneratedValue var id: Long? = null
)
