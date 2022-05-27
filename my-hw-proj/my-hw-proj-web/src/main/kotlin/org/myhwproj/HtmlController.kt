package org.myhwproj

import org.myhwproj.models.Homework
import org.myhwproj.models.Submission
import org.myhwproj.services.StudentService
import org.myhwproj.services.SubmissionService
import org.myhwproj.services.TeacherService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HtmlController(
    private val studentService: StudentService,
    private val submissionService: SubmissionService,
    private val teacherService: TeacherService,
) {
    @GetMapping("student/home")
    fun blog(model: Model): String {
        model["title"] = "Blog"
//        rg
        model["tasks"] = studentService.getActualHomeworks() as List<Homework>
        return "blog"
    }

    @RequestMapping("student/task/{id}")
    fun task(@PathVariable id: Int, model: Model): String? {
        val tasks = studentService.getActualHomeworks() as List<Homework>
        var curId = 0
        for (submission in tasks) {
            if ((submission.id?.toInt() ?: 0) == id) {
                break
            }
            curId ++
        }
        model["title"] = tasks[curId].title
        model["problem"] = tasks[curId].problem
        model["deadline"] = tasks[curId].deadline
        model["publicationDate"] = tasks[curId].publicationDate
        return "task"
    }

    @RequestMapping("student/task/{id}/submit")
    fun submit(@PathVariable id: Int, model: Model): String? {
        return "submit"
    }

    @RequestMapping("student/task/{id}/lastsubmission")
    fun lastsubmission(@PathVariable id: Int, model: Model): String? {
        val submissions = submissionService.getSubmissions() as List<Submission>

        val curId = 0
        for (submission in submissions) {
            if ((submission.id?.toInt() ?: 0) == id) {
                break
            }
        }
        if (submissions.isEmpty()) {
            model["submissionTime"] = "None"
            model["solution"] = "None"
            model["mark"] = "None"
        } else {
            model["submissionTime"] = submissions[curId].submissionTime
            model["solution"] = submissions[curId].solution
            if (submissions[curId].mark != null)
                model["mark"] = submissions[curId].mark!!
            else
                model["mark"] = 0
        }
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
        var curId = 0
        for (submission in submissions) {
            if ((submission.id?.toInt() ?: 0) == id) {
                break
            }
            curId ++
        }
        model["solution"] = submissions[curId].solution
        model["time"] = submissions[curId].submissionTime
        return "teacherSubmission"
    }

    @RequestMapping("teacher/newtask")
    fun newTask(model: Model): String? {
        return "newTask"
    }

    @PostMapping("student/addnewsubmit")
    fun addSubmit(@ModelAttribute submission: Submission): String? {
        studentService.createSubmission(submission)
        return "redirect:home"
    }

    @PostMapping("teacher/addnewtask")
    fun addStudent(@ModelAttribute homework: Homework?): String? {
        if (homework != null) {
            teacherService.createHomework(homework)
        }
        return "redirect:home"
    }
}
