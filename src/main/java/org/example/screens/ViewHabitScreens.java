package org.example.screens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.entity.Habit;
import org.example.entity.HabitRepository;
import org.example.entity.HabitTracker;
import org.example.entity.User;
import org.example.screens.HabitScreen;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ViewHabitScreens implements HabitScreen {

    private HabitTracker habitTracker;
    private User user;

    public ViewHabitScreens(HabitTracker habitTracker, User user) {
        this.habitTracker = habitTracker;
        this.user = user;
    }
    @Override
    public void showScreen() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);

        ListView<String> habitListView = new ListView<>();


        Map<User, Map<Habit, List<LocalDate>>> userHabitCompletionDates = habitTracker.getUserHabitCompletionDates();
        Map<Habit, List<LocalDate>> userHabits = userHabitCompletionDates.getOrDefault(user, Map.of());

        ObservableList<String> habitItems = FXCollections.observableArrayList();

        for (Map.Entry<Habit, List<LocalDate>> entry : userHabits.entrySet()) {
            Habit habit = entry.getKey();
            List<LocalDate> completionDates = entry.getValue();

            String habitInfo = habit.getName() + " - Завершено: " + completionDates.size() + " раз(а)";
            habitItems.add(habitInfo);
        }

        habitListView.setItems(habitItems);

        Button closeButton = new Button("Закрыть");
        closeButton.setOnAction(e -> stage.close());

        layout.getChildren().addAll(habitListView, closeButton);
        Scene scene = new Scene(layout, 300, 400);
        stage.setScene(scene);
        stage.setTitle("Посмотреть привычки");
        stage.show();
    }
}