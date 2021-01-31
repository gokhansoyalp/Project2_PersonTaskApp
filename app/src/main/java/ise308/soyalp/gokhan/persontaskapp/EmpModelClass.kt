package ise308.soyalp.gokhan.persontaskapp

class EmpModelClass(var userId: Int, var userName:String , var userRole: String,
                    var userTask: String, var userDeadline: String, var userSpend: Int){

    var selectedFlag : Boolean =false
    fun setFlag(flag: Boolean){
        selectedFlag= flag
    }
}