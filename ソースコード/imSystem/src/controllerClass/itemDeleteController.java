package controllerClass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import action.DAO.Delete;
import action.DTO.DTO;
import action.util.DbUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class itemDeleteController {
	@FXML
	private TextField item_name;

	@FXML
	private Button closeButton;

	@FXML
	public void handleCloseButtonAction(ActionEvent event) {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void confirmDelete(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		String itemName = item_name.getText();
		Connection con = null;

		try {
			con = DbUtil.getConnection();
			Delete deleteDao = new Delete(con);

			DTO item = deleteDao.selectbyName(itemName);
			if (item != null) {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxFile/itemDeleteConfirmScene.fxml"));
				Scene scene = new Scene(fxmlLoader.load());

				// Get the controller of the next scene and pass the item to it
				itemDelete_confirmController controller = fxmlLoader.getController();
				controller.initialize(deleteDao, item);

				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				stage.setScene(scene);
			} else {
				// Display an alert dialog when item not found
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("注意：メッセージ");
				alert.setHeaderText(null);
				alert.setContentText("検索した商品が見つかりません");

				alert.showAndWait();
			}
		} catch (SQLException e) {
		}
	}
}
