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
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

import static com.example.jobfinderclient.Client.*;

public class JobApplication extends Application {
    private static final ObservableList<Job> data = FXCollections.observableArrayList();
    private static TableView<Job> tableView;


    @Override
    public void start(Stage primaryStage) {
        // Create form elements
        TextField titleField = new TextField();
        TextField descField = new TextField();
        TextField idField = new TextField();
        TextField cityField = new TextField();
        Button addButton = new Button("Add Job");
        Button save = new Button("Save to server");
        Button delete = new Button("Delete from server");
        Button findJobById = new Button("Find job by id");
        Button findJobByDesc = new Button("Find job by description");
        Button findJobByTitle = new Button("Find job by title");
        Button findJobByCity = new Button("Find job by city");
        Button getAllJobs = new Button("Get all jobs");


        tableView = new TableView<>();
        TableColumn<Job, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setStyle("-fx-background-color: #CCCCCC;");
        TableColumn<Job, String> idColumn = new TableColumn<>("Id");
        idColumn.setStyle("-fx-background-color: #CCCCCC;");
        TableColumn<Job, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setStyle("-fx-background-color: #CCCCCC;");
        TableColumn<Job, String> cityColumn = new TableColumn<>("City");
        cityColumn.setStyle("-fx-background-color: #CCCCCC;");

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("jobId"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));

        tableView.getColumns().addAll(titleColumn, descriptionColumn, idColumn, cityColumn);
        tableView.setItems(data);

        addButton.setOnAction(e -> {
            long id = Long.parseLong(idField.getText());
            String title = titleField.getText();
            String description = descField.getText();
            String city = cityField.getText();

            Job job = new Job(id, title, description, city);

            tableView.getItems().add(job);

            idField.clear();
            titleField.clear();
            descField.clear();
            cityField.clear();
        });

        save.setOnAction(e -> {
            Job selectedJob = tableView.getSelectionModel().getSelectedItem();
            if (selectedJob != null) {
                Long jobId = selectedJob.getJobId();
                Map<String, Object> requestData = new HashMap<>();
                requestData.put("job", new Job(jobId, selectedJob.getTitle(), selectedJob.getDescription(), selectedJob.getCity()));
                Request request = new Request("saveJob", requestData);
                saveToServer(request);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Job saved to server.");
            } else {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please select a job to save.");
            }
        });

        delete.setOnAction(e -> {
            Job selectedJob = tableView.getSelectionModel().getSelectedItem();
            if (selectedJob != null) {
                Long jobId = selectedJob.getJobId();
                Map<String, Object> requestData = new HashMap<>();
                requestData.put("job", new Job(jobId, selectedJob.getTitle(), selectedJob.getDescription(), selectedJob.getCity()));
                Request request = new Request("deleteJob", requestData);
                saveToServer(request);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Job deleted from server.");
            } else {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please select a job to delete.");
            }
        });

        findJobById.setOnAction(id -> {
            if (!idField.getText().isEmpty()) {
                Map<String, Object> requestData = new HashMap<>();
                requestData.put("jobId", idField.getText());
                Request request = new Request("findJobById", requestData);
                sendToServer(request);
            } else {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please enter a job id to search.");
            }
        });

        findJobByDesc.setOnAction(desc -> {
            if (!descField.getText().isEmpty()) {
                Map<String, Object> requestData = new HashMap<>();
                requestData.put("jobDesc", descField.getText());
                Request request = new Request("findJobByDesc", requestData);
                sendToServer(request);
            } else {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please enter a job description to search.");
            }
        });

        findJobByTitle.setOnAction(title -> {
            if (!titleField.getText().isEmpty()) {
                Map<String, Object> requestData = new HashMap<>();
                requestData.put("jobTitle", titleField.getText());
                Request request = new Request("findJobByTitle", requestData);
                sendToServer(request);
            } else {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please enter a job id to search.");
            }
        });

        findJobByCity.setOnAction(city->{
            if (!cityField.getText().isEmpty()) {
                Map<String, Object> requestData = new HashMap<>();
                requestData.put("jobCity", cityField.getText());
                Request request = new Request("findJobByCity", requestData);
                sendToServer(request);
            } else {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please enter a job id to search.");
            }
        });

        getAllJobs.setOnAction(job -> {
            Request request = new Request("getAllJobs", null);
            sendToServer(request);
        });

        Label titleLabel = new Label("Job Finder Application");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-alignment: center;");

        VBox formLayout = new VBox(10);
        formLayout.setPadding(new Insets(10));
        formLayout.getChildren().addAll(
                titleLabel,
                new Label("Title:"),
                titleField,
                findJobByTitle,
                new Label("Id:"),
                idField,
                findJobById,
                new Label("Description:"),
                descField,
                findJobByDesc,
                new Label("City:"),
                cityField,
                findJobByCity,
                addButton,
                save,
                delete,
                getAllJobs
        );


        HBox contentBox = new HBox(20);
        contentBox.setPadding(new Insets(10));
        contentBox.getChildren().addAll(formLayout, tableView);
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #F5DEB3;");
        root.getChildren().add(contentBox);
        Scene scene = new Scene(root, 700, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Job Application");
        primaryStage.show();
    }

    private static void sendToServer(Request request) {
        try {
            if(requestToServer(request).getJobs()!=null) {
                data.setAll(requestToServer(request).getJobs());
            } else{
                data.clear();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        tableView.setItems(data);
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
