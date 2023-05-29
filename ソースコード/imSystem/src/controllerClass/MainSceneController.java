package controllerClass;



import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainSceneController {
    public void initialize() {}

    @FXML
    public void function_one(ActionEvent AE) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxFile/registerScene.fxml")); // make sure to use the correct file name here
        Parent function_one_Parent = fxmlLoader.load();
        Scene function_one_Scene = new Scene(function_one_Parent);

        // Create new stage (window)
        Stage window = new Stage();
        window.setScene(function_one_Scene);
        window.setTitle("商品登録"); // Set the window title here
        window.show();
    }


    @FXML
    public void function_two(ActionEvent AE) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxFile/searchChoiceScene.fxml")); // make sure to use the correct file name here
        Parent function_two_Parent = fxmlLoader.load();
        Scene function_two_Scene = new Scene(function_two_Parent);

        // Create new stage (window)
        Stage window = new Stage();
        window.setScene(function_two_Scene);
        window.setTitle("検索条件"); // Set the window title here
        window.show();
    }

    @FXML
    public void function_three(ActionEvent AE) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxFile/itemUpdateScene.fxml")); // make sure to use the correct file name here
        Parent function_three_Parent = fxmlLoader.load();
        Scene function_three_Scene = new Scene(function_three_Parent);
        // Create new stage (window)
        Stage window = new Stage();
        window.setScene(function_three_Scene);
        window.setTitle("商品情報更新"); // Set the window title here
        window.show();
    }

    @FXML
    public void function_four(ActionEvent AE) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxFile/AnalyseScene.fxml")); // make sure to use the correct file name here
        Parent function_four_Parent = fxmlLoader.load();
        Scene function_four_Scene = new Scene(function_four_Parent);

        // Create new stage (window)
        Stage window = new Stage();
        window.setScene(function_four_Scene);
        window.setTitle("商品情報分析"); // Set the window title here
        window.show();
    }

    @FXML
    public void function_five(ActionEvent AE) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxFile/itemDelete.fxml")); // make sure to use the correct file name here
        Parent function_four_Parent = fxmlLoader.load();
        Scene function_four_Scene = new Scene(function_four_Parent);

        // Create new stage (window)
        Stage window = new Stage();
        window.setScene(function_four_Scene);
        window.setTitle("商品情報削除"); // Set the window title here
        window.show();
    }
}
