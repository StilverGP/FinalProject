package com.github.StilverGP.utils;

import javafx.scene.control.Alert;

public class Alerts {

    /**
     * Shows an error alert with the specified header and content text.
     *
     * @param header      the header text of the alert.
     * @param contentText the content text of the alert.
     */
    public static void showErrorAlert(String header, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setContentText(contentText);
        alert.show();
    }

    /**
     * Shows a confirmation alert with the specified header and content text.
     *
     * @param header      the header text of the alert.
     * @param contentText the content text of the alert.
     * @return the confirmation alert.
     */
    public static Alert showConfirmationAlert(String header, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(header);
        alert.setContentText(contentText);
        return alert;
    }
}
