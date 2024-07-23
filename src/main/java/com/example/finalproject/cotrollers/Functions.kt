package com.example.finalproject.cotrollers

import android.content.Context
import com.example.finalproject.model.*

class Functions(context: Context) {
    private val departmentList: ArrayList<Department>
    private val dbHelper: SurveyDatabase = SurveyDatabase(context)


    init{
        departmentList = dbHelper.getAllDepartments()
    }

    fun getInCompletedSurveys(sID: Int): ArrayList<Survey>{
        return dbHelper.getInCompletedSurveys(sID)
    }

    fun getCompletedSurveys(sID: Int): ArrayList<Survey>{
        return dbHelper.getCompletedSurveys(sID)
    }

    fun getAllAnswers(): ArrayList<Answers>{
        return dbHelper.getAllAnswers()
    }
    fun getAllQuestions(surveyID: Int): ArrayList<Questions>{
        return dbHelper.getAllQuestions(surveyID)
    }
    fun getQuestionId(surveyId:Int, question:String): Int {
        return dbHelper.getQuestionId(surveyId, question)
    }

    fun getAnswerId(answer:String): Int {
        return dbHelper.getAnswerId(answer)
    }

    fun addResponse(response: StudentResponse): Boolean{
        return dbHelper.addResponse(response)
    }

    fun getDepartmentList(): ArrayList<Department>{
        return departmentList
    }

    fun getFilteredSurveysList(filter: Int): ArrayList<Survey>{
        return dbHelper.getFilteredSurveys(filter)
    }
    fun getStudentName(sId: Int): String{
        return dbHelper.findStudentName(sId)
    }
    fun checkStudent(email: String, pass: String): Boolean{
        return dbHelper.findStudent(email, pass)
    }
    fun addStudent(student: Student): Boolean{
        return dbHelper.addStudent(student)
    }
    fun addAdmin(admin: Admin): Boolean{
        return dbHelper.addAdmin(admin)
    }
    fun addSurvey(survey: Survey): Boolean{
        return dbHelper.addSurvey(survey)
    }
    fun updateSurvey(survey: Survey): Boolean{
        return dbHelper.updateSurvey(survey)
    }
    fun getDepartmentID(adminId: Int): Int {
        return dbHelper.getDepartmentID(adminId)
    }

    fun getAdminID(email: String): Int {
        return dbHelper.getAdminID(email)
    }

    fun getStudentID(email: String): Int {
        return dbHelper.getStudentID(email)
    }
    fun checkAdmin(email: String, pass: String): Boolean{
        return dbHelper.findAdmin(email, pass)
    }

    fun numOfPraticipants(surveyID: Int): Int{
        return dbHelper.countParticipants(surveyID)
    }

    fun checkSurveyHasQuestions(suID: Int): Boolean{
        return dbHelper.checkSurveyHasQuestions(suID)
    }

    fun surveyHasResponse(suID: Int): Boolean{
        return dbHelper.surveyHasResponse(suID)
    }

    fun getAllResponsesBySurvey(surveyID: Int): ArrayList<StudentResponse>{
        return dbHelper.getAllResponsesBySurvey(surveyID)
    }

    fun getAllResponsesByQuestion(surveyID: Int, questionID: Int): ArrayList<StudentResponse>{
        return dbHelper.getAllResponsesByQuestion(surveyID, questionID)
    }

    fun getAllResponsesByAnswer(surveyID: Int, questionID: Int, answerID: Int): ArrayList<StudentResponse>{
        return dbHelper.getAllResponsesByAnswer(surveyID, questionID, answerID)
    }

    fun addQuestion(question: Questions): Boolean{
        return dbHelper.addQuestion(question)
    }
    fun updateQuestion(question: Questions): Boolean{
        return dbHelper.updateQuestion(question)
    }

}