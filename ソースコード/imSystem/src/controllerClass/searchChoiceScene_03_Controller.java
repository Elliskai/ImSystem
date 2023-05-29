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

public class searchChoiceScene_03_Controller {

	@FXML
    private Button closeButton;

    @FXML
    private TableView<DTO> bookTable1; // use DTO

    @FXML
    private TableColumn<DTO, Integer> idCol; // use DTO

    @FXML
    private TableColumn<DTO, String> nameCol; // use DTO

    @FXML
    private TableColumn<DTO, String> categoryCol; // use DTO

    @FXML
    private TableColumn<DTO, Integer> priceCol; // use DTO

    @FXML
    private TableColumn<DTO, Integer> stockCol; // use DTO

    @FXML
    private TableColumn<DTO, Integer> sellCol; // use DTO

    @FXML
    private TableColumn<DTO, Integer> ratioCol; // use DTO

    @FXML
    private TableColumn<DTO, String> informationCol; // use DTO
    

    @FXML
    public void initialize() {
        bookTable1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        idCol.setCellValueFactory(new PropertyValueFactory<DTO, Integer>("itemId")); // use DTO
        nameCol.setCellValueFactory(new PropertyValueFactory<DTO, String>("itemName")); // use DTO
        categoryCol.setCellValueFactory(new PropertyValueFactory<DTO, String>("categoryName")); // use DTO
        priceCol.setCellValueFactory(new PropertyValueFactory<DTO, Integer>("price")); // use DTO
        stockCol.setCellValueFactory(new PropertyValueFactory<DTO, Integer>("stock")); // use DTO
        sellCol.setCellValueFactory(new PropertyValueFactory<DTO, Integer>("saleCount")); // use DTO
       
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
        searchItem(null);

        
    }
    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private TextField item_name;
    @FXML
    public void searchItem(ActionEvent actionEvent) {
        String selectedCategory = item_name.getText();
       Connection con = null;
        try {
            // Connect to your database.
            con = DbUtil.getConnection();

            // Create a Search object.
            Search searchDao = new Search(con);
//
//            // Call the categorySearch method.
            List<DTO> items = searchDao.itemNameSearch(selectedCategory);

            
            if (items.isEmpty()) {
                
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("入力エラー");
                alert.setHeaderText(null);
                alert.setContentText("検索した商品は見つかりませんでした　");

                alert.showAndWait();
            } else {
                // Populate the TableView
                ObservableList<DTO> observableList = FXCollections.observableArrayList();
                observableList.addAll(items);
                bookTable1.setItems(observableList);
            }
        } catch(SQLException | ClassNotFoundException e) {
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}
