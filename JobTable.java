package com.example.jobfinderclient;

import com.example.jobfinderclient.model.Request;
import com.example.jobfinderclient.model.Job;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JobTable extends Application {
    private final ObservableList<Job> Jobs = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Job Addition");

        // Create form elements
        TextField titleField = new TextField();
        TextField idField = new TextField();
        TextField descriptionField = new TextField();
        TextField cityField = new TextField();
        Button addButton = new Button("Add Job");

        // Create TableView and columns
        TableView<Job> tableView = new TableView<>();
        TableColumn<Job, String> titleColumn = new TableColumn<>("Title");
        TableColumn<Job, String> idColumn = new TableColumn<>("ID");
        TableColumn<Job, String> descriptionColumn = new TableColumn<>("Description");
        TableColumn<Job, String> cityColumn = new TableColumn<>("City");


        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("jobId"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));

        tableView.getColumns().addAll(titleColumn, idColumn, descriptionColumn, cityColumn);
        tableView.setItems(Jobs);

        // Add button click event
        addButton.setOnAction(e -> {
            String title = titleField.getText();
            Long id = Long.parseLong(idField.getText());
            String description = descriptionField.getText();
            String city = cityField.getText();

            Job job = new Job(id, title, description, city);
            Jobs.add(job);
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("job", job);
            Request request = new Request("addJob", reqMap);
            Client.saveToServer(request);

            // Clear text fields after adding to the table
            titleField.clear();
            idField.clear();
            descriptionField.clear();
            cityField.clear();
        });

        // Create layout
        VBox formLayout = new VBox(10);
        formLayout.setPadding(new Insets(10));
        formLayout.getChildren().addAll(
                new Label("Title:"),
                titleField,
                new Label("Id:"),
                idField,
                new Label("Description:"),
                descriptionField,
                new Label("City:"),
                cityField,
                addButton
        );

        HBox root = new HBox(20);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(formLayout, tableView);

        primaryStage.setScene(new Scene(root, 100, 400));
        primaryStage.show();
    }
}