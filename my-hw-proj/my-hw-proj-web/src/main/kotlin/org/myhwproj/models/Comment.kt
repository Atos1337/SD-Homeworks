package org.myhwproj.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Lob
import javax.persistence.ManyToOne

@Entity
data class Comment(
    @ManyToOne var submission: Submission,
    @Lob var content: String,
    @Id @GeneratedValue var id: Long? = null
)
