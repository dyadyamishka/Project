package org.example.observer;

import org.example.entity.Habit;

import java.time.LocalDate;

public interface Observer {
    void update(Habit habit, LocalDate date);
}