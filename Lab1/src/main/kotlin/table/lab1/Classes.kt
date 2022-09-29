package table.lab1

class Class(var name: String = "") {
    val students: ArrayList<Student> = arrayListOf()
    val variants: ArrayList<String> = arrayListOf()
}

class Student{
    var surname: String = ""
    var name: String = ""
    var patronymic: String = ""
    var variant: String = "-"
    var id: Int = -1
}
