package org.myhwproj.models

import java.sql.Timestamp
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class Submission(
    @ManyToOne var homework: Homework,
    var submissionTime: Timestamp,
    var solution: String,
    var mark: Int,
    @Id @GeneratedValue var id: Long? = null
)