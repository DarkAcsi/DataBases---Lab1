package table.lab1

import java.io.File
import javafx.fxml.FXML
import javafx.scene.control.TextField
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.MenuButton
import javafx.scene.control.cell.PropertyValueFactory

val classes: ArrayList<Class> = arrayListOf()
var change: Int = -1
var edit = arrayListOf(-1, -1, -1)

class HelloController  {
    @FXML private lateinit var text0: TextField
    @FXML private lateinit var ListClass: TableColumn<Class, String>
    @FXML private lateinit var TListClass: TableView<Class>
    @FXML
    private fun CreateClass() {
        var new: Class = Class(name = text0.text.trim())
        if (isClass(new.name) < 0) {
            classes.add(new)
            ListClass.cellValueFactory = PropertyValueFactory<Class, String>("name")
            TListClass.items.clear()
            TListClass.items.addAll( classes )
        }
        change = -1
    }
    @FXML
    private fun EditNameClass() {
        change = isClass(text0.text.trim())
    }
    @FXML
    private fun SaveNameClass() {
        if (change >= 0) {
            var str: String = text0.text.trim()
            if (isClass(str) < 0) {
                val file1 = File(classes[change].name + ".txt")
                val file2 = File(str + ".txt")
                if (file1.isFile())
                    file1.renameTo(file2)
                classes[change].name = str
                ListClass.cellValueFactory = PropertyValueFactory<Class, String>("name")
                TListClass.items.clear()
                TListClass.items.addAll(classes)
            }
            change = -1
        }
    }
    @FXML
    private fun OpenClass(){
        var str: String = text0.text.trim()
        if (isClass(str) < 0) {
            val file = File(str +".txt")
            if (file.isFile())  {
                var newclass: Class = Class(name = str)
                classes.add(newclass)
                ListClass.cellValueFactory = PropertyValueFactory<Class, String>("name")
                TListClass.items.clear()
                TListClass.items.addAll( classes )
                file.forEachLine(){
                    val arr = it.split(" ")
                    val new: Student = Student()
                    new.surname = arr[0]
                    new.name = arr[1]
                    new.patronymic = arr[2]
                    new.variant = arr[3]
                    classes[classes.count()-1].students.add(new)
                }
            }
        }
        change = -1
    }
    @FXML
    private fun CloseClass(){
        val str: String = text0.text.trim()
        var i: Int = isClass(str)
        if (i >= 0) {
            val file = File(str + ".txt")
            if (file.isFile()) file.delete()
            file.writeText("")
            for (j in 0 until classes[i].students.count())
                file.appendText(classes[i].students[j].surname + " " + classes[i].students[j].name + " " +
                        classes[i].students[j].patronymic + " " + classes[i].students[j].variant + "\n")
            classes.removeAt(i)
            ListClass.cellValueFactory = PropertyValueFactory<Class, String>("name")
            TListClass.items.clear()
            TListClass.items.addAll( classes )
        }
        change = -1
    }
    @FXML
    private fun DeleteClass(){
        val str: String = text0.text.trim()
        val i: Int = isClass(str)
        if (i >= 0) {
            var file = File(str + ".txt")
            if (file.isFile())
                file.delete()
            classes.removeAt(i)
            ListClass.cellValueFactory = PropertyValueFactory<Class, String>("name")
            TListClass.items.clear()
            TListClass.items.addAll( classes )
        }
        change = -1
    }
    @FXML private lateinit var text1: TextField
    @FXML private lateinit var class1: TextField
    @FXML
    private fun AddStudentsFromFile(){
        val name: String = text1.text.trim()
        val str: String = class1.text.trim()
        var ind: Int = isClass(str)
        if (ind < 0) return
        var file = File(name+".txt")
        if (file.isFile()) {
            file.forEachLine() {
                val arr = it.split(" ")
                val new: Student = Student()
                new.surname = arr[0]
                new.name = arr[1]
                new.patronymic = arr[2]
                classes[classes.count()-1].students.add(new)
            }
        }
    }
    @FXML private lateinit var text2: TextField
    @FXML private lateinit var class2: TextField
    @FXML
    private fun VariantsFromFile(){
        val name: String = text2.text.trim()
        val str: String = class2.text.trim()
        var ind: Int = isClass(str)
        if (ind < 0) return
        var file = File(name+".txt")
        if (file.isFile()) {
            file.forEachLine() {
                classes[ind].variants.add(it)
            }
            val count = classes[ind].variants.count()
            for (i in 0 until classes[ind].students.count())
                classes[ind].students[i].variant = classes[ind].variants[i%count]
        }
    }
    @FXML private var st1 = TextField()
    @FXML private var st2 = TextField()
    @FXML private var st3 = TextField()
    @FXML private var st4 = TextField()
    @FXML private var st5 = TextField()
    @FXML
    private fun AddStudent(){
        edit[0] = -1
        val ind: Int = isClass(st5.text.trim())
        if (ind < 0) return
        val new = isStudent(st1.text.trim(), st2.text.trim(), st3.text.trim(), st4.text.trim())
        if (new == null) return
        if (new.name.isEmpty()) return
        classes[ind].students.add(new)
    }
    @FXML
    private fun SaveStudent(){
        if (edit[0] < 0) return
        edit[2] = isClass(st5.text.trim())
        val new = isStudent(st1.text.trim(), st2.text.trim(), st3.text.trim(), st4.text.trim())
        if (new != null) {
            if ((edit[2] == edit[0]) || (edit[2] < 0))
                classes[edit[0]].students[edit[1]] = new
            else {
                classes[edit[2]].students.add(new)
                classes[edit[0]].students.removeAt(edit[1])
            }
        }
        edit[0] = -1
    }
    @FXML private lateinit var edit1: TextField
    @FXML private lateinit var edit2: TextField
    @FXML
    private fun EditStudent(){
        edit[0] = isClass(edit1.text.trim())
        if (edit[0] < 0) return
        val ind: Int? = edit2.text.trim().toIntOrNull()
        if (ind != null)
            if ((ind <= classes[edit[0]].students.count()) && (ind > 0)) {
                edit[1] = ind - 1
                idStudents(edit[0], classes[edit[0]].students.count())
                st1.setText(classes[edit[0]].students[edit[1]].surname)
                st2.setText(classes[edit[0]].students[edit[1]].name)
                st3.setText(classes[edit[0]].students[edit[1]].patronymic)
                st4.setText(classes[edit[0]].students[edit[1]].variant)
                st5.setText(classes[edit[0]].name)
            }
        else edit[0] = -1
    }
    @FXML
    private fun DeleteStudent(){
        edit[0] = -1
        var ind1 = isClass(edit1.text.trim())
        if (ind1 < 0) return
        var ind2: Int? = edit2.text.trim().toIntOrNull()
        if (ind2 == null) return
        idStudents(ind1, classes[ind1].students.count())
        if ((ind2 <= classes[ind1].students.count()) && (ind2 > 0))
            classes[ind1].students.removeAt(--ind2)
    }
    @FXML private lateinit var table1: TableColumn<Student, Int>
    @FXML private lateinit var table2: TableColumn<Student, String>
    @FXML private lateinit var table3: TableColumn<Student, String>
    @FXML private lateinit var table4: TableColumn<Student, String>
    @FXML private lateinit var table5: TableColumn<Student, String>
    @FXML private lateinit var table: TableView<Student>
    @FXML private lateinit var tab_class: TextField
    @FXML
    private fun WatchTableClass(){
        var ind = isClass(tab_class.text.trim())
        if (ind < 0) return
        idStudents(ind, classes[ind].students.count())
        table1.cellValueFactory = PropertyValueFactory<Student, Int>("id")
        table2.cellValueFactory = PropertyValueFactory<Student, String>("surname")
        table3.cellValueFactory = PropertyValueFactory<Student, String>("name")
        table4.cellValueFactory = PropertyValueFactory<Student, String>("patronymic")
        table5.cellValueFactory = PropertyValueFactory<Student, String>("variant")
        table.items.clear()
        table.items.addAll(classes[ind].students)
    }
    @FXML private lateinit var search1: TextField
    @FXML private lateinit var search2: TextField
    @FXML private lateinit var key: MenuButton
    @FXML
    private fun SetId(){
        key.setText("Id_student")
    }
    @FXML
    private fun SetSurname(){
        key.setText("Surname")
    }
    @FXML
    private fun SetVariant(){
        key.setText("Variant")
    }
    @FXML private lateinit var res1: TableColumn<Student, Int>
    @FXML private lateinit var res2: TableColumn<Student, String>
    @FXML private lateinit var res3: TableColumn<Student, String>
    @FXML private lateinit var res4: TableColumn<Student, String>
    @FXML private lateinit var res5: TableColumn<Student, String>
    @FXML private lateinit var res: TableView<Student>
    @FXML
    private fun SearchStudents(){
        var ind = isClass(search1.text.trim())
        if (ind < 0) {
            return
            res.items.clear()
        }
        var key_st = key.text
        var str = search2.text.trim()
        val list: ArrayList<Student> = arrayListOf()
        idStudents(ind, classes[ind].students.count())
        when {
            key_st.compareTo("Id_student") == 0 -> {
                val id = str.toIntOrNull()
                if (id != null)
                    if ((id > 0) && (id <= classes[ind].students.count()))
                        list.add(classes[ind].students[id-1])
            }
            key_st.compareTo("Surname") == 0 -> {
                for (i in 0 until classes[ind].students.count())
                    if (str.compareTo(classes[ind].students[i].surname) == 0)
                        list.add(classes[ind].students[i])
            }
            key_st.compareTo("Variant") == 0 -> {
                for (i in 0 until classes[ind].students.count())
                    if (str.compareTo(classes[ind].students[i].variant) == 0)
                        list.add(classes[ind].students[i])
            }
        }
        if (list.count() > 0) {
            res1.cellValueFactory = PropertyValueFactory<Student, Int>("id")
            res2.cellValueFactory = PropertyValueFactory<Student, String>("surname")
            res3.cellValueFactory = PropertyValueFactory<Student, String>("name")
            res4.cellValueFactory = PropertyValueFactory<Student, String>("patronymic")
            res5.cellValueFactory = PropertyValueFactory<Student, String>("variant")
            res.items.clear()
            res.items.addAll(list)
        }
        else res.items.clear()
    }

    fun CloseApp() {
        for (i in 0 until classes.count()) {
            var file = File(classes[i].name + ".txt")
            if (file.isFile()) file.delete()
            file.writeText("")
            for (j in 0 until classes[i].students.count())
                file.appendText(
                    classes[i].students[j].surname + " " + classes[i].students[j].name + " " +
                            classes[i].students[j].patronymic + " " + classes[i].students[j].variant + "\n")
        }
    }
    private fun isClass(str:String): Int{
        if (str.isEmpty()) return -1
        if (classes.count() > 0)
            for (i in 0 until classes.count())
                if (str.compareTo(classes[i].name) == 0)
                    return i
        return -1
    }
    private fun isStudent(st1: String, st2: String, st3: String, st4: String): Student? {
        var new:Student = Student()
        if (st1.isEmpty()) return null
        if (st2.isEmpty()) return null
        new.surname = st1
        new.name = st2
        new.patronymic = st3
        if (st3.isEmpty()) new.patronymic = "-"
        new.variant = st4
        if (st4.isEmpty()) new.variant = "-"
        return new
    }
    private fun idStudents(ind: Int, count: Int){
        for (i in 0 until count)
            classes[ind].students[i].id = i + 1
    }
}