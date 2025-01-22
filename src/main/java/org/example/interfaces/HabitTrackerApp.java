package org.example.interfaces;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.entity.HabitTracker;
import org.example.entity.User;
import org.example.entity.UserManagement;
import org.example.screens.AddHabitScreen;
import org.example.screens.CompleteHabitScreen;
import org.example.screens.StatisticsScreen;
import org.example.screens.ViewHabitScreens;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HabitTrackerApp extends Application {
    private static List<User> users = new ArrayList<>();
    private static HabitTracker habitTracker = new HabitTracker();
    public static UserManagement userManagement;
    @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("Отслеживатель");

        userManagement = new UserManagement(users, habitTracker);

        VBox mainLayout = new VBox(10);
        Button addHabitButton = new Button("Добавить привычку");
        Button viewHabitButton = new Button("Посмотреть привычки");
        Button statsButton = new Button("Статистика привычки");
        Button completeHabitButton = new Button("Завершить привычку");
        Button createUserButton = new Button("Создать пользователя");
        Button selectUserButton = new Button("Выбрать пользователя");

        createUserButton.setOnAction(e -> {
            userManagement.createUser ();
        });

        selectUserButton.setOnAction(e -> {
            userManagement.selectUser ();
        });


        addHabitButton.setOnAction(event -> {
            User currentUser  = userManagement.getCurrentUser ();
            if (currentUser  != null) {
                AddHabitScreen addHabitScreen = new AddHabitScreen(habitTracker, currentUser );
                addHabitScreen.showScreen();
            } else {
                System.out.println("Пожалуйста, выберите пользователя перед добавлением привычки.");
            }
        });

        viewHabitButton.setOnAction(event -> {
            User currentUser  = userManagement.getCurrentUser ();
            if (currentUser  != null) {
                ViewHabitScreens viewHabitsScreen = new ViewHabitScreens(habitTracker, currentUser );
                viewHabitsScreen.showScreen();
            } else {
                System.out.println("Пожалуйста, выберите пользователя перед просмотром привычек.");
            }
        });

        statsButton.setOnAction(event -> {
            User currentUser  = userManagement.getCurrentUser ();
            if (currentUser  != null) {
                StatisticsScreen statisticsScreen = new StatisticsScreen(habitTracker, currentUser );
                statisticsScreen.showScreen();
            } else {
                System.out.println("Пожалуйста, выберите пользователя перед просмотром статистики.");
            }
        });

        completeHabitButton.setOnAction(event -> {
            User currentUser  = userManagement.getCurrentUser ();
            if (currentUser  != null) {
                CompleteHabitScreen completeHabitScreen = new CompleteHabitScreen(habitTracker, currentUser );
                completeHabitScreen.showScreen();
            } else {
                System.out.println("Пожалуйста, выберите пользователя перед завершением привычки.");
            }
        });

        mainLayout.getChildren().addAll(createUserButton, selectUserButton, addHabitButton, viewHabitButton, statsButton, completeHabitButton);
        Scene mainScene = new Scene(mainLayout, 300, 200);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}



