package controllerClass;

import java.sql.SQLException;
import java.text.DecimalFormat;

import action.DTO.DTO; // use DTO
import action.main.classManager;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AnalyseController {

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
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        bookTable1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        idCol.setCellValueFactory(new PropertyValueFactory<DTO, Integer>("itemId")); // use DTO
        nameCol.setCellValueFactory(new PropertyValueFactory<DTO, String>("itemName")); // use DTO
        categoryCol.setCellValueFactory(new PropertyValueFactory<DTO, String>("categoryName")); // use DTO
        priceCol.setCellValueFactory(new PropertyValueFactory<DTO, Integer>("price")); // use DTO
        stockCol.setCellValueFactory(new PropertyValueFactory<DTO, Integer>("stock")); // use DTO
        sellCol.setCellValueFactory(new PropertyValueFactory<DTO, Integer>("saleCount")); // use DTO
 //       informationCol.setCellValueFactory(new PropertyValueFactory<DTO, String>("information")); // use DTO
//  <TableColumn fx:id="informationCol" text="商品説明" />
        try {
            ObservableList<DTO> list = FXCollections.observableArrayList(classManager.dAnalyse1()); // use DTO

            double sum = list.stream().mapToInt(DTO::getRatio).sum(); // use DTO

            TableColumn<DTO, Double> computedRatioCol = new TableColumn<DTO, Double>("売上構成比(%)"); // use DTO
            computedRatioCol.setCellValueFactory(cellData -> {
                DTO itm = cellData.getValue(); // use DTO

                double ratio = ((Math.floor((double) itm.getRatio() / sum * 10000)) / 10000) * 100;

                // Format the ratio value with two decimal places
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                String formattedRatio = decimalFormat.format(ratio);

                return new SimpleDoubleProperty(Double.parseDouble(formattedRatio)).asObject();
            });

            bookTable1.getColumns().add(computedRatioCol);
            
            // Make computedRatioCol sortable in descending order
            bookTable1.getSortOrder().add(computedRatioCol);
            computedRatioCol.setSortType(TableColumn.SortType.DESCENDING);

            bookTable1.setItems(list);
            bookTable1.sort();
        } 
        
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void showData() throws SQLException, ClassNotFoundException {
        ObservableList<DTO> list = FXCollections.observableArrayList(classManager.dAnalyse1()); // use DTO
        bookTable1.setItems(list);
    }
}