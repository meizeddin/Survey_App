package com.example.finalproject.model
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.SQLException
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Arrays
import kotlin.collections.ArrayList

/* Database Configuration*/
private const val DATABASE_NAME = "surveyDatabase.db"
private const val DATABASE_VERSION : Int = 1

class SurveyDatabase (context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    /* Department Table */
    private val departmentTableName = "Department"
    private val departmentColumnID = "department_id"
    private val departmentColumnTitle = "department_title"
    /*************************/

    /* Admin Table */
    private val adminTableName = "Admin"
    private val adminColumnID = "admin_id"
    private val adminColumnDepartmentID = "department_id"
    private val adminColumnName = "admin_full_name"
    private val adminColumnEmail = "admin_email"
    private val adminColumnKey = "admin_key"
    private val adminColumnPass = "admin_password"
    /*************************/

    /* Student Table */
    private val studentTableName = "Student"
    private val studentColumnID = "student_id"
    private val studentColumnDepartmentID = "department_id"
    private val studentColumnName = "student_full_name"
    private val studentColumnEmail = "student_email"
    private val studentColumnPass = "student_password"
    /*************************/

    /* Student Progress Table */
    private val studentProgressTableName = "Student_Progress"
    private val studentProgressColumnID = "student_progress_id"
    private val studentProgressColumnStudentID = "student_id"
    private val studentProgressColumnSurveyID = "survey_id"
    private val studentProgressColumnProgress = "progress_num"
    private val studentProgressColumnProgressArray = "progress_array"

    /*************************/

    /* Admin Progress Table */
    private val adminProgressTableName = "Admin_Progress"
    private val adminProgressColumnID = "admin_progress_id"
    private val adminProgressColumnAdminID = "admin_id"
    private val adminProgressColumnSurveyID = "survey_id"
    private val adminProgressColumnProgress = "progress_num"
    private val adminProgressColumnProgressArray = "progress_array"

    /*************************/

    /* Survey Table */
    private val surveyTableName = "Survey"
    private val surveyColumnID = "survey_id"
    private val surveyColumnDepartmentID = "department_id"
    private val surveyColumnTitle = "survey_title"
    private val surveyColumnStartDate = "survey_start_date"
    private val surveyColumnEndDate = "survey_end_date"
    private val surveyColumnPublished = "survey_is_published"
    /*************************/

    /* Question Table */
    private val questionTableName = "Question"
    private val questionColumnID = "question_id"
    private val questionColumnSurveyID = "survey_id"
    private val questionColumnQuestion = "question_text"
    /*************************/

    /* Answer Table */
    private val answerTableName = "Answers"
    private val answerColumnID = "answer_id"
    private val answerColumnAnswer = "answer_text"
    /*************************/

    /* StudentSurveyRespond Table */
    private val respondTableName = "Student_Survey_Response_Table"
    private val respondColumnID = "response_id"
    private val respondColumnStudentID = "student_id"
    private val respondColumnSurveyID = "survey_id"
    private val respondColumnQuestionID = "question_id"
    private val respondColumnAnswerID = "answer_id"
    /*************************/

    // This is called the first time a database is accessed
    // Create a new database
    override fun onCreate(db: SQLiteDatabase?) {
        try {
            var sqlCreateStatement: String = "CREATE TABLE " +
                    departmentTableName + " ( " +
                    departmentColumnID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    departmentColumnTitle + " TEXT NOT NULL UNIQUE ) "
            db?.execSQL(sqlCreateStatement)

            sqlCreateStatement = "CREATE TABLE " +
                    adminTableName + " ( " +
                    adminColumnID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    adminColumnDepartmentID + " INTEGER NOT NULL REFERENCES " + departmentTableName + "(" + departmentColumnID + "), " +
                    adminColumnName + " TEXT, " +
                    adminColumnEmail + " TEXT NOT NULL UNIQUE, " +
                    adminColumnKey + " TEXT, " +
                    adminColumnPass + " TEXT ) "
            db?.execSQL(sqlCreateStatement)


            sqlCreateStatement = "CREATE TABLE " +
                    studentTableName + " ( " +
                    studentColumnID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    studentColumnDepartmentID + " INTEGER NOT NULL REFERENCES " + departmentTableName + "(" + departmentColumnID + "), " +
                    studentColumnName + " TEXT, " +
                    studentColumnEmail + " TEXT NOT NULL UNIQUE, " +
                    studentColumnPass + " TEXT ) "
            db?.execSQL(sqlCreateStatement)

            sqlCreateStatement = "CREATE TABLE " +
                    surveyTableName + " ( " +
                    surveyColumnID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    surveyColumnDepartmentID + " INTEGER NOT NULL REFERENCES " + departmentTableName + "(" + departmentColumnID + "), " +
                    surveyColumnTitle + " TEXT NOT NULL UNIQUE, " +
                    surveyColumnStartDate + " TEXT, " +
                    surveyColumnEndDate + " TEXT, " +
                    surveyColumnPublished + " BOOLEAN ) "
            db?.execSQL(sqlCreateStatement)

            sqlCreateStatement = "CREATE TABLE " +
                    studentProgressTableName + " ( " +
                    studentProgressColumnID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    studentProgressColumnStudentID + " INTEGER NOT NULL REFERENCES " + studentTableName + "(" + studentColumnID + "), " +
                    studentProgressColumnSurveyID + " INTEGER NOT NULL REFERENCES " + surveyTableName + "(" + surveyColumnID + "), " +
                    studentProgressColumnProgress + " INTEGER, " +
                    studentProgressColumnProgressArray + " TEXT ) "
            db?.execSQL(sqlCreateStatement)

            sqlCreateStatement = "CREATE TABLE " +
                    adminProgressTableName + " ( " +
                    adminProgressColumnID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    adminProgressColumnAdminID + " INTEGER NOT NULL REFERENCES " + adminTableName + "(" + adminColumnID + "), " +
                    adminProgressColumnSurveyID + " INTEGER NOT NULL REFERENCES " + surveyTableName + "(" + surveyColumnID + "), " +
                    adminProgressColumnProgress + " INTEGER, " +
                    adminProgressColumnProgressArray + " TEXT ) "
            db?.execSQL(sqlCreateStatement)

            sqlCreateStatement = "CREATE TABLE " +
                    questionTableName + " ( " +
                    questionColumnID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    questionColumnSurveyID + " INTEGER NOT NULL REFERENCES " + surveyTableName + "(" + surveyColumnID + "), " +
                    questionColumnQuestion + " TEXT ) "
            db?.execSQL(sqlCreateStatement)

            sqlCreateStatement = "CREATE TABLE " +
                    answerTableName + " ( " +
                    answerColumnID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    answerColumnAnswer + " TEXT ) "
            db?.execSQL(sqlCreateStatement)

            sqlCreateStatement = "CREATE TABLE " +
                    respondTableName + " ( " +
                    respondColumnID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    respondColumnStudentID + " INTEGER NOT NULL REFERENCES "+studentTableName+"("+studentColumnID+"), " +
                    respondColumnSurveyID + " INTEGER NOT NULL REFERENCES "+surveyTableName+"("+surveyColumnID+"), " +
                    respondColumnQuestionID + " INTEGER NOT NULL REFERENCES "+questionTableName+"("+questionColumnID+"), " +
                    respondColumnAnswerID + " INTEGER NOT NULL REFERENCES "+answerTableName+"("+answerColumnID+") ) "
            db?.execSQL(sqlCreateStatement)
        }
        catch(e : SQLException) {
            println(e.message)
        }
    }
    // This is called if the database version is changed
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun addAdmin(admin: Admin) : Boolean {
        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(admin.password.toByteArray())

        cv.put(adminColumnDepartmentID, admin.departmentId)
        cv.put(adminColumnName, admin.fullName)
        cv.put(adminColumnEmail,admin.email)
        cv.put(adminColumnKey, admin.key)
        cv.put(adminColumnPass, hash)

        val success  =  db.insert(adminTableName, null, cv)
        db.close()
        return success != -1L
    }

    fun addStudent(student: Student) : Boolean {
        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(student.password.toByteArray())

        cv.put(studentColumnDepartmentID, student.departmentId)
        cv.put(studentColumnName, student.fullName)
        cv.put(studentColumnEmail,student.email)
        cv.put(studentColumnPass, hash)

        val success  =  db.insert(studentTableName, null, cv)
        db.close()
        return success != -1L
    }

    fun addSurvey(survey: Survey) : Boolean {
        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()

        cv.put(surveyColumnDepartmentID, survey.departmentId)
        cv.put(surveyColumnTitle, survey.title)
        cv.put(surveyColumnStartDate,survey.startDate)
        cv.put(surveyColumnEndDate, survey.endDate)
        cv.put(surveyColumnPublished, survey.isPublished)

        val success  =  db.insert(surveyTableName, null, cv)
        db.close()
        return success != -1L
    }

    fun updateSurvey(survey: Survey) : Boolean {
        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()
        val whereClause = "$surveyColumnID = ?"
        val whereArgs = arrayOf("${survey.id}")
        cv.put(surveyColumnDepartmentID, survey.departmentId)
        cv.put(surveyColumnTitle, survey.title)
        cv.put(surveyColumnStartDate,survey.startDate)
        cv.put(surveyColumnEndDate, survey.endDate)
        cv.put(surveyColumnPublished, survey.isPublished)

        val success  =  db.update(surveyTableName, cv, whereClause, whereArgs)
        db.close()
        return success != 0
    }

    fun addQuestion(question: Questions) : Boolean {
        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()

        cv.put(questionColumnSurveyID, question.surveyId)
        cv.put(questionColumnQuestion, question.question)

        val success  =  db.insert(questionTableName, null, cv)
        db.close()
        return success != -1L
    }

    fun updateQuestion(question: Questions) : Boolean {
        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()
        val whereClause = "$questionColumnID = ?"
        val whereArgs = arrayOf("${question.id}")
        cv.put(questionColumnSurveyID, question.surveyId)
        cv.put(questionColumnQuestion, question.question)

        val success  =  db.update(questionTableName, cv, whereClause, whereArgs)
        db.close()
        return success != 0
    }

    fun deleteAdmin(admin: Admin) : Boolean{
        // delete admin if exist in the database
        // writableDatabase for delete actions
        val db: SQLiteDatabase = this.writableDatabase
        val result = db.delete(adminTableName, "$adminColumnID = ${admin.id}", null) == 1
        db.close()
        return result
    }

    fun deleteStudent(student: Student) : Boolean{
        // delete student if exist in the database
        // writableDatabase for delete actions
        val db: SQLiteDatabase = this.writableDatabase
        val result = db.delete(studentTableName, "$studentColumnID = ${student.id}", null) == 1
        db.close()
        return result
    }

    fun getAllDepartments(): ArrayList<Department>{
        val departmentList = ArrayList<Department>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $departmentTableName"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do{
                val id: Int = cursor.getInt(0)
                val title: String = cursor.getString(1)

                val d = Department(id, title)
                departmentList.add(d)
            }while (cursor.moveToNext())

        cursor.close()
        db.close()

        return departmentList
    }

    fun findAdmin(email: String, password: String) : Boolean{
        var result = false
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $adminTableName"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(password.toByteArray())
        if (cursor.moveToFirst())
            do {
                val email1 = cursor.getString(3)
                val pass1 = cursor.getBlob(5)
                if (email1 == email && Arrays.equals(pass1, hash)){
                    result = true
                }
            } while (cursor.moveToNext())
        cursor.close()
        db.close()
        return result
    }

    fun findStudent(email: String, password: String) : Boolean{

        var result = false
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $studentTableName"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(password.toByteArray())
        if (cursor.moveToFirst())
            do {
                val email1 = cursor.getString(3)
                val pass1 = cursor.getBlob(4)
                if (email1 == email && Arrays.equals(pass1, hash)){
                    result = true
                }
            } while (cursor.moveToNext())
        cursor.close()
        db.close()
        return result
    }

    fun getFilteredSurveys(filter: Int): ArrayList<Survey>{
        val surveyList = ArrayList<Survey>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $surveyTableName"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do{
                val id: Int = cursor.getInt(0)
                val departmentId: Int = cursor.getInt(1)
                val title: String = cursor.getString(2)
                val startDate: String = cursor.getString(3)
                val endDate: String = cursor.getString(4)
                val isPublished: Boolean = cursor.getInt(5) == 1
                val s = Survey(id,departmentId, title, startDate, endDate, isPublished)
                when(filter){
                    1 -> if(!isPublished ){surveyList.add(s)}
                    2 -> if(isPublished && checkSurveyExpiration(id)) {surveyList.add(s)}
                    3 -> if(isPublished && !checkSurveyExpiration(id)){surveyList.add(s)}
                }
            }while (cursor.moveToNext())

        cursor.close()
        db.close()

        return surveyList
    }

    fun getInCompletedSurveys(sID: Int): ArrayList<Survey>{
        val surveyList = ArrayList<Survey>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $surveyTableName WHERE $surveyColumnPublished = 1"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do{
                val id: Int = cursor.getInt(0)
                val departmentId: Int = cursor.getInt(1)
                val title: String = cursor.getString(2)
                val startDate: String = cursor.getString(3)
                val endDate: String = cursor.getString(4)
                val isPublished: Boolean = cursor.getInt(5) == 1

                val s = Survey(id,departmentId, title, startDate, endDate, isPublished)
                if(checkSurveyExpiration(id) && !checkSurveyComplete(sID, id)) {
                    surveyList.add(s)
                }
            } while (cursor.moveToNext())

        cursor.close()
        db.close()

        return surveyList
    }

    fun getCompletedSurveys(sID: Int): ArrayList<Survey>{
        val surveyList = ArrayList<Survey>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $surveyTableName WHERE $surveyColumnPublished = 1"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do{
                val id: Int = cursor.getInt(0)
                val departmentId: Int = cursor.getInt(1)
                val title: String = cursor.getString(2)
                val startDate: String = cursor.getString(3)
                val endDate: String = cursor.getString(4)
                val isPublished: Boolean = cursor.getInt(5) == 1

                val s = Survey(id,departmentId, title, startDate, endDate, isPublished)
                if(checkSurveyExpiration(id) && checkSurveyComplete(sID, id)) {
                    surveyList.add(s)
                }
            } while (cursor.moveToNext())

        cursor.close()
        db.close()

        return surveyList
    }

    private fun checkSurveyComplete(sID: Int, suID: Int): Boolean{
        var result = false
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $respondTableName"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do{
                val studentId: Int = cursor.getInt(1)
                val surveyId: Int = cursor.getInt(2)
                if(suID == surveyId && sID == studentId) {
                    result = true
                }
            } while (cursor.moveToNext())

        cursor.close()
        db.close()

        return result
    }

    fun checkSurveyHasQuestions(suID: Int): Boolean{
        var result = false
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $questionTableName"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do{
                val surveyId: Int = cursor.getInt(1)
                if(suID == surveyId) {
                    result = true
                }
            } while (cursor.moveToNext())

        cursor.close()
        db.close()

        return result
    }

    fun countParticipants(suID: Int): Int{
        var lastStudentId = 0
        var result = 0
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $respondTableName WHERE $respondColumnSurveyID = $suID"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do{
                val studentId: Int = cursor.getInt(1)
                if(studentId != lastStudentId) {
                    result ++
                    lastStudentId = studentId
                }
            } while (cursor.moveToNext())
        cursor.close()
        db.close()
        return result
    }

    fun surveyHasResponse(suID: Int): Boolean{
        var result = false
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $respondTableName"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do{
                val surveyId: Int = cursor.getInt(2)
                if(suID == surveyId) {
                    result = true
                }
            } while (cursor.moveToNext())
        cursor.close()
        db.close()
        return result
    }

    fun getAllResponsesBySurvey(suId: Int): ArrayList<StudentResponse>{
        val responseList = ArrayList<StudentResponse>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * From $respondTableName WHERE $respondColumnSurveyID = $suId"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do{
                val id: Int = cursor.getInt(0)
                val studentId: Int = cursor.getInt(1)
                val surveyId: Int = cursor.getInt(2)
                val questionId: Int = cursor.getInt(3)
                val answerId: Int = cursor.getInt(4)

                val r = StudentResponse(id,studentId, surveyId, questionId, answerId)
                responseList.add(r)
            }while (cursor.moveToNext())

        cursor.close()
        db.close()

        return responseList
    }

    fun getAllResponsesByQuestion(suId: Int, questionID: Int): ArrayList<StudentResponse>{
        val responseList = ArrayList<StudentResponse>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * From $respondTableName WHERE $respondColumnSurveyID = $suId AND $respondColumnQuestionID = $questionID"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do{
                val id: Int = cursor.getInt(0)
                val studentId: Int = cursor.getInt(1)
                val surveyId: Int = cursor.getInt(2)
                val questionId: Int = cursor.getInt(3)
                val answerId: Int = cursor.getInt(4)

                val r = StudentResponse(id,studentId, surveyId, questionId, answerId)
                responseList.add(r)
            }while (cursor.moveToNext())

        cursor.close()
        db.close()

        return responseList
    }

    fun getAllResponsesByAnswer(suId: Int, questionID: Int, answerID: Int): ArrayList<StudentResponse>{
        val responseList = ArrayList<StudentResponse>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * From $respondTableName WHERE $respondColumnSurveyID = $suId AND $respondColumnQuestionID = $questionID AND $respondColumnAnswerID = $answerID"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do{
                val id: Int = cursor.getInt(0)
                val studentId: Int = cursor.getInt(1)
                val surveyId: Int = cursor.getInt(2)
                val questionId: Int = cursor.getInt(3)
                val answerId: Int = cursor.getInt(4)

                val r = StudentResponse(id,studentId, surveyId, questionId, answerId)
                responseList.add(r)
            }while (cursor.moveToNext())

        cursor.close()
        db.close()

        return responseList
    }

    @SuppressLint("SimpleDateFormat")
    private fun checkSurveyExpiration(suID: Int): Boolean{
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val now = dateFormat.parse(LocalDate.now().format(formatter))
        var check = false

        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $surveyTableName"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        if (cursor.moveToFirst())
            do {
                val surveyId: Int = cursor.getInt(0)
                val startDate = dateFormat.parse(cursor.getString(3))
                val endDate = dateFormat.parse(cursor.getString(4))
                if (surveyId == suID) {
                    check = (startDate!!.before(now) || (startDate == now)) && endDate!!.after(now)
                }
            }while (cursor.moveToNext())

        cursor.close()
        db.close()
        return check
    }

    fun getAllAnswers(): ArrayList<Answers>{
        val answerList = ArrayList<Answers>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $answerTableName"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do{
                val id: Int = cursor.getInt(0)
                val answer: String = cursor.getString(1)

                val s = Answers(id, answer)
                answerList.add(s)
            }while (cursor.moveToNext())

        cursor.close()
        db.close()

        return answerList
    }
    fun getAllQuestions(idS: Int): ArrayList<Questions>{
        val questionList = ArrayList<Questions>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $questionTableName WHERE $questionColumnSurveyID IN (SELECT $surveyColumnID FROM $surveyTableName)"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if (cursor.moveToFirst())
            do{
                val surveyId: Int = cursor.getInt(1)
                if(idS == surveyId) {
                    val id: Int = cursor.getInt(0)
                    val question: String = cursor.getString(2)
                    val s = Questions(id, surveyId, question)
                    questionList.add(s)
                }
            }while (cursor.moveToNext())

        cursor.close()
        db.close()

        return questionList
    }


    fun findStudentName(sID: Int) : String{
        var result = "No Student Found"
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $studentTableName"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        if (cursor.moveToFirst())
            do {
                val studentId: Int = cursor.getInt(0)
                if (studentId == sID){
                    result = cursor.getString(2)
                }
            } while (cursor.moveToNext())
        cursor.close()
        db.close()
        return result
    }

    fun getStudentID(email: String) : Int {
        var result = 0
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $studentTableName"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        if (cursor.moveToFirst())
            do {
                val email1: String = cursor.getString(3)
                if (email == email1){
                    result = cursor.getInt(0)
                }
            } while (cursor.moveToNext())
        cursor.close()
        db.close()
        return result
    }

    fun getDepartmentID(id: Int) : Int {
        var result = 0
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $adminTableName"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        if (cursor.moveToFirst())
            do {
                val adminId: Int = cursor.getInt(0)
                if (id == adminId){
                    result = cursor.getInt(1)
                }
            } while (cursor.moveToNext())
        cursor.close()
        db.close()
        return result
    }

    fun getAdminID(email: String) : Int {
        var result = 0
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $adminTableName"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        if (cursor.moveToFirst())
            do {
                val email1: String = cursor.getString(3)
                if (email == email1){
                    result = cursor.getInt(0)
                }
            } while (cursor.moveToNext())
        cursor.close()
        db.close()
        return result
    }

    fun getQuestionId(idS: Int, question: String) : Int {
        var result = 0
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $questionTableName"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        if (cursor.moveToFirst())
            do {
                val surveyId: Int = cursor.getInt(1)
                val questionTxt: String = cursor.getString(2)
                if (idS == surveyId && question == questionTxt){
                    result = cursor.getInt(0)
                }
            } while (cursor.moveToNext())
        cursor.close()
        db.close()
        return result
    }

    fun getAnswerId(answer: String) : Int {
        var result = 0
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $answerTableName"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)
        if (cursor.moveToFirst())
            do {
                val answerTxt: String = cursor.getString(1)
                if (answer == answerTxt){
                    result = cursor.getInt(0)
                }
            } while (cursor.moveToNext())
        cursor.close()
        db.close()
        return result
    }

    fun addResponse(respond: StudentResponse) : Boolean {
        // writableDatabase for insert actions
        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()
        cv.put(respondColumnStudentID, respond.studentID)
        cv.put(respondColumnSurveyID,respond.surveyID)
        cv.put(respondColumnQuestionID, respond.questionID)
        cv.put(respondColumnAnswerID, respond.answerID)

        val success  =  db.insert(respondTableName, null, cv)
        db.close()
        return success != -1L
    }

    fun deleteRespond(respond: StudentResponse) : Boolean{
        // delete response if exist in the database
        // writableDatabase for delete actions
        val db: SQLiteDatabase = this.writableDatabase
        val result = db.delete(respondTableName, "$respondColumnID = ${respond.id}", null) == 1
        db.close()
        return result
    }









}