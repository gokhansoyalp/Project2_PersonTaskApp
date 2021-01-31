package ise308.soyalp.gokhan.persontaskapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class AddActivity() : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =inflater.inflate(R.layout.add_activity, container, false)

        val btnInsert=view.findViewById<Button>(R.id.btn_insert)
        val btnCancel=view.findViewById<Button>(R.id.btn_cancel)



        btnInsert.setOnClickListener {
            val personID =view.findViewById<EditText>(R.id.insert_id)
            val personName=view.findViewById<EditText>(R.id.insert_name)
            val personRole=view.findViewById<EditText>(R.id.insert_role)
            val personTask = view.findViewById<EditText>(R.id.insert_task)
            val taskDeadline = view.findViewById<EditText>(R.id.insert_deadline)
            val spendingHour = view.findViewById<EditText>(R.id.insert_spend)
            val isComplete = view?.findViewById<CheckBox>(R.id.cb_complete)

            EmpModelClass(personID.text.toString().toInt(),personName.text.toString(),personRole.text.toString()
            ,personTask.text.toString(),taskDeadline.text.toString(), spendingHour.text.toString().toInt())

            saveRecord()
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
        btnCancel.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }

        return view

    }



   private fun saveRecord(){
        val pID = view?.findViewById<EditText>(R.id.insert_id)
        val pName = view?.findViewById<EditText>(R.id.insert_name)
        val pRole= view?.findViewById<EditText>(R.id.insert_role)
        val pTask = view?.findViewById<EditText>(R.id.insert_task)
        val pDeadline = view?.findViewById<EditText>(R.id.insert_deadline)
        val pHour = view?.findViewById<EditText>(R.id.insert_spend)

        val personID = pID?.text.toString()
        val personName = pName?.text.toString()
        val personRole = pRole?.text.toString()
        val personTask = pTask?.text.toString()
        val taskDeadline = pDeadline?.text.toString()
        val personHour = pHour?.text.toString()

        val databaseHandler: DatabaseHandler= DatabaseHandler(context as MainActivity)
        if(personID.trim()!="" && personName.trim()!="" && personRole.trim()!="" && personTask.trim()!="" && taskDeadline.trim()!="" && personHour.trim()!=""){
            val status = databaseHandler.addEmployee(EmpModelClass(Integer.parseInt(personID),personName, personRole,personTask,taskDeadline,Integer.parseInt(personHour)))
            if(status > -1){
                Toast.makeText(context,"Record Saved", Toast.LENGTH_LONG).show()
                pID?.text?.clear()
                pName?.text?.clear()
                pRole?.text?.clear()
                pTask?.text?.clear()
                pDeadline?.text?.clear()
                pHour?.text?.clear()
            }
        }else{
            Toast.makeText(context,"id or name or email cannot be blank", Toast.LENGTH_LONG).show()
        }

    }


}