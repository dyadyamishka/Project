package org.example.entity;

import java.util.List;
import java.util.Scanner;

public class UserManagement {
    private List<User> users;
    private HabitTracker habitTracker;
    private User currentUser ;

    public UserManagement(List<User> users, HabitTracker habitTracker) {
        this.users = users;
        this.habitTracker = habitTracker;
    }

    public void createUser () {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя нового пользователя: ");
        String userName = scanner.nextLine();
        User newUser  = new User(userName);
        users.add(newUser );

        System.out.print("Хотите переключиться на работу с пользователем " + userName + "? (да/нет): ");
        String switchResponse = scanner.nextLine();
        if (switchResponse.equalsIgnoreCase("да")) {
            currentUser  = newUser ;
        }

        offerSubscription(newUser );
    }

    public User getCurrentUser () {
        return currentUser ;
    }

    public void selectUser () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите пользователя из списка:");
        for (int i = 0; i < users.size(); i++) {
            System.out.println((i + 1) + ". " + users.get(i).getName());
        }

        int userIndex = -1;
        while (true) {
            System.out.print("Введите номер пользователя: ");

            // Проверка ввода
            if (scanner.hasNextInt()) {
                userIndex = scanner.nextInt() - 1;

                if (userIndex >= 0 && userIndex < users.size()) {
                    currentUser  = users.get(userIndex);
                    System.out.println("Теперь вы работаете с пользователем: " + currentUser .getName());
                    offerSubscription(currentUser );
                    break;
                } else {
                    System.out.println("Неверный номер пользователя. Пожалуйста, попробуйте снова.");
                }
            } else {
                System.out.println("Пожалуйста, введите целое число.");
                scanner.next();
            }
        }
    }

    private void offerSubscription(User user) {
        Scanner scanner = new Scanner(System.in);
        String subscribeResponse;

        while (true) {
            System.out.print("Хотите подписать пользователя " + user.getName() + " на привычки других пользователей? (да/нет): ");
            subscribeResponse = scanner.nextLine().trim().toLowerCase();

            if (subscribeResponse.equals("да")) {

                subscribeToAllOrOne(user);
                break;
            } else if (subscribeResponse.equals("нет")) {
                System.out.println("Вы работаете с пользователем " + user.getName() + ". Можете вернуться в главное меню или создать нового пользователя.");
                break;
            } else {
                System.out.println("Неверный ввод. Пожалуйста, введите 'да' или 'нет'.");
            }
        }
    }

    private void subscribeToAllOrOne(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Хотите подписаться на все привычки другого пользователя или на одну? (все/одна): ");
        String choice = scanner.nextLine().trim().toLowerCase();

        if (choice.equals("все")) {
            System.out.print("Введите имя пользователя, на привычки которого вы хотите подписаться: ");
            String targetUserName = scanner.nextLine();


            User targetUser  = users.stream()
                    .filter(u -> u.getName().equals(targetUserName))
                    .findFirst()
                    .orElse(null);

            if (targetUser  != null) {

                for (Habit habit : targetUser .getHabits()) {
                    habitTracker.subscribe(user, targetUser , habit);
                    System.out.println(user.getName() + " подписан на привычку " + habit.getName() + " пользователя " + targetUser .getName());
                }
            } else {
                System.out.println("Пользователь не найден.");
            }
        } else if (choice.equals("одна")) {
            System.out.print("Введите имя пользователя, на привычки которого вы хотите подписаться: ");
            String targetUserName = scanner.nextLine();


            User targetUser  = users.stream()
                    .filter(u -> u.getName().equals(targetUserName))
                    .findFirst()
                    .orElse(null);

            if (targetUser  != null) {

                System.out.println("Список привычек пользователя " + targetUser .getName() + ":");
                List<Habit> habits = targetUser .getHabits();
                for (int i = 0; i < habits.size(); i++) {
                    System.out.println((i + 1) + ". " + habits.get(i).getName());
                }

                System.out.print("Введите номер привычки, на которую хотите подписаться: ");
                int habitIndex = scanner.nextInt() - 1;

                if (habitIndex >= 0 && habitIndex < habits.size()) {
                    Habit targetHabit = habits.get(habitIndex);
                    habitTracker.subscribe(user, targetUser , targetHabit);
                    System.out.println(user.getName() + " подписан на привычку " + targetHabit.getName() + " пользователя " + targetUser .getName());


                    offerAdditionalSubscription(user, targetUser );
                } else {
                    System.out.println("Неверный номер привычки.");
                }
            } else {
                System.out.println("Пользователь не найден.");
            }
        } else {
            System.out.println("Неверный выбор. Пожалуйста, введите 'все' или 'одна'.");
            subscribeToAllOrOne(user);
        }
    }

    private void offerAdditionalSubscription(User user, User targetUser ) {
        Scanner scanner = new Scanner(System.in);
        String additionalResponse;

        while (true) {
            System.out.print("Хотите подписаться на еще одну привычку пользователя " + targetUser .getName() + "? (да/нет): ");
            additionalResponse = scanner.nextLine().trim().toLowerCase();

            if (additionalResponse.equals("да")) {
                System.out.print("Введите название привычки, на которую хотите подписаться: ");
                String habitName = scanner.nextLine();
                Habit targetHabit = targetUser .getHabitByName(habitName);

                if (targetHabit != null) {
                    habitTracker.subscribe(user, targetUser , targetHabit);
                    System.out.println(user.getName() + " подписан на привычку " + targetHabit.getName() + " пользователя " + targetUser .getName());
                } else {
                    System.out.println("Привычка не найдена.");
                }
            } else if (additionalResponse.equals("нет")) {
                System.out.println("Вы можете вернуться в главное меню или создать нового пользователя.");
                break;
            } else {
                System.out.println("Неверный ввод. Пожалуйста, введите 'да' или 'нет'.");
            }
        }
    }
}