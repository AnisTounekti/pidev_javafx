module org.esprit {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;
    requires mail;
    opens org.esprit.entities to javafx.base;
  opens org.esprit to javafx.fxml;
  exports org.esprit;
  exports org.esprit.controller;
  opens org.esprit.controller to javafx.fxml;

}
