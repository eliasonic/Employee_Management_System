package com.example.employee_management_system;

import com.example.employee_management_system.domain.Employee;
import com.example.employee_management_system.exceptions.EmployeeNotFoundException;
import com.example.employee_management_system.exceptions.InvalidEmployeeArgumentException;
import com.example.employee_management_system.repository.EmployeeRepository;
import com.example.employee_management_system.service.EmployeeCRUDService;
import com.example.employee_management_system.service.EmployeeQueryService;
import com.example.employee_management_system.service.EmployeeSortService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));

        Scene scene = new Scene(root, 900, 650);

        stage.setTitle("Employee Management System");
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
//        launch(args);

        EmployeeRepository repository = new EmployeeRepository();
        EmployeeCRUDService crudService = new EmployeeCRUDService(repository);
        EmployeeSortService sortService = new EmployeeSortService(repository);
        EmployeeQueryService queryService = new EmployeeQueryService(repository);

        /* CRUD */
        // Add employee
        try {
            Employee<Integer> newEmployee = new Employee<>(1, "Elias", "Backend", 75000.0, 4.5, 5, true);
            crudService.addEmployee(newEmployee);
            System.out.println("Employee added successfully.");
        } catch (InvalidEmployeeArgumentException e) {
            System.out.println("Error adding employee: " + e.getMessage());
        }

        // Delete employee
        try {
            crudService.removeEmployee(1);
            System.out.println("Employee deleted successfully.");
        } catch (EmployeeNotFoundException e) {
            System.out.println("Error deleting employee: " + e.getMessage());
        }

        /* SORTING & FILTERING */
        // Sorting an empty list
        try {
            sortService.sortByPerformance().forEach(System.out::println);
        } catch (IllegalStateException e) {
            System.out.println("Error sorting: " + e.getMessage());
        }

        // Sorting a list with some employee objects having null performance rating
        crudService.addEmployee(new Employee<>(1, "Elias", "Backend", 75000.0, 4.5, 5, true));
        crudService.addEmployee(new Employee<>(2, "John", "Backend", 75000.0, null, 5, true));
        crudService.addEmployee(new Employee<>(3, "Jane", "Backend", 75000.0, 3.5, 5, true));
        crudService.addEmployee(new Employee<>(4, "Alice", "Backend", 75000.0, 4.0, 5, true));

        System.out.println("Employees before sorting:");
        repository.findAll().forEach(System.out::println);
        System.out.println();

        System.out.println("Employees after sorting by performance:");
        sortService.sortByPerformance().forEach(System.out::println);


        // Searching a non-existing employee
        try {
            List<Employee<Integer>> results = queryService.filterByName("Elias");
            results.forEach(System.out::println);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }
}