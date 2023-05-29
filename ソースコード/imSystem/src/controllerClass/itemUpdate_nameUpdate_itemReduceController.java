package controllerClass;

import java.sql.Connection;
import java.sql.SQLException;

import action.DAO.InputCheck;
import action.DAO.Update;
import action.util.DbUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class itemUpdate_nameUpdate_itemReduceController {

	@FXML
	private Button commitButton;

	@FXML
	private TextField item_name;

	@FXML
	private Text item_stock_field;

	@FXML
	private TextField item_stock_reduce;

	@FXML
	private Button searchButton;

	@FXML
	private VBox vboxMiddle;

	@FXML
	private VBox vboxReduce;

	private String name;

	@FXML
	void ClickedcommitButton(ActionEvent event) throws ClassNotFoundException, SQLException {
		Connection con = DbUtil.getConnection();
		Update st = new Update(con);
		InputCheck nameid = new InputCheck(con);
		int nowStock = nameid.nowStock(name);
		try {
			String sell = item_stock_reduce.getText();
			itemUpdate_nameUpdate_dataFillController data = new itemUpdate_nameUpdate_dataFillController();
			if (sell.isEmpty() || sell == null) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("エラー");
				alert.setHeaderText(null);
				alert.setContentText("販売数を半角数字で入力してください");

				alert.showAndWait();
			} else if (nowStock < Integer.parseInt(sell)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("エラー");
				alert.setHeaderText(null);
				alert.setContentText("現在在庫数以上の売上数は登録できません");
				
				alert.showAndWait();
			} else {

				//現在在庫変更
				st.updateItemSell(sell, name);
				//売上数反映
				String time = st.getTimestampSetReceipt();
				String id = nameid.getItemID(name);

				st.setPurchaseHistory(sell, id, time);

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("登録完了");
				alert.setHeaderText(null);
				alert.setContentText("売上数の登録が完了しました");
				alert.showAndWait();

				// Close the window
				commitButton.getScene().getWindow().hide();

				item_stock_reduce.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void clickedSearchButton(ActionEvent event) throws ClassNotFoundException, SQLException {
		try {
			this.name = item_name.getText();
			itemUpdate_nameUpdate_dataFillController data = new itemUpdate_nameUpdate_dataFillController();
			if (name.isEmpty()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("エラー");
				alert.setHeaderText(null);
				alert.setContentText("商品名を入力してください");
			} else {
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
					vboxReduce.setVisible(true); // vboxReduceを表示する
					String stock = ("" + check.nowStock(name));
					item_stock_field.setText(stock);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
