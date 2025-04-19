module com.example.employee_management_system {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.employee_management_system to javafx.fxml;
    exports com.example.employee_management_system;
}