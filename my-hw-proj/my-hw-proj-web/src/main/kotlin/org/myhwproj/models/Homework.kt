package org.myhwproj.models

import org.springframework.format.annotation.DateTimeFormat
import java.util.Date
import javax.persistence.*

@Entity
data class Homework(
    var title: String,
    var publicationDate: String,
    var problem: String,
    var deadline: String,
    @Id @GeneratedValue var id: Long? = null
)
