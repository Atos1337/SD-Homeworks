package org.myhwproj.models

import java.sql.Timestamp
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Homework(
    var title: String,
    var publicationDate: Timestamp,
    var problem: String,
    var deadline: Timestamp,
    @Id @GeneratedValue var id: Long? = null
)
