package kg.megacom.okhttptest.controllers.adminForms;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kg.megacom.okhttptest.http.OkHttpHelper;
import kg.megacom.okhttptest.models.Lot;

public class AdminFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuItem mnItemCreateLot;

    @FXML
    private MenuItem mnItmEdit;


    @FXML
    private MenuItem mnItmStatus;

    @FXML
    private TableView<Lot> tbViewLot;

    @FXML
    private TableColumn<Lot, String> colmName;

    @FXML
    private TableColumn<Lot, Double> colmMinPrice;

    @FXML
    private TableColumn<Lot, Double> colmMaxPrice;

    @FXML
    private TableColumn<Lot, Date> colmStartDate;

    @FXML
    private TableColumn<Lot, Date> colmEndDate;

    @FXML
    void onMnClicked(ActionEvent event) {
        if (event.getSource().equals(mnItemCreateLot)){
            Stage stage=new Stage();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/layouts/lotAdd.fxml"));
            try {
                loader.load();
                stage.setScene(new Scene(loader.getRoot()));
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        try {
                            tbrefresh();
                            tbViewLot.refresh();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.show();
        }else if (event.getSource().equals(mnItmStatus)){
            Stage stage =new Stage();
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/layouts/statusAddForm.fxml"));
            try {
                loader.load();
                stage.setScene(new Scene(loader.getRoot()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.show();
        }else if(event.getSource().equals(mnItmEdit)){
            Stage stage =new Stage();
            try {
                FXMLLoader loader= new FXMLLoader(getClass().getResource("/layouts/lotEditForm.fxml"));
                loader.load();
                stage.setScene(new Scene(loader.getRoot()));

                LotEditFormController controller=loader.getController();
                Lot lot=tbViewLot.getSelectionModel().getSelectedItem();
                controller.initData(stage,lot);
                stage.setOnCloseRequest(eventClose -> {
                    try {
                        tbViewLot.refresh();
                        tbrefresh();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });


            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.show();
        }

    }




    @FXML
    void initialize() throws IOException {
    initData();
    tbrefresh();

    }

    private void tbrefresh() throws IOException {
        List<Lot> lotList;
        lotList= OkHttpHelper.getInstance().getLotList();
        tbViewLot.setItems(FXCollections.observableArrayList(lotList));
    }

    private void initData() {
        colmName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colmMinPrice.setCellValueFactory(new PropertyValueFactory<>("minPrice"));
        colmMaxPrice.setCellValueFactory(new PropertyValueFactory<>("maxPrice"));
        colmStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colmEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
    }
}
