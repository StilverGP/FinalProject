package com.github.StilverGP.utils;

import javafx.scene.control.Alert;

public class Alerts {

    public static void showErrorAlert(String header, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setContentText(contentText);
        alert.show();
    }

    public static void showInfoAlert(String header, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setContentText(contentText);
        alert.show();
    }

    public static Alert showConfirmationAlert(String header, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(header);
        alert.setContentText(contentText);
        return alert;
    }
}
