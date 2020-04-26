package kg.megacom.okhttptest.controllers.adminForms;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import kg.megacom.okhttptest.http.OkHttpHelper;
import kg.megacom.okhttptest.models.Lot;
import kg.megacom.okhttptest.models.Status;

public class LotEditFormController {
    private Stage stage;
    private Lot lot;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtMinPrice;

    @FXML
    private TextField txtMaxPrice;

    @FXML
    private TextField txtStep;

    @FXML
    private DatePicker dtpickStartDate;

    @FXML
    private DatePicker dtpickEndDate;

    @FXML
    private ComboBox<Status> cmbStatus;

    @FXML
    private Button btnfileChooser;

    @FXML
    private ImageView imgView;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnClose;

    @FXML
    void onButtonClicked(ActionEvent event) {
        if(event.getSource().equals(btnSave)){
            onSaveButtonClicked();
        }else if(event.getSource().equals(btnfileChooser)){
            Window window=btnfileChooser.getScene().getWindow();
            FileChooser fileChooser=new FileChooser();
            File file=fileChooser.showOpenDialog(window);
            Image image=new Image(file.toURI().toString());
            imgView.setImage(image);
            lot.setFile(file);
        }

    }

    private void onSaveButtonClicked() {
        lot.setName(txtName.getText());
        lot.setName(txtName.getText());
        lot.setMinPrice(Double.valueOf(txtMinPrice.getText()));
        lot.setMaxPrice(Double.valueOf(txtMaxPrice.getText()));
        lot.setStep(Double.valueOf(txtStep.getText()));
        LocalDate endDate=dtpickEndDate.getValue();
        Instant instant=Instant.from(endDate.atStartOfDay(ZoneId.systemDefault()));
        Date enddate=Date.from(instant);
        lot.setEndDate(enddate);
        LocalDate startDate=dtpickStartDate.getValue();
        Instant instant1=Instant.from(startDate.atStartOfDay(ZoneId.systemDefault()));
        Date startdate=Date.from(instant1);
        lot.setStartDate(startdate);
        lot.setStatus(cmbStatus.getSelectionModel().getSelectedItem());
        try {
            OkHttpHelper.getInstance().updateLot(lot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() throws IOException {
        cmbStatus.setCellFactory(param -> new ListCell<Status>(){
            @Override
            protected void updateItem(Status item,boolean empty){
                super.updateItem(item,empty);
                if(item!=null&& !empty){
                    setText(item.getName());
                }else{
                    setText(null);
                }
            }


        });     cmbStatus.setItems(FXCollections.observableArrayList(OkHttpHelper.getInstance().getStatus()));


    }

    public void initData(Stage stage, Lot lot) {
        this.stage=stage;

        if(lot!=null){
            this.lot=lot;
            txtName.setText(lot.getName());
            txtMaxPrice.setText(String.valueOf(lot.getMaxPrice()));
            txtMinPrice.setText(String.valueOf(lot.getMinPrice()));
            txtStep.setText(String.valueOf(lot.getStep()));
            Date startDate=lot.getStartDate();
            LocalDate start=startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            dtpickStartDate.setValue(start);
            Date endDate=lot.getEndDate();
            LocalDate end=endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            dtpickEndDate.setValue(end);
            File imgFile=new File(lot.getFile().getAbsolutePath());
            Image image=new Image(lot.getFile().toURI().toString());
            imgView.setImage(image);
            cmbStatus.setValue(lot.getStatus());


        }else {
            this.lot=new Lot();
        }
    }
}
