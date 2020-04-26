package kg.megacom.okhttptest.controllers.userForms;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


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

public class LotViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuItem mnItmCreateBid;
    @FXML
    private MenuItem mnItemAdmin;

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
        if (event.getSource().equals(mnItmCreateBid)){
            Stage stage=new Stage();
            FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/layouts/bidAddForm.fxml"));
            try {
                fxmlLoader.load();
                stage.setScene(new Scene(fxmlLoader.getRoot()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.show();
        }else if (event.getSource().equals(mnItemAdmin)){
            Stage stage=new Stage();
            FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/layouts/adminSignIn.fxml"));
            try {
                fxmlLoader.load();
                stage.setScene(new Scene(fxmlLoader.getRoot()));
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        try {
                            tbViewLotRefresh();
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
        }

    }

    @FXML
    void initialize() throws IOException {
        initdata();
        tbViewLotRefresh();


    }

    private void tbViewLotRefresh() throws IOException {
            List<Lot> lotList;
            lotList=OkHttpHelper.getInstance().getActiveLotList();
            tbViewLot.setItems(FXCollections.observableArrayList(lotList));

    }

    private void initdata() {
        colmName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colmMinPrice.setCellValueFactory(new PropertyValueFactory<>("minPrice"));
        colmMaxPrice.setCellValueFactory(new PropertyValueFactory<>("maxPrice"));
        colmStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colmEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        }
    }

