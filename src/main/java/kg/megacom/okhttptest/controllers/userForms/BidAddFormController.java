package kg.megacom.okhttptest.controllers.userForms;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import kg.megacom.okhttptest.http.OkHttpHelper;
import kg.megacom.okhttptest.models.Bid;
import kg.megacom.okhttptest.models.Customer;
import kg.megacom.okhttptest.models.Lot;
import kg.megacom.okhttptest.models.Status;

public class BidAddFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Lot> cmbAuction;

    @FXML
    private TextField txtPhone;

    @FXML
    private Spinner<Double> spinnerBid;

    @FXML
    private CheckBox checBoxActive;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;
    @FXML
    void onButtonClicked(ActionEvent event) {
        if (event.getSource().equals(btnSave)){
            onSaveButtonClicked();
        }
    }

    private void onSaveButtonClicked() {
        Bid bid=new Bid();
        bid.setLot(cmbAuction.getSelectionModel().getSelectedItem());
        bid.setBidValue(spinnerBid.getValue());
        bid.setAddDate(new Date());
        bid.setActive(checBoxActive.isSelected());
        Customer customer = new Customer();
        customer.setPhone(txtPhone.getText());
        bid.setCustomer(customer);
        try {
            OkHttpHelper.getInstance().postBid(bid);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @FXML
    void initialize() {
        try {
            cmbAuction.setCellFactory(param -> new ListCell<Lot>(){
                @Override
                protected void updateItem(Lot item,boolean empty){
                    super.updateItem(item,empty);
                    if(item!=null&& !empty){
                        setText(item.getName());
                    }else{
                        setText(null);
                    }
                }
            });
            cmbAuction.setItems(FXCollections.observableArrayList(OkHttpHelper.getInstance().getActiveLotList()));
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        cmbAuction.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Lot>() {
            @Override
            public void changed(ObservableValue<? extends Lot> observable, Lot oldValue, Lot newValue) {


                spinnerBid.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(newValue.getMinPrice(),
                        newValue.getMaxPrice()*1000,newValue.getMinPrice(),newValue.getStep()));
            }
        });
    }


}
