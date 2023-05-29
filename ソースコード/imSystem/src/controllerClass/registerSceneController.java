package controllerClass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import action.DAO.AddNew;
import action.DAO.InputCheck;
import action.util.DbUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class registerSceneController {
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
    
    
    
    @FXML
    public void initialize() {
        // Set the placeholders
        item_name.setPromptText("商品名を入力してください");
        item_price.setPromptText("価格を半角数字で入力してください");
        item_stock.setPromptText("在庫を半角数字で入力してください");
        item_description.setPromptText("商品説明を入力してください");
    }

    @FXML
    public void handleSearchButtonClick() throws ClassNotFoundException, SQLException, IOException {
    	 Connection con = DbUtil.getConnection();
         AddNew addNewDao = new AddNew(con);
         InputCheck check = new InputCheck(con);
    	String itemName = check.cutBrank(item_name.getText());
        String itemCategory = item_category.getSelectionModel().getSelectedItem();
        String itemPrice = check.cutBrank(item_price.getText());
        String itemStock = check.cutBrank(item_stock.getText());
        String itemDescription = check.cutBrank(item_description.getText());
        
       
        
        try {
        	String str = check.checkName(itemName);
        	if(check.checkNameCount(itemName)) {
        		Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("エラー");
                alert.setHeaderText(null);
                alert.setContentText("商品名は50文字以内で入力してください。");

                alert.showAndWait();
        	}else if(check.checkInfoCount(itemDescription)){
        		Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("エラー");
                alert.setHeaderText(null);
                alert.setContentText("商品説明は150文字以内で入力してください。");

                alert.showAndWait();
        	}else if(itemName == "" || itemCategory == null || itemPrice == "" || itemStock == "" || itemDescription == ""){
        		Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("エラー");
                alert.setHeaderText(null);
                alert.setContentText("全ての項目を入力してください");

                alert.showAndWait();
        	}else if(str == null) {
            int int_itemPrice = Integer.parseInt(itemPrice);
            int int_itemStock = Integer.parseInt(itemStock);

            addNewDao.addItem(itemName, itemCategory, int_itemPrice, int_itemStock, itemDescription);

           
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("登録完了");
            alert.setHeaderText(null);
            alert.setContentText("新しい商品の登録が完了しました。");
            
            
            // Clear the fields
            item_name.clear();
            item_category.getSelectionModel().clearSelection();
            item_price.clear();
            item_stock.clear();
            item_description.clear();

            alert.showAndWait();
        	}else {
        		Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("エラー");
                alert.setHeaderText(null);
                alert.setContentText("その商品は既に存在します。");

                alert.showAndWait();
        	}
        } catch(NumberFormatException e) {

            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("入力エラー");
            alert.setHeaderText(null);
            alert.setContentText("価格と在庫数は半角数字で入力してください");
            alert.showAndWait();
        } catch(SQLException e) {
        } finally {
            DbUtil.closeConnection(con);
        }
    }

    @FXML
    public void handleCloseButtonClick() {
    	Stage stage = (Stage) closeButton.getScene().getWindow();

        stage.close();
    }
   
    
}
