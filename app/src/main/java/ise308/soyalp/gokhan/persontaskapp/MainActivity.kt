package ise308.soyalp.gokhan.persontaskapp


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import android.content.DialogInterface
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.insert_btn_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()
        val listView = findViewById<ListView>(R.id.listView)
        if(id==R.id.action_one){
            listView.visibility=GONE
            changeFragment(AddActivity())
            return true
        }
        if (id==R.id.action_two){
            viewRecord()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewRecord()
    }

    private fun changeFragment(fragment : Fragment){
       val fragmentTransaction =supportFragmentManager.beginTransaction()
       fragmentTransaction.replace(R.id.frameLay, fragment)
       fragmentTransaction.commit()

    }

     fun viewRecord(){
        val listView=findViewById<ListView>(R.id.listView)
        //creating the instance of DatabaseHandler class
        val databaseHandler: DatabaseHandler= DatabaseHandler(this)
        //calling the viewEmployee method of DatabaseHandler class to read the records
        val emp: List<EmpModelClass> = databaseHandler.viewEmployee()
        val empArrayId = Array<String>(emp.size){"0"}
        val empArrayName = Array<String>(emp.size){"null"}
        val empArrayRole = Array<String>(emp.size){"null"}
        val empArrayTask = Array<String>(emp.size){"null"}
        val empArrayDeadline = Array<String>(emp.size){"null"}
        val empArraySpend = Array<String>(emp.size){"0"}
        var index = 0
        for(e in emp){
            empArrayId[index] = e.userId.toString()
            empArrayName[index] = e.userName
            empArrayRole[index] = e.userRole
            empArrayTask[index] = e.userTask
            empArrayDeadline[index] = e.userDeadline
            empArraySpend[index] = e.userSpend.toString()
            index++
        }
        //creating custom ArrayAdapter
        val myListAdapter = MyListAdapter(this,empArrayId,empArrayName,empArrayRole,empArrayTask,empArrayDeadline,empArraySpend)
        listView.adapter = myListAdapter

    }

    fun updateRecord(){
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.update_dialog, null)
        dialogBuilder.setView(dialogView)

        val edtId = dialogView.findViewById(R.id.updateId) as EditText
        val edtName = dialogView.findViewById(R.id.updateName) as EditText
        val edtRole = dialogView.findViewById(R.id.updateRole) as EditText
        val edtTask = dialogView.findViewById(R.id.updateTask) as EditText
        val edtDeadline = dialogView.findViewById(R.id.updateDeadline) as EditText
        val edtSpend = dialogView.findViewById(R.id.updateHour) as EditText

        val databaseHandler1: DatabaseHandler= DatabaseHandler(this)
        val emp: List<EmpModelClass> =databaseHandler1.viewEmployee()

        val empArrayId= Array<String>(emp.size){"0"}
        val empArrayName= Array<String>(emp.size){""}
        val empArrayRole= Array<String>(emp.size){""}
        val empArrayTask= Array<String>(emp.size){""}
        val empArrayDeadline= Array<String>(emp.size){""}
        val empArraySpend= Array<String>(emp.size){"0"}
        for ((index, e) in emp.withIndex()){
                empArrayId[index] = e.userId.toString()
                empArrayName[index] = e.userName.toString()
                empArrayRole[index] = e.userRole.toString()
                empArrayTask[index] = e.userTask.toString()
                empArrayDeadline[index] = e.userDeadline.toString()
                empArraySpend[index] = e.userSpend.toString()
                edtId.setText(empArrayId[index])
                edtName.setText(empArrayName[index])
                edtRole.setText(empArrayRole[index])
                edtTask.setText(empArrayTask[index])
                edtDeadline.setText(empArrayDeadline[index])
                edtSpend.setText(empArraySpend[index])
        }
        dialogBuilder.setTitle("Update Record")
        dialogBuilder.setMessage("Enter data below")
        dialogBuilder.setPositiveButton("Update", DialogInterface.OnClickListener { _, _ ->

            val updateId = edtId.text.toString()
            val updateName = edtName.text.toString()
            val updateRole = edtRole.text.toString()
            val updateTask = edtTask.text.toString()
            val updateDeadline = edtDeadline.text.toString()
            val updateSpend = edtSpend.text.toString()
            //creating the instance of DatabaseHandler class
            val databaseHandler: DatabaseHandler= DatabaseHandler(this)
            if(updateId.trim()!="" && updateName.trim()!="" && updateRole.trim()!="" && updateTask.trim()!="" && updateDeadline.trim()!="" && updateSpend.trim()!=""){
                //calling the updateEmployee method of DatabaseHandler class to update record
                val status = databaseHandler.updateEmployee(EmpModelClass(Integer.parseInt(updateId),updateName, updateRole, updateTask, updateDeadline, Integer.parseInt(updateSpend)))
                if(status > -1){
                    Toast.makeText(applicationContext,"Record Updated",Toast.LENGTH_LONG).show()
                    viewRecord()
                }
            }else{
                Toast.makeText(applicationContext,"id or name or email cannot be blank",Toast.LENGTH_LONG).show()
            }

        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            //pass
        })
        val b = dialogBuilder.create()
        b.show()
    }
    //method for deleting records based on id
    fun deleteRecord(){
        //creating AlertDialog for taking user id
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.delete_dialog, null)
        dialogBuilder.setView(dialogView)

        val dltId = dialogView.findViewById(R.id.deleteId) as EditText
        dialogBuilder.setTitle("Delete Record")
        dialogBuilder.setMessage("Enter id below")
        dialogBuilder.setPositiveButton("Delete", DialogInterface.OnClickListener { _, _ ->

            val deleteId = dltId.text.toString()
            //creating the instance of DatabaseHandler class
            val databaseHandler: DatabaseHandler= DatabaseHandler(this)
            if(deleteId.trim()!=""){
                //calling the deleteEmployee method of DatabaseHandler class to delete record
                val status = databaseHandler.deleteEmployee(EmpModelClass(Integer.parseInt(deleteId),"","","","",0))
                if(status > -1){
                    Toast.makeText(applicationContext,"Record Deleted",Toast.LENGTH_LONG).show()
                    viewRecord()
                }
            }else{
                Toast.makeText(applicationContext,"id or name or email cannot be blank",Toast.LENGTH_LONG).show()
            }

        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ ->
            //pass
        })
        val b = dialogBuilder.create()
        b.show()
    }

    fun updateRecord(view: View) {
        updateRecord()
    }

    fun deleteRecord(view: View) {
        deleteRecord()
    }
}