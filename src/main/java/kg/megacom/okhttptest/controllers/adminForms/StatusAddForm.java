package kg.megacom.okhttptest.controllers.adminForms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import kg.megacom.okhttptest.http.OkHttpHelper;
import kg.megacom.okhttptest.models.Status;

import java.awt.*;
import java.io.IOException;

public class StatusAddForm {

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    @FXML
    private TextField txtStatus;

    @FXML
    void onButtnonClicked(ActionEvent event) {
        if(event.getSource().equals(btnSave)){
            onSaveButtonClicked();
        }
        else {
            txtStatus.clear();

        }

    }

    private void onSaveButtonClicked() {
        Status status=new Status();
        status.setName(txtStatus.getText());
        try {
            OkHttpHelper.getInstance().postStatus(status);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
