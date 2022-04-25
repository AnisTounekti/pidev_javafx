package org.esprit.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.esprit.Main;
import org.esprit.entities.Guide;
import org.esprit.entities.Trip;
import org.esprit.services.TripService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.System.exit;
import static org.esprit.controller.GuideController.guideSelected;

public class TripController implements Initializable {

    public Label idGuide;
    public Label nameGuide;
    public TextField name;
    public TextField nbSejour;
    public TextField prix;
    public TextField id;
    public DatePicker date;
    private TripService tripService = new TripService();
    private static List<Trip> trips = new ArrayList<>();
    private static Trip tripSelected;

    public Button btnOverview;
    public Button btnGuides;
    public Button btnSignout;
    public Button btnTrips;
    public Button btnReservation;
    public Pane pnlTrips;
    @FXML
    private TableView table;
    @FXML
    private TableColumn ids;
    @FXML
    private TableColumn guide;
    @FXML
    private TableColumn names;
    @FXML
    private TableColumn dates;
    @FXML
    private TableColumn nbsejours;
    @FXML
    private TableColumn prixs;
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

    public void switchToAdd(ActionEvent actionEvent) throws IOException {
        Main.setRoot("addEditTrip");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (location.getPath().endsWith("trips.fxml")) {
            trips = tripService.Afficher();
            for (int i = 0; i < trips.size(); i++) {
                ids.setCellValueFactory(new PropertyValueFactory<Guide, Integer>("id"));
                guide.setCellValueFactory(new PropertyValueFactory<Guide, Integer>("guide"));
                names.setCellValueFactory(new PropertyValueFactory<Guide, String>("name"));
                dates.setCellValueFactory(new PropertyValueFactory<Guide, Integer>("date"));
                nbsejours.setCellValueFactory(new PropertyValueFactory<Guide, Integer>("nbSejour"));
                prixs.setCellValueFactory(new PropertyValueFactory<Guide, Integer>("prix"));
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
                        Trip trip = (Trip) table.getItems().get(cell.getTableRow().getIndex());
                        tripService.Supprimer(trip.getId());
                        trips.remove(cell.getTableRow().getIndex());
                        table.getItems().remove(cell.getTableRow().getIndex());
                    });
                    return cell;
                });

                ObservableList<Trip> data = FXCollections.observableArrayList(
                        trips.get(i)
                );
                table.getItems().addAll(data);
            }
        } else if (location.getPath().endsWith("tripsOfGuide.fxml")) {
            trips = tripService.AfficherByGuide(guideSelected);
            idGuide.setText(String.valueOf(guideSelected.getId()));
            nameGuide.setText(guideSelected.getName());
            for (int i = 0; i < trips.size(); i++) {
                ids.setCellValueFactory(new PropertyValueFactory<Guide, Integer>("id"));
                guide.setCellValueFactory(new PropertyValueFactory<Guide, Integer>("guide"));
                names.setCellValueFactory(new PropertyValueFactory<Guide, String>("name"));
                dates.setCellValueFactory(new PropertyValueFactory<Guide, Integer>("date"));
                nbsejours.setCellValueFactory(new PropertyValueFactory<Guide, Integer>("nbSejour"));
                prixs.setCellValueFactory(new PropertyValueFactory<Guide, Integer>("prix"));
                actions.setCellFactory(col -> {
                    Button editButton = new Button("Edit");
                    Button deleteButton = new Button("Delete");

                    TableCell<Guide, Guide> cell = new TableCell<Guide, Guide>() {
                        @Override
                        public void updateItem(Guide guide, boolean empty) {
                            super.updateItem(guide, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                HBox pane = new HBox(editButton, deleteButton);
                                setGraphic(pane);
                            }
                        }
                    };
                    deleteButton.setOnAction(event -> {
                        Trip trip = (Trip) table.getItems().get(cell.getTableRow().getIndex());
                        tripService.Supprimer(trip.getId());
                        trips.remove(cell.getTableRow().getIndex());
                        table.getItems().remove(cell.getTableRow().getIndex());
                    });
                    editButton.setOnAction(event -> {
                        tripSelected = (Trip) table.getItems().get(cell.getTableRow().getIndex());
                        try {
                            Main.setRoot("addEditTrip");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    return cell;
                });

                ObservableList<Trip> data = FXCollections.observableArrayList(
                        trips.get(i)
                );
                table.getItems().addAll(data);
            }
        } else if (tripSelected != null) {
            id.setText(String.valueOf(tripSelected.getId()));
            name.setText(tripSelected.getName());
            date.setValue(tripSelected.getDate());
            nbSejour.setText(String.valueOf(tripSelected.getNbSejour()));
            prix.setText(String.valueOf(tripSelected.getPrix()));
        }
    }

    public void saveTrip(ActionEvent actionEvent) throws IOException {
        if (tripSelected != null) {
            tripSelected.setName(name.getText());
            tripSelected.setDate(date.getValue());
            tripSelected.setNbSejour(Integer.parseInt(nbSejour.getText()));
            tripSelected.setPrix(Float.parseFloat(prix.getText()));
            tripService.Modifier(tripSelected);
            tripSelected = null;
        } else {
            Trip trip = new Trip((int) (Math.random() * 100), name.getText(), date.getValue(), Integer.parseInt(nbSejour.getText()), Float.parseFloat(prix.getText()), guideSelected.getId());
            tripService.Ajouter(trip);

        }
        Main.setRoot("tripsOfGuide");
    }
}
