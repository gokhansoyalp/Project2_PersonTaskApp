package ise308.soyalp.gokhan.persontaskapp

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper


//creating the database logic, extending the SQLiteOpenHelper base class
class DatabaseHandler(context: MainActivity): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "EmployeeDatabase"
        private val TABLE_CONTACTS = "EmployeeTable"
        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_ROLE = "role"
        private val KEY_TASK = "task"
        private val KEY_DEADLINE = "deadline"
        private val KEY_SH = "spendingh"

    }
    override fun onCreate(db: SQLiteDatabase?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //creating table with fields
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_ROLE + " TEXT," + KEY_TASK + " TEXT," + KEY_DEADLINE + " TEXT,"
                + KEY_SH + " INTEGER" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)
        onCreate(db)
    }


    //method to insert data
    fun addEmployee(emp: EmpModelClass):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.userId)
        contentValues.put(KEY_NAME, emp.userName) // EmpModelClass Name
        contentValues.put(KEY_ROLE, emp.userRole)
        contentValues.put(KEY_TASK, emp.userTask)
        contentValues.put(KEY_DEADLINE, emp.userDeadline)
        contentValues.put(KEY_SH, emp.userSpend)
        // Inserting Row
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    //method to read data
    fun viewEmployee():List<EmpModelClass>{
        val empList:ArrayList<EmpModelClass> = ArrayList<EmpModelClass>()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var uId: Int
        var uName: String
        var uRole: String
        var uTask: String
        var uDeadline :String
        var uSpend: Int
        if (cursor.moveToFirst()) {
            do {
                uId = cursor.getInt(cursor.getColumnIndex("id"))
                uName = cursor.getString(cursor.getColumnIndex("name"))
                uRole = cursor.getString(cursor.getColumnIndex("role"))
                uTask = cursor.getString(cursor.getColumnIndex("task"))
                uDeadline = cursor.getString(cursor.getColumnIndex("deadline"))
                uSpend = cursor.getInt(cursor.getColumnIndex("spendingh"))
                val emp= EmpModelClass(userId = uId, userName = uName, userRole = uRole, userTask = uTask, userDeadline = uDeadline, userSpend = uSpend)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }
    //method to update data
    fun updateEmployee(emp: EmpModelClass):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.userId)
        contentValues.put(KEY_NAME, emp.userName) // EmpModelClass Name
        contentValues.put(KEY_ROLE, emp.userRole )
        contentValues.put(KEY_TASK, emp.userTask)
        contentValues.put(KEY_DEADLINE, emp.userDeadline)
        contentValues.put(KEY_SH, emp.userSpend)

        // Updating Row
        val success = db.update(TABLE_CONTACTS, contentValues,"id="+emp.userId,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    //method to delete data
    fun deleteEmployee(emp: EmpModelClass):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.userId) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_CONTACTS,"id="+emp.userId,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
}