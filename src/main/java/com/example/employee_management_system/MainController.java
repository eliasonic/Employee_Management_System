package com.example.employee_management_system;

import com.example.employee_management_system.domain.Employee;
import com.example.employee_management_system.exceptions.InvalidDepartmentException;
import com.example.employee_management_system.exceptions.InvalidEmployeeArgumentException;
import com.example.employee_management_system.exceptions.InvalidNameException;
import com.example.employee_management_system.exceptions.InvalidSalaryException;
import com.example.employee_management_system.repository.EmployeeRepository;
import com.example.employee_management_system.service.*;
import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.*;

public class MainController {
    // Services initialization
    private final EmployeeRepository<Integer> repository = new EmployeeRepository<>();
    private final EmployeeCRUDService<Integer> crudService = new EmployeeCRUDService<>(repository);
    private final EmployeeQueryService<Integer> queryService = new EmployeeQueryService<>(repository);
    private final EmployeeSortService<Integer> sortService = new EmployeeSortService<>(repository);
    private final EmployeeSalaryService<Integer> salaryService = new EmployeeSalaryService<>(repository);

    @FXML private GridPane gridTable;
    @FXML private TextField nameField;
    @FXML private TextField deptField;
    @FXML private TextField salaryField;

    private HBox headerRow;
    private Employee<Integer> selectedEmployee;

    @FXML
    public void initialize() {
        refreshGridTable();
    }

    private void refreshGridTable(List<Employee<Integer>> employees) {
        gridTable.getChildren().clear();
        clearRowHighlights();
        selectedEmployee = null;

        Object[][] columns = {
                {60.0, Pos.CENTER_LEFT},    // [width, alignment]
                {150.0, Pos.CENTER_LEFT},
                {120.0, Pos.CENTER_LEFT},
                {120.0, Pos.CENTER},
                {80.0, Pos.CENTER},
                {60.0, Pos.CENTER},
                {60.0, Pos.CENTER}
        };

        // Adds header row
        headerRow = new HBox(10);
        headerRow.setAlignment(Pos.CENTER_LEFT);
        headerRow.setPadding(new Insets(10, 15, 10, 15));
        headerRow.setStyle("-fx-background-color: #42a5f5; -fx-background-radius: 3;");

        String[] headers = {"ID", "Name", "Department", "Salary", "Rating", "Exp", "Active"};
        for (int i = 0; i < headers.length; i++) {
            Label header = new Label(headers[i]);
            header.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
            header.setMinWidth((double)columns[i][0]);
            header.setAlignment((Pos)columns[i][1]);
            headerRow.getChildren().add(header);
        }

        gridTable.add(headerRow, 0, 0);
        GridPane.setColumnSpan(headerRow, headers.length);

        // Adds employee data rows
        for (int row = 0; row < employees.size(); row++) {
            Employee<Integer> emp = employees.get(row);

            HBox dataRow = new HBox(10);
            dataRow.setAlignment(Pos.CENTER_LEFT);
            dataRow.setPadding(new Insets(5, 15, 5, 15));

            dataRow.getChildren().addAll(
                    createCell(emp.getEmployeeId().toString(), columns[0]),
                    createCell(emp.getName(), columns[1]),
                    createCell(emp.getDepartment(), columns[2]),
                    createCell(String.format("$%,.2f", emp.getSalary()), columns[3]),
                    createCell(String.format("%.1f", emp.getPerformanceRating()), columns[4]),
                    createCell(String.valueOf(emp.getYearsOfExperience()), columns[5]),
                    createCell(emp.isActive() ? "Yes" : "No", columns[6])
            );

            // Row selection
            dataRow.setOnMouseClicked(event -> {
                clearRowHighlights();
                selectedEmployee = emp;
                dataRow.setStyle("-fx-background-color: #e3f2fd; -fx-background-radius: 3;");
            });

            gridTable.add(dataRow, 0, row + 1);
            GridPane.setColumnSpan(dataRow, headers.length);
        }
    }

    // CRUD Operations
    @FXML
    private void handleAddEmployee() {
        try {
            int newId = crudService.getAllEmployees().stream()
                    .mapToInt(Employee::getEmployeeId)
                    .max().orElse(0) + 1;

            Random rand = new Random();
            double randomRating = 2.0 + (10.0 - 2.0) * rand.nextDouble(); // 2.0 - 10.0
            randomRating = Math.round(randomRating * 10) / 10.0;

            int randomExperience = 1 + rand.nextInt(7); // 1 - 7

            Employee<Integer> newEmployee = new Employee<>(
                    newId,
                    nameField.getText(),
                    deptField.getText(),
                    Double.parseDouble(salaryField.getText()),
                    randomRating,
                    randomExperience,
                    true
            );

            if (crudService.addEmployee(newEmployee)) {
                refreshGridTable();
                clearFormFields();
            } else {
                showAlert("Error", "Employee cannot be null");
            }
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid salary");
        } catch (InvalidEmployeeArgumentException e) {
            showAlert("Input Error", e.getMessage());
        } catch (Exception e) {
            showAlert("Error", "Failed to add employee");
        }
    }

    @FXML
    private void handleDeleteEmployee() {
        if (selectedEmployee != null) {
            Optional<ButtonType> result = showAlert("Confirm Deletion", "Are you sure you want to delete " + selectedEmployee.getName() + "?");

            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (crudService.removeEmployee(selectedEmployee.getEmployeeId())) {
                    refreshGridTable();
                    selectedEmployee = null;
                } else {
                    showAlert("Error", "Failed to delete employee");
                }
            }
        } else {
            showAlert("No Selection", "Please select an employee to delete");
        }
    }

    // Filtering Operations
    @FXML
    private void handleFilterByName() {
        Optional<String> result = showStringInputDialog("Enter employee name: ");
        result.ifPresent(name -> {
            if (!name.trim().isEmpty()) {
                refreshGridTable(queryService.filterByName(name));
            } else {
                showAlert("Input Error", "Please provide employee name to search");
            }
        });
    }

    @FXML
    private void handleFilterByDepartment() {
        Optional<String> result = showStringInputDialog("Enter department name: ");
        result.ifPresent(dept -> {
            if (!dept.trim().isEmpty()) {
                refreshGridTable(queryService.filterByDepartment(dept));
            } else {
                showAlert("Input Error", "Please provide department");
            }
        });
    }

    @FXML
    private void handleFilterBySalaryRange() {
        try {
            int min = showIntegerInputDialog("Enter minimum salary:", "0");
            int max = showIntegerInputDialog("Enter maximum salary:", "1000000");
            if (min > max) {
                showAlert("Input Error", "Minimum salary cannot be greater than maximum salary");
                return;
            }
            refreshGridTable(queryService.filterBySalaryRange(min, max));
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid salary range");
        }
    }

    @FXML
    private void handleFilterByHighPerformers() {
        try {
            double minRating = showDoubleInputDialog("Enter minimum rating to get top performers", "5.0");
            refreshGridTable(queryService.filterByRating(minRating));
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid rating");
        }
    }

    // Sorting Operations
    @FXML
    private void handleSortByExperience() {
        refreshGridTable(sortService.sortByExperience());
    }

    @FXML
    private void handleSortBySalary() {
        refreshGridTable(sortService.sortBySalary());
    }

    @FXML
    private void handleSortByPerformance() {
        refreshGridTable(sortService.sortByPerformance());
    }

    // Salary Operations
    @FXML
    private void handleGiveRaises() {
        try {
            double percentage = showDoubleInputDialog("Enter raise percentage:", "10.0");
            double minRating = showDoubleInputDialog("Minimum rating for raise:", "4.5");

            salaryService.applyRaise(percentage, minRating);
            refreshGridTable();
            showAlert("Success", String.format("Applied %.1f%% raise to employees with rating >= %.1f", percentage, minRating));
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid numbers");
        }
    }

    @FXML
    private void handleShowTopPaid() {
        try {
            int count = showIntegerInputDialog("Number of top paid employees to show:", "5");
            refreshGridTable(salaryService.getTopPaid(count));
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter a valid number");
        }
    }

    @FXML
    private void handleShowAverageDeptSalary() {
        Optional<String> result = showStringInputDialog("Enter department to get average salary: ");
        result.ifPresent(dept -> {
            if (!dept.trim().isEmpty()) {
                double avgSalary = salaryService.getAverageSalary(dept);
                showAlert("Average Salary", String.format("Average salary in %s Department: $%,.2f", dept, avgSalary));
            } else {
                showAlert("Input Error", "Please enter a department");
            }
        });
    }

    // Report Operations
    @FXML
    private void handleShowAllViaIterator() {
        List<Employee<Integer>> allEmployees = new ArrayList<>();

        Iterator<Employee<Integer>> iterator = queryService.getEmployeeIterator();
        while (iterator.hasNext()) {
            allEmployees.add(iterator.next());
        }
        refreshGridTable(allEmployees);
    }

    @FXML
    private void handleGenerateConsoleReport() {
        new EmployeeReportService<Integer>(repository).generateFullReport();
    }

    // Utility Methods
    private void refreshGridTable() {
        refreshGridTable(crudService.getAllEmployees());
    }

    private Optional<ButtonType> showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait();
    }

    private double showDoubleInputDialog(String message, String defaultValue) throws NumberFormatException {
        TextInputDialog dialog = new TextInputDialog(defaultValue);
        dialog.setHeaderText(message);
        Optional<String> result = dialog.showAndWait();
        return result.map(Double::parseDouble).orElse(0.0);
    }

    private int showIntegerInputDialog(String message, String defaultValue) throws NumberFormatException {
        TextInputDialog dialog = new TextInputDialog(defaultValue);
        dialog.setHeaderText(message);
        Optional<String> result = dialog.showAndWait();
        return result.map(Integer::parseInt).orElse(0);
    }

    private Optional<String> showStringInputDialog(String message) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(message);
        return dialog.showAndWait();
    }

    private void clearFormFields() {
        nameField.clear();
        deptField.clear();
        salaryField.clear();
    }

    private void clearRowHighlights() {
        for (Node node : gridTable.getChildren()) {
            if (node instanceof HBox && node != headerRow) {
                node.setStyle("");
            }
        }
    }

    private Node createCell(String text, Object[] columnSpec) {
        Label label = new Label(text);
        label.setMinWidth((double)columnSpec[0]);
        label.setAlignment((Pos)columnSpec[1]);
        label.setStyle("-fx-padding: 5 0;");
        return label;
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }
}