package org.example.entity;

import org.example.observer.Observer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User implements Observer {
    private String username;
    private List<Habit> habits;

    public User(String username) {
        this.username = username;
        this.habits = new ArrayList<>();
    }


    public String getName() {
        return username;
    }


    public void addHabit(Habit habit) {
        habits.add(habit);
    }
        public List<Habit> getHabits(){
        return habits;
    }

    public Habit getHabitByName(String habitName) {
        for (Habit habit : habits) {
            if (habit.getName().equals(habitName)) {
                return habit;
            }
        }
        return null;
    }

    @Override
    public void update(Habit habit, LocalDate date) {
        System.out.println("Пользователь " + username + " получил уведомление: привычка " + habit.getName() + " выполнена " + date);
    }
}