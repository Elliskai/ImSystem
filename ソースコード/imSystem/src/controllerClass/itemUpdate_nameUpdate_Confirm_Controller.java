package controllerClass;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class itemUpdate_nameUpdate_Confirm_Controller {
	 @FXML
	    private Button closeButton;
	
	@FXML
 public void handleCloseButtonAction(ActionEvent event) {
     Stage stage = (Stage) closeButton.getScene().getWindow();
     stage.close();
 }
    public void initData(String name) {
        this.item_name.setText(name);
    }


    @FXML
    private TextField item_name;

    @FXML
    private TextField item_id;

    // The DAO object. This should be initialized somewhere, e.g., in the constructor or an init method.
  
    public void initialize(String name) {
    	 
    }

    @FXML
    public void ConfirmEdit() throws IOException {

        // Load the next scene
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxFile/itemUpdate_nameUpdate_dataFillScene.fxml")); // adjust the fxml file name as needed
        Parent root = (Parent) fxmlLoader.load();

        // Pass the name to the next scene
        itemUpdate_nameUpdate_dataFillController controller = fxmlLoader.<itemUpdate_nameUpdate_dataFillController>getController();
        controller.setData(item_name.getText());
        controller.updateData(); // New method
        // Show the new scene
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("変更情報入力");
        stage.setScene(scene);
        stage.show();
        
        // Close the current scene
        Stage currentStage = (Stage) closeButton.getScene().getWindow();
        currentStage.close();
    }

}
