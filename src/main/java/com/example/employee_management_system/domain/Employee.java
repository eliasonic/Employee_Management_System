package com.example.employee_management_system.domain;

import java.util.Comparator;
import java.util.Objects;

/**
 * Represents an employee with generic ID support.
 * Implements Comparable to enable sorting based on years of experience.
 * Provides methods to access and modify employee attributes.
 *
 * @param <T> the type of the employee ID (e.g., Integer, String)
 */
public class Employee<T> implements Comparable<Employee<T>> {
    private T employeeId;
    private String name;
    private String department;
    private Double salary;
    private Double performanceRating;
    private Integer yearsOfExperience;
    private boolean isActive;

    public Employee(T employeeId, String name, String department, Double salary,
                    Double performanceRating, Integer yearsOfExperience, boolean isActive) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.performanceRating = performanceRating;
        this.yearsOfExperience = yearsOfExperience;
        this.isActive = isActive;
    }

    // Getters and Setters
    public T getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public Double getSalary() { return salary; }
    public Double getPerformanceRating() { return performanceRating; }
    public Integer getYearsOfExperience() { return yearsOfExperience; }
    public boolean isActive() { return isActive; }

    public void setEmployeeId(T employeeId) { this.employeeId = employeeId; }
    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public void setSalary(Double salary) { this.salary = salary; }
    public void setPerformanceRating(Double performanceRating) {this.performanceRating = performanceRating;}
    public void setYearsOfExperience(Integer yearsOfExperience) {this.yearsOfExperience = yearsOfExperience;}
    public void setActive(boolean isActive) { this.isActive = isActive; }

    /**
     * Compares employees by years of experience (descending order).
     *
     * @param other the other employee to compare to
     * @return a negative integer, zero, or a positive integer as this employee has less than, equal to, or more experience than the other
     */
    @Override
    public int compareTo(Employee<T> other) {
        return Integer.compare(
                other.yearsOfExperience != null ? other.yearsOfExperience : 0,
                this.yearsOfExperience != null ? this.yearsOfExperience : 0
        );
    }

    @Override
    public String toString() {
        return String.format("ID: %-10s Name: %-20s Dept: %-10s Salary: $%,-10.2f Rating: %-3.1f Exp: %-2d Active: %-5s",
                employeeId,
                name,
                department,
                salary != null ? salary : 0.0,
                performanceRating != null ? performanceRating : 0.0,
                yearsOfExperience != null ? yearsOfExperience : 0,
                isActive
        );
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee<?> employee = (Employee<?>) o;
        return Objects.equals(employeeId, employee.employeeId) &&
                Objects.equals(name, employee.name) &&
                Objects.equals(department, employee.department) &&
                Objects.equals(salary, employee.salary) &&
                Objects.equals(performanceRating, employee.performanceRating) &&
                Objects.equals(yearsOfExperience, employee.yearsOfExperience) &&
                Objects.equals(isActive, employee.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, name, department, salary,
                performanceRating, yearsOfExperience, isActive);
    }
}
