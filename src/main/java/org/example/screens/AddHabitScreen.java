package org.example.screens;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.entity.Habit;
import org.example.entity.HabitRepository;
import org.example.entity.HabitTracker;
import org.example.entity.User;

import static org.example.interfaces.HabitTrackerApp.userManagement;

public class AddHabitScreen implements HabitScreen {


    private HabitTracker habitTracker;
    private User user;


    public AddHabitScreen(HabitTracker habitTracker, User user) {
        this.habitTracker = habitTracker;
        this.user = user;
    }


    @Override
    public void showScreen() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);

        TextField habitField = new TextField();
        habitField.setPromptText("Введите название привычки");

        Button addButton = new Button("Добавить привычку");
        addButton.setOnAction(e -> {
            String habitName = habitField.getText();
            if (!habitName.isEmpty()) {
                Habit newHabit = new Habit();
                newHabit.setName(habitName);
                habitTracker.addHabitForUser(user, newHabit);
                System.out.println("Привычка '" + habitName + "' добавлена для пользователя " + user.getName());
                stage.close();
            }
        });

        layout.getChildren().addAll(habitField, addButton);
        Scene scene = new Scene(layout, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Добавить привычку");
        stage.show();
    }
}
