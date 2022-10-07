package org.example.exceptions;

public class EmployeeNotFound extends RuntimeException {
    public EmployeeNotFound(String department) {
        super("there is no employee in the given department :" + department);
    }
}
