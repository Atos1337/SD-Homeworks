package org.myhwproj

import org.myhwproj.models.Homework
import org.myhwproj.models.Submission
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.sql.Timestamp
import java.util.Date

val tasks: List<Homework> = listOf(
    Homework("aaaa", Timestamp(0), "aaaa", Timestamp(0), 1),
    Homework("bbbb", Timestamp(0), "bbbb", Timestamp(0), 2),
    Homework("cccc", Timestamp(0), "cccc", Timestamp(0), 3),
    Homework("dddd", Timestamp(0), "dddd", Timestamp(0), 4))

val submissions: List<Submission> = listOf(
    Submission(tasks[0], "aaaa", 5, Date(System.currentTimeMillis()),  1),
    Submission(tasks[0], "aaaa", 5, Date(System.currentTimeMillis()),  1),
    Submission(tasks[0], "aaaa", 5, Date(System.currentTimeMillis()),  1)
)

@Controller
class HtmlController {

    @GetMapping("student/home")
    fun blog(model: Model): String {
        model["title"] = "Blog"
        model["tasks"] = tasks
        return "blog"
    }

    @RequestMapping("student/task/{id}")
    fun task(@PathVariable id: Int, model: Model): String? {
        model["title"] = tasks[id].title
        model["problem"] = tasks[id].problem
        model["deadline"] = tasks[id].deadline
        model["publicationDate"] = tasks[id].publicationDate
        return "task"
    }

    @RequestMapping("student/task/{id}/submit")
    fun submit(@PathVariable id: Int, model: Model): String? {
        return "submit"
    }

    @RequestMapping("student/task/{id}/lastsubmission")
    fun lastsubmission(@PathVariable id: Int, model: Model): String? {
        model["submissionTime"] = "ibjnvekc"
        model["solution"] = "ibjnvekc"
        model["mark"] = "ibjnvekc"
        return "lastsubmission"
    }

    @GetMapping("teacher/home")
    fun teacherHome(model: Model): String {
        model["title"] = "Blog"
        model["tasks"] = submissions
        return "teacherHome"
    }

    @RequestMapping("teacher/submission/{id}")
    fun teacherSubmission(@PathVariable id: Int, model: Model): String? {
        model["title"] = submissions[id].homework.title
        model["problem"] = submissions[id].homework.problem
        model["deadline"] = submissions[id].homework.deadline
        model["publicationDate"] = submissions[id].homework.publicationDate
        model["solution"] = submissions[id].solution
        model["time"] = submissions[id].submissionTime
        return "teacherSubmission"
    }

    @RequestMapping("teacher/newtask")
    fun newTask(model: Model): String? {
        model["submissionTime"] = "ibjnvekc"
        model["solution"] = "ibjnvekc"
        model["mark"] = "ibjnvekc"
        return "newTask"
    }

    //    @RequestMapping("teacher/task/{id}")
    //    fun task(@PathVariable id: Int, model: Model): String? {
    //        model["title"] = tasks[id].title
    //        model["problem"] = tasks[id].problem
    //        model["deadline"] = tasks[id].deadline
    //        model["publicationDate"] = tasks[id].publicationDate
    //        return "task"
    //    }
    //
    //    @RequestMapping("student/task/{id}/submit")
    //    fun submit(@PathVariable id: Int, model: Model): String? {
    //        return "submit"
    //    }
    //
    //    @RequestMapping("student/task/{id}/lastsubmission")
    //    fun lastsubmission(@PathVariable id: Int, model: Model): String? {
    //        model["submissionTime"] = "ibjnvekc"
    //        model["solution"] = "ibjnvekc"
    //        model["mark"] = "ibjnvekc"
    //        return "lastsubmission"
    //    }
}