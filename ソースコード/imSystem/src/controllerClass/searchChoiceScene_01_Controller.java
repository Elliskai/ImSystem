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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class searchChoiceScene_01_Controller {

    @FXML
    private Button closeButton;

    @FXML
    private TextField item_name;

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
    private TableColumn<DTO, String> informationCol;

    @FXML
    private RadioButton radioButton1;

    @FXML
    private RadioButton radioButton2;

    private ToggleGroup group;

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handlesearchButtonAction(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle(); 
        
        String selectedCategory = item_name.getText().trim();

        if (selectedCategory.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("入力エラー");
            alert.setHeaderText(null);
            alert.setContentText("数字を入力してください");
            alert.showAndWait();
            return;
        }

        int int_selectedCategory;
        try {
            int_selectedCategory = Integer.parseInt(selectedCategory);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("入力エラー");
            alert.setHeaderText(null);
            alert.setContentText("半角数字を入力してください");
            alert.showAndWait();
            return;
        }


        Connection con = null;

        try {
            con = DbUtil.getConnection();
            Search searchDao = new Search(con);
            List<DTO> items = null;

            if ("以上".equals(selectedRadioButton.getText())) {
                items = searchDao.saleCountMoreSearch(int_selectedCategory);
            } else if ("以下".equals(selectedRadioButton.getText())) {
                items = searchDao.saleCountLessSearch(int_selectedCategory);
            }

            if(items != null && !items.isEmpty()) {
                ObservableList<DTO> observableList = FXCollections.observableArrayList();
                observableList.addAll(items);
                bookTable1.setItems(observableList);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
        group = new ToggleGroup();
        radioButton1.setToggleGroup(group);
        radioButton2.setToggleGroup(group);

        // Set the default selection
        radioButton1.setSelected(true);

        bookTable1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        idCol.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        sellCol.setCellValueFactory(new PropertyValueFactory<>("saleCount"));
     //   informationCol.setCellValueFactory(new PropertyValueFactory<>("information"));
        
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
