package main_app.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.example.MainApp;
import org.example.exceptions.EmployeeNotFound;
import org.example.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class MainAppTest {

    private List<Employee> employees = getList();

    /**
     * It returns a list of employees
     * 
     * @return A list of employees
     */
    private List<Employee> getList() {
        return List.of(new Employee(1, "rakshith", 23, "male", "development", 2016, 3500000l),
                new Employee(2, "ramya", 25, "female", "design", 2023, 34000l),
                new Employee(3, "ranjini", 23, "female", "development", 2022, 350000l),
                new Employee(4, "kichha", 24, "male", "sales", 2022, 130000l),
                new Employee(5, "Dilip", 20, "male", "marketing", 2022, 300000l));
    }

    public MainApp app;

    /**
     * This function creates a new instance of the MainApp class before each test
     */

    @BeforeEach
    public void createNewInstance() {
        app = new MainApp();
    }

    /**
     * It counts the number of male and female employees in a list of employees
     */
    @Test
    public void countMaleAndFemaleTest() {
        Map<String, Long> countMaleAndFemale = app.countMaleAndFemale(employees);
        Map<String, Long> actualResultMap = new HashMap<>();
        actualResultMap.put("male", 3l);
        actualResultMap.put("female", 2l);
        assertEquals(actualResultMap, countMaleAndFemale, () -> "count should be male :3, female :2");
    }

    /**
     * It tests that the set of departments returned by the allDepartments function
     * contains the
     * expected set of departments
     */
    @Test
    public void allDepartmentsTest() {
        List<String> departments = new ArrayList<>();
        departments.add("development");
        departments.add("design");
        departments.add("sales");
        departments.add("marketing");

        assertEquals(departments, app.allDepartments(employees),
                () -> "it should contains 'development', 'design', 'sales', 'marketing' ");
    }

    /**
     * It tests that the average age of male employees is 22 and female employees is
     * 24
     */
    @Test
    public void averageAgeOfEmployeesTest() {
        Map<String, Integer> averageAge = new HashMap<>();
        averageAge.put("male", 22);
        averageAge.put("female", 24);
        assertEquals(averageAge, app.averageAgeOfEmployees(employees), () -> "average age of male :23, female :23");
    }

    /**
     * This function is used to get the employee with the highest salary
     */
    @Test
    public void getMaxPayedEmployeeTest() {
        Employee expected = new Employee(1, "rakshith", 23, "male", "development", 2022, 3500000l);
        assertEquals(expected.getName(), app.getMaxPayedEmployee(employees).getName(), "maxPayed employee :rakshith");
    }

    /**
     * It asserts that the result of the function `getEmployeesJoinedAfter2015` is
     * the same as the list
     * passed to it
     */

    @Test
    public void getEmployeesJoinedAfter2015Test() {
        assertEquals(employees, app.getEmployeesJoinedAfter2015(employees), "result should be same list passed");
    }

    /**
     * This function counts the number of employees in each department and returns a
     * map of department
     * names and the number of employees in each department
     */
    @Test
    public void countNumberOfEmployeesInEachDepartmentTest() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("development", 2);
        expected.put("design", 1);
        expected.put("marketing", 1);
        expected.put("sales", 1);

        assertEquals(expected, app.countNumberOfEmployeesInEachDepartment(employees),
                "development :2, design :1, marketing :1, sales :1");
    }

    /**
     * It takes a list of employees and returns a map of department names to average
     * salaries
     */
    @Test
    public void averageSalaryOfEachDepartmentTest() {
        Map<String, Long> expected = new HashMap<>();
        expected.put("development", 1925000l);
        expected.put("design", 34000l);
        expected.put("marketing", 300000l);
        expected.put("sales", 130000l);

        assertEquals(expected, app.averageSalaryOfEachDepartment(employees), "should calculate accurate average");
    }

    /**
     * It tests that the getYoungestMaleEmployee function throws an EmployeeNotFound
     * exception when the
     * list of employees is empty
     */
    @Test
    public void getYoungestMaleEmployeeTest() {
        assertThrows(EmployeeNotFound.class, () -> app.getYoungestMaleEmployee(employees),
                "expected to throw EmployeeNotFound exception");
    }

    /**
     * This function is used to get the employee with maximum experience
     */
    @Test
    public void getMaxExperiencedEmployeeTest() {
        Employee expected = new Employee(1, "rakshith", 23, "male", "development", 2016, 3500000l);
        assertSame(expected.getName(), app.getMaxExperiencedEmployee(employees).getName(),
                "max experienced employee is rakshith");
    }

    /**
     * It returns a map of department names to a map of gender to the number of
     * employees in that
     * department with that gender
     */
    @Test
    public void employeesInSaleAndMarketingTest() {
        Map<String, Map<String, Integer>> expected = new HashMap<>();
        Map<String, Integer> sales = new HashMap<>();
        sales.put("male", 1);
        Map<String, Integer> marketing = new HashMap<>();
        marketing.put("male", 1);
        expected.put("sales", sales);
        expected.put("marketing", marketing);

        assertEquals(expected, app.employeesInSaleAndMarketing(employees), "in each department male=1");
    }

    /**
     * This function takes a list of employees and returns a map of the average
     * salary for each gender
     */
    @Test
    public void avgMaleFemaleSalaryTest() {
        Map<String, Long> expected = new HashMap<>();
        expected.put("male", 1310000l);
        expected.put("female", 192000l);

        assertEquals(expected, app.avgMaleFemaleSalary(employees), "avg salary male = 1310000, female = 192000");
    }

    /**
     * Given a list of employees, return a map of department to list of employees in
     * that department
     */
    @Test
    public void employeesInEachDepartmentTest() {
        Map<String, List<String>> expected = new HashMap<>();
        expected.put("development", List.of("rakshith", "ranjini"));
        expected.put("design", List.of("ramya"));
        expected.put("sales", List.of("kichha"));
        expected.put("marketing", List.of("Dilip"));

        assertEquals(expected, app.employeesInEachDepartment(employees),
                "sales= Dilip, development= rakshith, ranjin, design= ramya, sales= kichha");
    }

    /**
     * This function takes a list of employees and returns a map with the total and
     * average salary of
     * the employees
     */
    @Test
    public void avgAndTotalSalary() {
        Map<String, Long> expected = new HashMap<>();
        expected.put("total", 4314000l);
        expected.put("average", 4314000l / 5);

        assertEquals(expected, app.avgAndTotalSalary(employees), "avg= " + 4314000 / 5 + "total= 4314000");
    }

    /**
     * It takes a list of employees and returns a map with two keys, one for
     * employees under 25 and one
     * for employees above 25
     */
    @Test
    public void separateEmployeesTest() {
        Map<String, List<Employee>> expected = new HashMap<>();
        expected.put("under25", employees);
        expected.put("above25", new ArrayList<>());
        assertEquals(expected, app.separateEmployees(employees), "same list has to be returned");
    }

    /**
     * This function is used to find the oldest employee in the list of employees
     */
    @Test
    public void oldestEmployee() {
        assertEquals("ramya", app.oldestEmployee(employees).getName(), "ramya is a oldest one");
    }

}
