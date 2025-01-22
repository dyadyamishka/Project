package org.example.screens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.entity.Habit;
import org.example.entity.HabitTracker;
import org.example.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class StatisticsScreen implements HabitScreen {
    private HabitTracker habitTracker;
    private User user;

    public StatisticsScreen(HabitTracker habitTracker, User user) {
        this.habitTracker = habitTracker;
        this.user = user;
    }
    @Override
    public void showScreen() {
        Stage statsStage = new Stage();
        statsStage.setTitle("Статистика");

        VBox statsLayout = new VBox(10);
        Label statsLabel = new Label("Статистика по привычкам:");

        ComboBox<Habit> habitComboBox = new ComboBox<>();
        habitComboBox.getItems().addAll(habitTracker.getUserHabits(user));


        ListView<String> statsListView = new ListView<>();
        ObservableList<String> statsItems = FXCollections.observableArrayList();


        Button showStatsButton = new Button("Показать статистику");
        showStatsButton.setOnAction(e -> {
            Habit selectedHabit = habitComboBox.getValue();
            if (selectedHabit != null) {
                habitTracker.getHabitStatistics(user, selectedHabit);

                Map<User, Map<Habit, List<LocalDate>>> userHabitCompletionDates = habitTracker.getUserHabitCompletionDates();
                Map<Habit, List<LocalDate>> userHabits = userHabitCompletionDates.getOrDefault(user, Map.of());


                List<LocalDate> completionDates = userHabits.get(selectedHabit);
                statsItems.clear();
                if (completionDates != null) {
                    String habitInfo = selectedHabit.getName() + " - Завершено: " + completionDates.size() + " раз(а)";
                    statsItems.add(habitInfo);
                    statsItems.addAll(completionDates.stream().map(LocalDate::toString).toList());
                } else {
                    statsItems.add("Нет данных о выполнениях для выбранной привычки.");
                }
                statsListView.setItems(statsItems);
            } else {
                statsItems.add("Пожалуйста, выберите привычку.");
            }
        });


        Button closeButton = new Button("Закрыть");
        closeButton.setOnAction(e -> statsStage.close());

        statsLayout.getChildren().addAll(statsLabel, habitComboBox, showStatsButton, statsListView, closeButton);
        Scene statsScene = new Scene(statsLayout, 300, 350);
        statsStage.setScene(statsScene);
        statsStage.show();
    }
}
