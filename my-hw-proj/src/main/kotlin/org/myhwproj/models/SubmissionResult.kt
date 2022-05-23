package org.myhwproj.models


data class SubmissionResult(
    val SubmissionResultId: Long,
    val submissionId: Long,
    val mark: Int,
    val comment: String,
)