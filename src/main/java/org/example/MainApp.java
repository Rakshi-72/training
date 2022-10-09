package org.example;

import org.example.exceptions.EmployeeNotFound;
import org.example.model.Employee;

import java.util.*;
import java.util.stream.Collectors;

public class MainApp {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

    }

    /**
     * Count the number of male and female employees in the list.
     *
     * @param employees The list of employees
     * @return A map with the number of male and female employees.
     */
    public Map<String, Long> countMaleAndFemale(List<Employee> employees) {
        Map<String, Long> genders = new HashMap<>();
        long male = employees.stream().filter(e -> e.getGender().equalsIgnoreCase("male")).count();
        genders.put("male", male);
        genders.put("female", employees.size() - male);
        return genders;
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
     * It takes a list of employees, and returns a map of the average age of male
     * and female employees
     *
     * @param employees a list of employees
     * @return A map with the average age of male and female employees.
     */
    public Map<String, Integer> averageAgeOfEmployees(List<Employee> employees) {
        Map<String, Integer> ages = new HashMap<>();

        Integer[] genders = {0, 0, 0};

        employees.forEach(employee -> {
            if (employee.getGender().equalsIgnoreCase("male")) {
                genders[0] += employee.getAge();
                genders[2]++;
            } else
                genders[1] += employee.getAge();
        });

        ages.put("male", genders[0] / genders[2]);
        ages.put("female", genders[1] / (employees.size() - genders[2]));

        return ages;
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
     * For each employee in the list of employees, if the department of the employee
     * is already in the map, then increment
     * the count of employees in that department by 1, else add the department to
     * the map with a count of 1
     *
     * @param employees a list of Employee objects
     * @return A map of department names and the number of employees in each
     * department.
     */
    public Map<String, Integer> countNumberOfEmployeesInEachDepartment(List<Employee> employees) {
        Map<String, Integer> departmentEmployees = new LinkedHashMap<>();
        for (Employee employee : employees) {
            if (departmentEmployees.containsKey(employee.getDepartment())) {
                departmentEmployees.put(employee.getDepartment(),
                        departmentEmployees.get(employee.getDepartment()) + 1);
            } else {
                departmentEmployees.put(employee.getDepartment(), 1);
            }
        }
        return departmentEmployees;
    }

    /**
     * We are iterating through the list of employees and adding the salary of each
     * employee to the departmentEmployees
     * map.
     * We are also using the countNumberOfEmployeesInEachDepartment function to get
     * the number of employees in each
     * department.
     * After we have the total salary of each department, we are dividing it by the
     * number of employees in that department
     * to get the average salary.
     * We are returning the departmentEmployees map which now contains the average
     * salary of each department.
     *
     * @param employees List of employees
     * @return A map of department and average salary of employees in that
     * department.
     */
    public Map<String, Long> averageSalaryOfEachDepartment(List<Employee> employees) {
        Map<String, Long> departmentEmployees = new LinkedHashMap<>();
        Map<String, Integer> noOfEmployeesInEachDepartment = this.countNumberOfEmployeesInEachDepartment(employees);

        for (Employee employee : employees) {
            if (departmentEmployees.containsKey(employee.getDepartment())) {
                departmentEmployees.put(employee.getDepartment(),
                        departmentEmployees.get(employee.getDepartment()) + employee.getSalary());
            } else {
                departmentEmployees.put(employee.getDepartment(), employee.getSalary());
            }
        }

        departmentEmployees.replaceAll(
                (department, salary) -> salary / noOfEmployeesInEachDepartment.get(department));
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
                .filter(employee -> employee.getDepartment().equalsIgnoreCase("productDevelopment") && employee.getGender().equalsIgnoreCase("male"))
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
     * It takes a list of employees and returns a map of departments and a map of
     * genders and the number of employees of
     * that gender in that department
     *
     * @param employees List of employees
     * @return A map of maps. The outer map has two keys, "sales" and "marketing".
     * The inner maps have two keys, "male" and
     * "female". The values of the inner maps are the number of employees in
     * the department with the corresponding gender.
     */
    public Map<String, Map<String, Integer>> employeesInSaleAndMarketing(List<Employee> employees) {
        Map<String, Map<String, Integer>> maleAndFemales = new LinkedHashMap<>();
        Map<String, Integer> sales = new LinkedHashMap<>();
        Map<String, Integer> marketing = new LinkedHashMap<>();
        employees.forEach(employee -> {
            if (employee.getDepartment().equalsIgnoreCase("sales")) {
                if (employee.getGender().equalsIgnoreCase("male")) {
                    if (sales.containsKey("male"))
                        sales.put("male", sales.get("male") + 1);
                    else
                        sales.put("male", 1);
                } else {
                    if (sales.containsKey("female"))
                        sales.put("female", sales.get("female") + 1);
                    else
                        sales.put("female", 1);
                }
            } else if (employee.getDepartment().equalsIgnoreCase("marketing")) {
                if (employee.getGender().equalsIgnoreCase("male")) {
                    if (marketing.containsKey("male"))
                        marketing.put("male", marketing.get("male") + 1);
                    else
                        marketing.put("male", 1);
                } else {
                    if (marketing.containsKey("female"))
                        marketing.put("female", marketing.get("female") + 1);
                    else
                        marketing.put("female", 1);
                }
            }
        });

        maleAndFemales.put("sales", sales);
        maleAndFemales.put("marketing", marketing);
        return maleAndFemales;
    }

    /**
     * It takes a list of employees and returns a map of average salaries for male
     * and female employees
     *
     * @param employees a list of employees
     * @return A map with the average salary of male and female employees.
     */
    public Map<String, Long> avgMaleFemaleSalary(List<Employee> employees) {
        Long[] genders = {0L, 0L, 0L};
        employees.forEach(employee -> {
            if (employee.getGender().equalsIgnoreCase("male")) {
                genders[0] += employee.getSalary();
                genders[2]++;
            } else
                genders[1] += employee.getSalary();
        });
        Map<String, Long> avgSalary = new HashMap<>();
        avgSalary.put("male", genders[0] / genders[2]);
        avgSalary.put("female", genders[1] / (employees.size() - genders[2]));
        return avgSalary;
    }

    /**
     * We're iterating over the list of employees, and for each employee, we're
     * checking if the departmentEmployees map
     * already contains the employee's department. If it does, we're adding the
     * employee's name to the list of names for
     * that department. If it doesn't, we're creating a new list with the employee's
     * name and adding it to the map
     *
     * @param employees List<Employee>
     * @return A map of departments and the employees in each department.
     */
    public Map<String, List<String>> employeesInEachDepartment(List<Employee> employees) {
        Map<String, List<String>> departmentEmployees = new LinkedHashMap<>();
        employees.forEach(employee -> {
            if (departmentEmployees.containsKey(employee.getDepartment())) {
                List<String> namesList = departmentEmployees.get(employee.getDepartment());
                namesList.add(employee.getName());
                departmentEmployees.put(employee.getDepartment(), namesList);
            } else {
                List<String> name = new ArrayList<>();
                name.add(employee.getName());
                departmentEmployees.put(employee.getDepartment(), name);
            }
        });

        return departmentEmployees;
    }

    /**
     * Given a list of employees, return a map containing the total and average
     * salary of all employees.
     *
     * @param employees a list of employees
     * @return A map with two keys, "total" and "average", and the corresponding
     * values.
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

}
