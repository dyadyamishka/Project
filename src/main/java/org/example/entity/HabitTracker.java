package org.example.entity;

import org.example.observer.Observable;
import org.example.observer.Observer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HabitTracker implements Observable {
    private Map<User, Map<Habit, List<LocalDate>>> userHabitCompletionDates;
    private Map<Habit, List<Observer>> habitObservers;
    private Map<User, List<Habit>> userHabits;


    public HabitTracker() {
        userHabits = new HashMap<>();
        this.userHabitCompletionDates = new HashMap<>();
        this.habitObservers = new HashMap<>();
    }

    public Map<User, Map<Habit, List<LocalDate>>> getUserHabitCompletionDates() {
        return userHabitCompletionDates;
    }

    public void addHabitForUser(User user, Habit habit) {
        if (user == null || habit == null) {
            throw new IllegalArgumentException("User  or Habit cannot be null");
        }

        user.addHabit(habit);
        userHabitCompletionDates.putIfAbsent(user, new HashMap<>());
        userHabitCompletionDates.get(user).put(habit, new ArrayList<>());

        userHabits.putIfAbsent(user, new ArrayList<>());
        userHabits.get(user).add(habit);
    }


    public List<Habit> getUserHabits(User user) {
        return userHabits.getOrDefault(user, new ArrayList<>());
    }


    public void markHabitAsCompleted(User user, Habit habit, LocalDate date) {
        if (userHabitCompletionDates.containsKey(user) && userHabitCompletionDates.get(user).containsKey(habit)) {
            userHabitCompletionDates.get(user).get(habit).add(date);
            notifyObservers(habit, date); // Уведомляем наблюдателей
        } else {
            System.out.println("Привычка не найдена для пользователя!");
        }
    }

    public void getHabitStatistics(User user, Habit habit) {
        if (userHabitCompletionDates.containsKey(user) && userHabitCompletionDates.get(user).containsKey(habit)) {
            List<LocalDate> completionDates = userHabitCompletionDates.get(user).get(habit);
            System.out.println("Статистика для привычки: " + habit.getName() + " пользователя " + user.getName());
            System.out.println("Количество выполнений: " + completionDates.size());
            System.out.println("Даты выполнения: " + completionDates);
        } else {
            System.out.println("Привычка не найдена для пользователя!");
        }
    }

    @Override
    public void addObserver(Habit habit, Observer observer) {
        habitObservers.computeIfAbsent(habit, k -> new ArrayList<>()).add(observer);
    }

    @Override
    public void removeObserver(Habit habit, Observer observer) {
        List<Observer> observers = habitObservers.get(habit);
        if (observers != null) {
            observers.remove(observer);
        }
    }

    @Override
    public void notifyObservers(Habit habit, LocalDate date) {
        List<Observer> observers = habitObservers.get(habit);
        if (observers != null) {
            for (Observer observer : observers) {
                observer.update(habit, date);
            }
        }
    }

    public void subscribe(User observer, User targetUser , Habit habit) {

        Map<Habit, List<LocalDate>> targetHabits = userHabitCompletionDates.get(targetUser );

        if (targetHabits != null && targetHabits.containsKey(habit)) {

            addObserver(habit, observer);
        }
        }
    }
