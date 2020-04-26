
package kg.megacom.okhttptest.controllers.adminForms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class AdminSignInController {

    @FXML
    private TextField txtLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnEnter;

    @FXML
    private Button btnCancel;

    @FXML
    void onButtonClicked(ActionEvent event) {
        if (event.getSource().equals(btnEnter)) {
            String login = txtLogin.getText();
            String password = txtPassword.getText();
            onEnterButtonClicked(login, password);
        }else {
            Window stage= btnCancel.getScene().getWindow();
            stage.hide();
        }

    }

    private void onEnterButtonClicked(String login, String password) {
        if(!login.equals("admin") ||!password.equals("admin")){
            Alert alert=new Alert(Alert.AlertType.INFORMATION,"Login or password is false",ButtonType.OK);
            alert.showAndWait();
            if(alert.getResult()==ButtonType.OK){
                alert.close();
            }

        }
        else if (login.equals("admin") && password.equals("admin")) {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layouts/adminForm.fxml"));
            try {
                fxmlLoader.load();
                stage.setScene(new Scene(fxmlLoader.getRoot()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.show();
        }
    }
}

