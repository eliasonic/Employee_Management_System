package com.example.employee_management_system.service;

import com.example.employee_management_system.domain.Employee;
import com.example.employee_management_system.repository.EmployeeRepository;
import java.util.List;

/**
 * Service class that generates comprehensive report of employee data.
 *
 * @param <T> the type of the employee ID
 */
public class EmployeeReportService<T> {
    private static final String HEADER_FORMAT = "%-6s | %-20s | %-10s | %-12s | %-6s | %-5s | %-6s%n";
    private static final String DIVIDER = "-----------------------------------------------------------------------------------";
    private final EmployeeRepository<T> repository;

    public EmployeeReportService(EmployeeRepository<T> repository) {
        this.repository = repository;
    }

    /**
     * Generates and prints a report of employee data to the console.
     * The report includes:
     * <ul>
     *   <li>Detailed list of all employees</li>
     *   <li>Total employee count</li>
     *   <li>Active employee count and percentage</li>
     *   <li>Total salary expenditure</li>
     *   <li>Average salary</li>
     * </ul>
     */
    public void generateFullReport() {
        List<Employee<T>> employees = repository.findAll();

        System.out.println("\n" + DIVIDER);
        System.out.println("EMPLOYEE MANAGEMENT SYSTEM REPORT");
        System.out.println(DIVIDER);

        // Print employee details
        System.out.printf(HEADER_FORMAT,
                "ID", "Name", "Department", "Salary", "Rating", "Exp", "Active");
        System.out.println(DIVIDER);

        employees.forEach(emp -> System.out.printf(HEADER_FORMAT,
                emp.getEmployeeId(),
                emp.getName(),
                emp.getDepartment(),
                emp.getSalary(),
                emp.getPerformanceRating(),
                emp.getYearsOfExperience(),
                emp.isActive() ? "Yes" : "No")
        );

        // Print statistics
        System.out.println(DIVIDER);
        printStatistics(employees);
        System.out.println(DIVIDER);
    }

    private void printStatistics(List<Employee<T>> employees) {
        double totalSalary = employees.stream()
                .mapToDouble(Employee::getSalary)
                .sum();

        long activeCount = employees.stream()
                .filter(Employee::isActive)
                .count();

        System.out.printf("%n%-30s: %d", "Total Employees", employees.size());
        System.out.printf("%n%-30s: %d (%.1f%%)", "Active Employees", activeCount, (activeCount * 100.0 / employees.size()));
        System.out.printf("%n%-30s: $%,.2f", "Total Salary Expenditure", totalSalary);
        System.out.printf("%n%-30s: $%,.2f%n", "Average Salary", (totalSalary / employees.size()));
    }
}