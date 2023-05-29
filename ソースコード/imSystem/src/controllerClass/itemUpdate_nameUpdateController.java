package controllerClass;

import java.sql.Connection;
import java.sql.SQLException;

import action.DAO.InputCheck;
import action.util.DbUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class itemUpdate_nameUpdateController {
	@FXML
	private TextField item_name;
	@FXML
	private Button cancelButton;

	@FXML
	private HBox hboxBottom;


	@FXML
	private void handleButtonAction(ActionEvent event) throws ClassNotFoundException, SQLException {
	    try {
	        String name = item_name.getText();
	        Connection con = DbUtil.getConnection();
	        InputCheck check = new InputCheck(con);
	        String checkName = check.checkName(name);

	        if (checkName == null || checkName.isEmpty() || checkName.equals("noname")) {
	            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	            alert.setTitle("エラー");
	            alert.setHeaderText(null);
	            alert.setContentText("検索した商品名はみつかりません");
	            alert.showAndWait();
	        } else {
	        	if (checkName != null) {
	        	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxFile/itemUpdate_nameUpdate_Confirm_Scene.fxml"));
	        	    Parent parent = fxmlLoader.load();

	        	    itemUpdate_nameUpdate_Confirm_Controller controller = fxmlLoader.getController();
	        	    controller.initData(checkName);

	        	    Stage stage = new Stage();
	        	    stage.setTitle("確認画面");
	        	    stage.setScene(new Scene(parent));
	        	    stage.show();
	        	}    
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}




	@FXML
	void clickedCancelButton(ActionEvent event) {
		cancelButton.getScene().getWindow().hide();
		//引数（次画面のFXML,画面名）
		transitionScreen("/fxFile/itemUpdateScene.fxml", "商品情報更新");
	}

	public void transitionScreen(String fxmlName, String fxmlTitle) {
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
}
