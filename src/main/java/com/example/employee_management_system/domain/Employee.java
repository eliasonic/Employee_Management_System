package com.example.employee_management_system.domain;

import java.util.Comparator;

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
    private double salary;
    private double performanceRating;
    private int yearsOfExperience;
    private boolean isActive;

    public Employee(T employeeId, String name, String department, double salary,
                    double performanceRating, int yearsOfExperience, boolean isActive) {
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
    public double getSalary() { return salary; }
    public double getPerformanceRating() { return performanceRating; }
    public int getYearsOfExperience() { return yearsOfExperience; }
    public boolean isActive() { return isActive; }

    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public void setSalary(double salary) { this.salary = salary; }
    public void setPerformanceRating(double performanceRating) {this.performanceRating = performanceRating;}
    public void setYearsOfExperience(int yearsOfExperience) {this.yearsOfExperience = yearsOfExperience;}
    public void setActive(boolean isActive) { this.isActive = isActive; }

    /**
     * Compares employees by years of experience (descending order).
     *
     * @param other the other employee to compare to
     * @return a negative integer, zero, or a positive integer as this employee has less than, equal to, or more experience than the other
     */
    @Override
    public int compareTo(Employee<T> other) {
        return Integer.compare(other.yearsOfExperience, this.yearsOfExperience);
    }

    @Override
    public String toString() {
        return String.format("ID: %-10s Name: %-20s Dept: %-10s Salary: $%,-10.2f Rating: %-3.1f Exp: %-2d Active: %-5s",
                employeeId, name, department, salary, performanceRating, yearsOfExperience, isActive);
    }
}
