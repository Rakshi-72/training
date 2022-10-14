package org.example;

import org.example.exceptions.EmployeeNotFound;
import org.example.model.Employee;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class MainApp {
    private static final Scanner sc = new Scanner(System.in);

    /**
     * It takes a list of employees and returns a map of gender to the number of
     * employees of that
     * gender
     * 
     * @param employees List of employees
     * @return A map of the genders and the number of employees of that gender.
     */
    public Map<String, Long> countMaleAndFemale(List<Employee> employees) {

        return employees.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));
    }

    /**
     * Given a list of employees, return a set of all the departments.
     *
     * @param employees a list of employees
     * @return A set of all the departments in the list of employees.
     */
    public List<String> allDepartments(List<Employee> employees) {

        return employees.stream().map(Employee::getDepartment).distinct().collect(Collectors.toList());
    }

    /**
     * It takes a list of employees and returns a map of gender to average age of
     * employees of that
     * gender
     * 
     * @param employees List of employees
     * @return A map of gender to average age of employees.
     */
    public Map<String, Double> averageAgeOfEmployees(List<Employee> employees) {

        Map<String, Double> resultMap = employees.stream()
                .collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingLong(Employee::getAge)));
        resultMap.replaceAll((gender, age) -> this.getFormattedDouble(age));
        return resultMap;
    }

    /**
     * Return the employee with the highest salary.
     *
     * @param employees a list of employees
     * @return The employee with the highest salary.
     */
    public Employee getMaxPayedEmployee(List<Employee> employees) {
        return Collections.max(employees, Comparator.comparingLong(Employee::getSalary));
    }

    /**
     * Return a list of employees who joined after 2015.
     *
     * @param employees The list of employees
     * @return A list of employees who joined after 2015.
     */
    public List<Employee> getEmployeesJoinedAfter2015(List<Employee> employees) {
        return employees.stream().filter(employee -> employee.getYearOfJoining() > 2015).collect(Collectors.toList());
    }

    /**
     * It takes a list of employees and returns a map of department to number of
     * employees in that
     * department
     * 
     * @param employees List of employees
     * @return A map of department names and the number of employees in each
     *         department.
     */
    public Map<String, Long> countNumberOfEmployeesInEachDepartment(List<Employee> employees) {
        return employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
    }

    /**
     * It takes a list of employees and returns a map of department to average
     * salary of employees in
     * that department
     * 
     * @param employees List of employees
     * @return A map of department names and the average salary of employees in that
     *         department.
     */
    public Map<String, Double> averageSalaryOfEachDepartment(List<Employee> employees) {
        Map<String, Double> departmentEmployees = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingLong(Employee::getSalary)));

        departmentEmployees.replaceAll(
                (department, salary) -> this.getFormattedDouble(salary));
        return departmentEmployees;
    }

    /**
     * Get the youngest male employee from the product development department.
     *
     * @param employees List of employees
     * @return The youngest male employee in the productDevelopment department.
     */
    public Employee getYoungestMaleEmployee(List<Employee> employees) throws EmployeeNotFound {
        return employees.stream()
                .filter(employee -> employee.getDepartment().equalsIgnoreCase("productDevelopment")
                        && employee.getGender().equalsIgnoreCase("male"))
                .min(Comparator.comparingInt(Employee::getAge))
                .orElseThrow(() -> new EmployeeNotFound("productDevelopment"));
    }

    /**
     * It returns the employee with the minimum year of joining from the list of
     * employees(that means most experienced)
     *
     * @param employees List of employees
     * @return employee with the max experience
     */
    public Employee getMaxExperiencedEmployee(List<Employee> employees) {
        return employees.stream().min(Comparator.comparingInt(Employee::getYearOfJoining)).get();
    }

    /**
     * It takes a list of employees and returns a map of departments to a map of
     * genders to the number
     * of employees of that gender in that department
     * 
     * @param employees List of employees
     * @return A map of maps.
     */
    public Map<String, Map<String, Long>> employeesInSaleAndMarketing(List<Employee> employees) {
        Map<String, Map<String, Long>> maleAndFemales = new LinkedHashMap<>();

        Map<String, Map<String, Long>> groupedEmployees = employees.stream().collect(Collectors
                .groupingBy(Employee::getDepartment,
                        Collectors.groupingBy(Employee::getGender, Collectors.counting())));

        maleAndFemales.put("sales", groupedEmployees.get("sales"));
        maleAndFemales.put("marketing", groupedEmployees.get("marketing"));
        return maleAndFemales;
    }

    /**
     * It takes a list of employees, groups them by gender, and then averages their
     * salaries
     * 
     * @param employees List of employees
     * @return A map of gender to average salary.
     */
    public Map<String, Double> avgMaleFemaleSalary(List<Employee> employees) {

        Map<String, Double> avgSalary = employees.stream()
                .collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingLong(Employee::getSalary)));

        avgSalary.replaceAll((gender, salary) -> this.getFormattedDouble(salary));
        return avgSalary;
    }

    /**
     * It takes a list of employees and returns a map of department to a list of
     * employee names in that
     * department
     * 
     * @param employees List<Employee>
     * @return A map of department to a list of employees in that department.
     */
    public Map<String, List<String>> employeesInEachDepartment(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.mapping(Employee::getName, Collectors.toList())));
    }

    /**
     * Given a list of employees, return a map containing the total and average
     * salary of all employees.
     *
     * @param employees a list of employees
     * @return A map with two keys, "total" and "average", and the corresponding
     *         values.
     */
    public Map<String, Long> avgAndTotalSalary(List<Employee> employees) {
        Long totalSalary = employees.stream().map(Employee::getSalary).reduce(Long::sum).get();
        Map<String, Long> salaries = new LinkedHashMap<>();
        salaries.put("total", totalSalary);
        salaries.put("average", totalSalary / employees.size());
        return salaries;
    }

    /**
     * It takes a list of employees, separates them into two lists based on their
     * age, and returns a
     * map of those two lists
     *
     * @param employees List of employees
     * @return A map of employees.
     */
    public Map<String, List<Employee>> separateEmployees(List<Employee> employees) {
        List<Employee> under25 = new ArrayList<>();
        List<Employee> above25 = new ArrayList<>();
        employees.forEach(employee -> {
            if (employee.getAge() < 26)
                under25.add(employee);
            else
                above25.add(employee);
        });
        Map<String, List<Employee>> mapOfEmployees = new LinkedHashMap<>();
        mapOfEmployees.put("under25", under25);
        mapOfEmployees.put("above25", above25);
        return mapOfEmployees;
    }

    /**
     * "Return the employee with the highest age from the list of employees."
     * <p>
     * The function takes a list of employees and returns the employee with the
     * highest age
     *
     * @param employees The list of employees to be sorted.
     * @return The oldest employee
     */
    public Employee oldestEmployee(List<Employee> employees) {
        return Collections.max(employees, Comparator.comparingInt(Employee::getAge));
    }

    /**
     * It takes the number of employees as input, and then takes the details of each
     * employee as input
     * and returns a list of employees
     * 
     * @return A list of employees
     */
    public List<Employee> getList() {
        int noOfEmployees = sc.nextInt();
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < noOfEmployees; i++) {
            System.out.println("enter id");
            Integer id = sc.nextInt();
            sc.nextLine();
            System.out.println("enter name");
            String name = sc.nextLine();
            System.out.println("enter age");
            Integer age = sc.nextInt();
            sc.nextLine();
            System.out.println("enter gender");
            String gender = sc.nextLine();
            System.out.println("enter department");
            String department = sc.nextLine();
            System.out.println("enter year of joining");
            Integer yearOfJoining = sc.nextInt();
            System.out.println("enter the salary");
            Long salary = sc.nextLong();
            employees.add(new Employee(id, name, age, gender, department, yearOfJoining, salary));
        }
        return employees;
    }

    public Double getFormattedDouble(Double value) {
        return Double.valueOf(new DecimalFormat("#.##").format(value));
    }

}
