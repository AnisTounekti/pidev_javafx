package org.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.esprit.Main;

import java.io.IOException;

import static java.lang.System.exit;

public class DashBoardController {

    @FXML
    private Button btnOverview;
    @FXML
    private Button btnGuides;
    @FXML
    private Button btnTrips;
    @FXML
    private Button btnReservation;
    @FXML
    private Button btnSignout;
    @FXML
    private Pane pnlOverview;

    public void handleClicks(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == btnOverview) {
            Main.setRoot("dashboard");
        } else if (actionEvent.getSource() == btnGuides) {
            Main.setRoot("Guides");
        } else if (actionEvent.getSource() == btnTrips) {
            Main.setRoot("trips");
        } else if (actionEvent.getSource() == btnReservation) {
            Main.setRoot("reservations");
        } else if (actionEvent.getSource() == btnSignout) {
            exit(0);
        }
    }
}
