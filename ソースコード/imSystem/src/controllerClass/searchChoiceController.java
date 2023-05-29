package controllerClass;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class searchChoiceController {

    @FXML
    private void searchButton_01(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxFile/searchChoiceScene_01.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("売上数検索");
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void searchButton_02(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxFile/searchChoiceScene_02.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("在庫切れ検索");
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void searchButton_03(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxFile/searchChoiceScene_03.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("商品名で検索");
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void searchButton_04(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxFile/searchChoiceScene_04.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("カテゴリーで検索");
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private Button closeButton;

@FXML
public void handleCloseButtonAction(ActionEvent event) {
    Stage stage = (Stage) closeButton.getScene().getWindow();
    stage.close();
}
}
