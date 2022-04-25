package org.esprit.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.esprit.Main;
import org.esprit.entities.Guide;
import org.esprit.entities.Reservation;
import org.esprit.entities.Trip;
import org.esprit.services.ReservationService;
import org.esprit.services.TripService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.System.exit;

public class ReservationController implements Initializable {

    private ReservationService reservationService = new ReservationService();
    private static List<Reservation> reservations = new ArrayList<>();

    public Button btnOverview;
    public Button btnGuides;
    public Button btnSignout;
    public Button btnTrips;
    public Button btnReservation;
    public Pane pnlReservation;
    @FXML
    private TableView table;
    @FXML
    private TableColumn ids;
    @FXML
    private TableColumn trips;
    @FXML
    private TableColumn users;
    @FXML
    private TableColumn dates;
    @FXML
    private TableColumn status;
    @FXML
    private TableColumn actions;


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

    public void switchToAdd(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        reservations = reservationService.Afficher();
        for (int i = 0; i < reservations.size(); i++) {
            ids.setCellValueFactory(new PropertyValueFactory<Guide, Integer>("id"));
            trips.setCellValueFactory(new PropertyValueFactory<Guide, Integer>("trip"));
            users.setCellValueFactory(new PropertyValueFactory<Guide, String>("user"));
            dates.setCellValueFactory(new PropertyValueFactory<Guide, Integer>("date"));
            status.setCellValueFactory(new PropertyValueFactory<Guide, Integer>("status"));
            actions.setCellFactory(col -> {
                Button deleteButton = new Button("Delete");

                TableCell<Guide, Guide> cell = new TableCell<Guide, Guide>() {
                    @Override
                    public void updateItem(Guide guide, boolean empty) {
                        super.updateItem(guide, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox pane = new HBox(deleteButton);
                            setGraphic(pane);
                        }
                    }
                };
                deleteButton.setOnAction(event -> {
                    Reservation trip = (Reservation) table.getItems().get(cell.getTableRow().getIndex());
                    reservationService.Supprimer(trip.getId());
                    reservations.remove(cell.getTableRow().getIndex());
                    table.getItems().remove(cell.getTableRow().getIndex());
                });
                return cell;
            });

            ObservableList<Reservation> data = FXCollections.observableArrayList(
                    reservations.get(i)
            );
            table.getItems().addAll(data);
        }

    }
}
