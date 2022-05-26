package org.myhwproj

import org.myhwproj.models.Homework
import org.myhwproj.models.Submission
import org.myhwproj.services.StudentService
import org.myhwproj.services.SubmissionService
import org.myhwproj.services.TeacherService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*





@Controller
class HtmlController (
    private val studentService: StudentService,
    private val submissionService: SubmissionService,
    private val teacherService: TeacherService,
){
    @GetMapping("student/home")
    fun blog(model: Model): String {
        model["title"] = "Blog"
        model["tasks"] = studentService.getActualHomeworks() as List<Homework>
        return "blog"
    }

    @RequestMapping("student/task/{id}")
    fun task(@PathVariable id: Int, model: Model): String? {
        val tasks = studentService.getActualHomeworks() as List<Homework>
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
        model["tasks"] = submissionService.getSubmissions() as List<Submission>
        return "teacherHome"
    }

    @RequestMapping("teacher/submission/{id}")
    fun teacherSubmission(@PathVariable id: Int, model: Model): String? {
        val submissions: List<Submission> = submissionService.getSubmissions() as List<Submission>
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

    @PostMapping("teacher/addnewtask")
    fun addStudent(@ModelAttribute homework: Homework?): String? {
        if (homework != null) {
            teacherService.createHomework(homework)
        }
        return "redirect:home"
    }
}
