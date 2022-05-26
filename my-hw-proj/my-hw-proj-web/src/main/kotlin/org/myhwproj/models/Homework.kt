package org.myhwproj.models

import javax.persistence.*

@Entity
data class Homework(
    var title: String,
    var publicationDate: String,
    var problem: String,
    var deadline: String,
    @Id @GeneratedValue var id: Long? = null
)
