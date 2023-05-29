package controllerClass;

import java.sql.Connection;
import java.sql.SQLException;

import action.DAO.InputCheck;
import action.DAO.Update;
import action.util.DbUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class itemUpdate_nameUpdate_dataFillController {

	@FXML
	private TextField item_name;

	@FXML
	private ComboBox<String> item_category;

	@FXML
	private TextField item_price;

	@FXML
	private TextField item_stock;

	@FXML
	private TextArea item_description;

	@FXML
	private Button searchButton;

	@FXML
	private Button closeButton;

	private String data;

	@FXML
	public void handleSearchButtonClick() throws ClassNotFoundException, SQLException {
		// TODO: Add the logic to be performed when the search button is clicked
		try {
			Connection con = DbUtil.getConnection();
			InputCheck check = new InputCheck(con);
			Update update = new Update(con);
			String data = this.data;
			String changeName = item_name.getText().strip();
			String category = item_category.getValue();
			String price = item_price.getText().strip();
			String stock = item_stock.getText().strip();
			String description = item_description.getText().strip();
			
			if (check.checkNameCount(changeName)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("エラー");
				alert.setHeaderText(null);
				alert.setContentText("商品名は50文字以内で入力してください。");

				alert.showAndWait();
			} else if (check.checkInfoCount(description)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("エラー");
				alert.setHeaderText(null);
				alert.setContentText("商品説明は150文字以内で入力してください。");

				alert.showAndWait();
			} else {
				if (category != null && !category.isEmpty()) {
					
					String ct = check.changeCategoryInteger(category);
					update.updateItemCategory(ct, data);
				}
				if (!price.isEmpty()) {

					update.updateItemPrice(price, data);
				}
				if (!stock.isEmpty()) {
					update.updateItemStock(stock, data);
				}
				if (!description.isEmpty()) {
					update.updateItemInformation(description, data);
				}
				if (!changeName.isEmpty()) {
					update.updateItemName(changeName, data);
				}
				// Clear the fields
				item_name.clear();
				item_category.getSelectionModel().clearSelection();
				item_price.clear();
				item_stock.clear();
				item_description.clear();

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("更新完了");
				alert.setHeaderText(null);
				alert.setContentText("更新が完了しました");

				alert.showAndWait();
			}
		} catch (NumberFormatException e) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("入力エラー");
			alert.setHeaderText(null);
			alert.setContentText("価格と在庫数は半角数字で入力してください");
			alert.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void handleCloseButtonClick() {
		// TODO: Add the logic to be performed when the close button is clicked
		closeButton.getScene().getWindow().hide();

		//引数（次画面のFXML,画面名）
		transitionScreen("/fxFile/itemUpdate_nameUpdateScene.fxml", "更新：商品名入力");
	}

	//データ受け取り
	public void setData(String data) {
		this.data = data;
	}

	//シーン切り替え用メソッド
	void transitionScreen(String fxmlName, String fxmlTitle) {
		try {
			//FXMLからのシーングラフの読み込み
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
			Parent root = loader.load();

			//シーングラフのルートノードを設定したシーンの作成
			Scene scene = new Scene(root, 290, 460);
			//ステージへのシーンの設定
			Stage stage = new Stage();

			stage.setScene(scene);
			stage.setTitle(fxmlTitle);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setData1(String data) {
		this.data = data;
	}

	public void updateData() {
		if (data != null) {
			item_name.setText(data);
		}
	}

}
