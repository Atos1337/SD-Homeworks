package org.myhwproj.models

import java.util.Date
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Homework(
    var title: String,
    var publicationDate: Date,
    var problem: String,
    var deadline: Date,
    @Id @GeneratedValue var id: Long? = null
)
