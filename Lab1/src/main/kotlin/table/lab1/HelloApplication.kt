package table.lab1

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class HelloApplication : Application() {
    val classes: MutableList<Class> = mutableListOf()
    val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("hello-view.fxml"))
    val scene = Scene(fxmlLoader.load())
    override fun start(stage: Stage) {
        val controller: HelloController = fxmlLoader.getController()
        stage.setOnCloseRequest{controller.CloseApp()}
        stage.title = "DataBases"
        stage.scene = scene
        stage.show()
    }
}

fun main() {
    Application.launch(HelloApplication::class.java)
}