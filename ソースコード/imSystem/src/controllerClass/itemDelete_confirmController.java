package controllerClass;

import java.io.IOException;
import java.sql.SQLException;

import action.DAO.Delete;
import action.DTO.DTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class itemDelete_confirmController {
	 @FXML
	    private Button closeButton;
	
	@FXML
 public void handleCloseButtonAction(ActionEvent event) {
     Stage stage = (Stage) closeButton.getScene().getWindow();
     stage.close();
 }

    @FXML
    private TextField item_name;

    @FXML
    private TextField item_id;

    // The DAO object. This should be initialized somewhere, e.g., in the constructor or an init method.
    private Delete deleteDAO;

    public void initialize(Delete deleteDAO, DTO item) {
    	  this.deleteDAO = deleteDAO;
        item_name.setText(item.getItemName());
        item_id.setText(String.valueOf(item.getItemId())); 
    }

    @FXML
    public void deleteButtonClicked() throws IOException {
        // Get the Name of the item to delete
        String itemName = item_name.getText();

        // Delete the item
        try {
            deleteDAO.deleteItem(itemName);
            
            // show 
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("削除完了");
            alert.setHeaderText(null);
            alert.setContentText("削除が完了しました");

            alert.showAndWait();
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
