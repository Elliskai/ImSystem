package controllerClass;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import action.DAO.Search;
import action.DTO.DTO;
import action.util.DbUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class searchChoiceScene_02_Controller {


    @FXML
    private Button closeButton;
    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private TextField item_stock;

    @FXML
    private TableView<DTO> bookTable1;

    @FXML
    private TableColumn<DTO, Integer> idCol;

    @FXML
    private TableColumn<DTO, String> nameCol;

    @FXML
    private TableColumn<DTO, String> categoryCol;

    @FXML
    private TableColumn<DTO, Integer> priceCol;

    @FXML
    private TableColumn<DTO, Integer> stockCol;

    @FXML
    private TableColumn<DTO, Integer> sellCol;
    @FXML
    private TableColumn<DTO, String> informationCol; // use DTO

  

    public void searchItem(ActionEvent actionEvent) {
        Connection con = null;
        try {
            con = DbUtil.getConnection();
            Search searchDao = new Search(con);
            List<DTO> items = searchDao.stockSearch();

            

            if(items != null && !items.isEmpty()) {
                ObservableList<DTO> observableList = FXCollections.observableArrayList();
                observableList.addAll(items);
                bookTable1.setItems(observableList);
            }
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    
    public void searchItem_01(ActionEvent actionEvent){
         
         String selectedCategory = item_stock.getText(); //text input field data
         if (selectedCategory.isBlank()) {
             Alert alert = new Alert(AlertType.INFORMATION);
             alert.setTitle("入力エラー");
             alert.setHeaderText(null);
             alert.setContentText("商品名を入力してください");

             alert.showAndWait();
             return;
         }
      

        
         Connection con = null;
         try {
             con = DbUtil.getConnection();
             Search searchDao = new Search(con);
             List<DTO> items = searchDao.stockItemNameSearch(selectedCategory);

             

             if(items != null && !items.isEmpty()) {
                 ObservableList<DTO> observableList = FXCollections.observableArrayList();
                 observableList.addAll(items);
                 bookTable1.setItems(observableList);
             }
             if(items.isEmpty()) {
            	 Alert alert = new Alert(AlertType.INFORMATION);
                 alert.setTitle("入力エラー");
                 alert.setHeaderText(null);
                 alert.setContentText("検索した商品は見つかりませんでした　");

                 alert.showAndWait();
            	 
             }
         } catch(SQLException | ClassNotFoundException e) {
             e.printStackTrace();
         } finally {
             if (con != null) {
                 try {
                     con.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
             }
         }
    	
    }
    @FXML
    public void initialize() {
    	 searchItem(null); // Call searchItem on initialization
        
        bookTable1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        idCol.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        sellCol.setCellValueFactory(new PropertyValueFactory<>("saleCount"));
     // Setup Tooltip for Information Column
        informationCol.setCellValueFactory(new PropertyValueFactory<>("information"));
        
        informationCol.setCellFactory(tc -> {
            TableCell<DTO, String> cell = new TableCell<>();
            Tooltip tooltip = new Tooltip();
            
            
            cell.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    tooltip.setText(newValue);
                    cell.setTooltip(tooltip);
                }
            });
            cell.itemProperty().addListener((obs, oldItem, newItem) -> {
                if (newItem == null) {
                    cell.setText(null);
                    cell.setGraphic(null);
                } else {
                    cell.setText(newItem);
                }
            });
            return cell;
        });
        
        

    }
}
