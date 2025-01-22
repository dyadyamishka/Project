package org.example.screens;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.entity.Habit;
import org.example.entity.HabitTracker;
import org.example.entity.User;

import java.time.LocalDate;


public class CompleteHabitScreen implements HabitScreen{

    private HabitTracker habitTracker;
    private User user;

    public CompleteHabitScreen(HabitTracker habitTracker, User user) {
        this.habitTracker = habitTracker;
        this.user = user;
    }
    @Override
    public void showScreen() {
        Stage stage = new Stage();
        stage.setTitle("Завершение привычки");

        ComboBox<Habit> habitComboBox = new ComboBox<>();
        habitComboBox.getItems().addAll(habitTracker.getUserHabits(user));

        DatePicker datePicker = new DatePicker(); // Выбор даты
        Button completeButton = new Button("Отметить как выполненную");

        completeButton.setOnAction(e -> {
            Habit selectedHabit = habitComboBox.getValue();
            LocalDate selectedDate = datePicker.getValue();
            if (selectedHabit != null && selectedDate != null) {
                habitTracker.markHabitAsCompleted(user, selectedHabit, selectedDate);
                System.out.println("Привычка " + selectedHabit.getName() + " отмечена как выполненная на " + selectedDate);
                stage.close(); // Закрытие экрана после выполнения
            } else {
                System.out.println("Пожалуйста, выберите привычку и дату.");
            }
        });

        VBox layout = new VBox(10, habitComboBox, datePicker, completeButton);
        Scene scene = new Scene(layout, 300, 200);
        stage.setScene(scene);
        stage.show();
    }
}
