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
import org.esprit.services.GuideService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.System.exit;

public class GuideController implements Initializable {


    private final GuideService guideService = new GuideService();
    public static Guide guideSelected;
    private static List<Guide> guides = new ArrayList<>();
    public Button btnOverview;
    public Button btnGuides;
    public Button btnTrips;
    public Button btnReservation;
    public Button btnSignout;
    public Pane pnlOverview;
    public Pane pnlGuides;
    public TextField search;
    private boolean add = false;
    // add or edit Guide
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField age;
    // table guides
    @FXML
    private TableView<Guide> table;
    @FXML
    private TableColumn<Guide, Integer> ids;
    @FXML
    private TableColumn<Guide, String> names;
    @FXML
    private TableColumn<Guide, Integer> ages;
    @FXML
    private TableColumn<Guide, Guide> actions;

    private String message;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (url.getPath().endsWith("Guides.fxml")) {
            search.textProperty().addListener((observable, oldValue, newValue) -> {
                guides=guideService.getSearchGuidesByName(newValue);
                table.setItems(FXCollections.observableArrayList(guides));
            });

            guides = guideService.Afficher();
            for (int i = 0; i < guides.size(); i++) {
                ids.setCellValueFactory(new PropertyValueFactory<Guide, Integer>("id"));
                names.setCellValueFactory(new PropertyValueFactory<Guide, String>("name"));
                ages.setCellValueFactory(new PropertyValueFactory<Guide, Integer>("age"));
                actions.setCellFactory(col -> {
                    Button tripsButton = new Button("Trips");
                    Button editButton = new Button("Edit");
                    Button deleteButton = new Button("Delete");

                    TableCell<Guide, Guide> cell = new TableCell<Guide, Guide>() {
                        @Override
                        public void updateItem(Guide guide, boolean empty) {
                            super.updateItem(guide, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                HBox pane = new HBox(tripsButton, editButton, deleteButton);
                                setGraphic(pane);
                            }
                        }
                    };
                    deleteButton.setOnAction(event -> {
                        Guide guide = table.getItems().get(cell.getTableRow().getIndex());
                        guideService.Supprimer(guide.getId());
                        guides.remove(cell.getTableRow().getIndex());
                        table.getItems().remove(cell.getTableRow().getIndex());
                    });

                    editButton.setOnAction(event -> {
                        Guide guide = table.getItems().get(cell.getTableRow().getIndex());
                        guideSelected = guide;
                        try {
                            Main.setRoot("addEditGuide");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                    tripsButton.setOnAction(event -> {
                        guideSelected = table.getItems().get(cell.getTableRow().getIndex());
                        try {
                            Main.setRoot("tripsOfGuide");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    return cell;
                });

                ObservableList<Guide> data = FXCollections.observableArrayList(
                        guides.get(i)
                );
                table.getItems().addAll(data);
            }
        } else if (guideSelected != null) {
            id.setText(guideSelected.getId().toString());
            name.setText(guideSelected.getName());
            age.setText(guideSelected.getAge().toString());
        }
    }

    @FXML
    private void addGuide() throws IOException {
        add = true;
        Main.setRoot("Overview");
    }

    @FXML
    private void editGuide() throws IOException {
        add = false;
        Main.setRoot("Overview");
    }

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
        Main.setRoot("addEditGuide");
    }

    public void saveGuide(ActionEvent actionEvent) throws IOException {

        if (!letter_Validation(name)) {
            showAlertWithHeaderText("le nom", message);
            return;
        }

        if (!numeric_Validation(age)) {
            showAlertWithHeaderText("l'age", message);
            return;
        }

        if (guideSelected != null) {
            guideSelected.setName(name.getText());
            guideSelected.setAge(Integer.parseInt(age.getText()));
            guideService.Modifier(guideSelected);
            guideSelected = null;
        } else {
            Guide guide = new Guide((int) (Math.random() * 100), name.getText(), Integer.parseInt(age.getText()));
            guideService.Ajouter(guide);

        }
        Main.setRoot("Guides");
    }

    private void showAlertWithHeaderText(String champ, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur Saisie");
        alert.setHeaderText("Results: " + champ + " " + message);
        alert.setContentText("veuillez corriger votre saisie SVP");

        alert.showAndWait();
    }

    private boolean numeric_Validation(TextField text) {

        if (text.getText().length() == 0) {
            message = "est obligatoire";
            return false;
        }
        if (!text.getText().matches("[0-9]*")) {
            message = "doit etre en chiffre";
            return false;
        }
        if (text.getText().length() > 2) {
            message = "ne depasse pas deux chiffre ";
            return false;
        }

        return true;
    }

    private boolean letter_Validation(TextField text) {

        if (text.getText().length() == 0) {
            message = "est obligatoire";
            return false;
        }
        if (!text.getText().matches("[A-Za-z]*")) {
            message = "doit etre en caractere";
            return false;
        }
        if (text.getText().length() > 7) {
            message = "ne depasse pas 7 caracteres ";
            return false;
        }

        return true;
    }
}
