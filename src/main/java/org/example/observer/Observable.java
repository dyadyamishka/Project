package org.example.observer;


import org.example.entity.Habit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface Observable {
    void addObserver(Habit habit, Observer observer);
    void removeObserver(Habit habit, Observer observer);
    void notifyObservers(Habit habit, LocalDate date);
}