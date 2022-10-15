package main_app.test;

import org.example.MainApp;
import org.example.exceptions.EmployeeNotFound;
import org.example.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(Lifecycle.PER_CLASS)
public class MainAppTest {

    private final List<Employee> employees = getList();

    /**
     * It returns a list of employees
     * 
     * @return A list of employees
     */
    private List<Employee> getList() {
        return List.of(new Employee(1, "rakshith", 23, "male", "development", 2016, 3500000L),
                new Employee(2, "ramya", 25, "female", "design", 2023, 34000L),
                new Employee(3, "ranjini", 23, "female", "development", 2022, 350000L),
                new Employee(4, "kichha", 24, "male", "sales", 2022, 130000L),
                new Employee(5, "Dilip", 20, "male", "marketing", 2022, 300000L));
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
        actualResultMap.put("male", 3L);
        actualResultMap.put("female", 2L);
        assertEquals(actualResultMap, countMaleAndFemale, "count should be male :3, female :2");
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
                "it should contains 'development', 'design', 'sales', 'marketing' ");
    }

    /**
     * It tests that the average age of male employees is 22 and female employees is
     * 24
     */
    @Test
    public void averageAgeOfEmployeesTest() {
        Map<String, Double> averageAge = new HashMap<>();
        averageAge.put("male", 22.33);
        averageAge.put("female", 24.00);
        assertEquals(averageAge, app.averageAgeOfEmployees(employees), "average age of male :22.33, female :24.00");
    }

    /**
     * This function is used to get the employee with the highest salary
     */
    @Test
    public void getMaxPayedEmployeeTest() {
        Employee expected = new Employee(1, "rakshith", 23, "male", "development", 2022, 3500000L);
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
        Map<String, Long> expected = new HashMap<>();
        expected.put("development", 2L);
        expected.put("design", 1L);
        expected.put("marketing", 1L);
        expected.put("sales", 1L);

        assertEquals(expected, app.countNumberOfEmployeesInEachDepartment(employees),
                "development :2, design :1, marketing :1, sales :1");
    }

    /**
     * It takes a list of employees and returns a map of department names to average
     * salaries
     */
    @Test
    public void averageSalaryOfEachDepartmentTest() {
        Map<String, Double> expected = new HashMap<>();
        expected.put("development", 1925000.00);
        expected.put("design", 34000.00);
        expected.put("marketing", 300000.00);
        expected.put("sales", 130000.00);

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
        Employee expected = new Employee(1, "rakshith", 23, "male", "development", 2016, 3500000L);
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
        Map<String, Map<String, Long>> expected = new HashMap<>();
        Map<String, Long> sales = new HashMap<>();
        sales.put("male", 1L);
        Map<String, Long> marketing = new HashMap<>();
        marketing.put("male", 1L);
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
        Map<String, Double> expected = new HashMap<>();
        expected.put("male", 1310000.00);
        expected.put("female", 192000.00);

        assertEquals(expected, app.avgMaleFemaleSalary(employees), "avg salary male = 1310000.00, female = 192000.00");
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
                "sales= Dilip, development= rakshith, ranjini, design= ramya, sales= kichha");
    }

    /**
     * This function takes a list of employees and returns a map with the total and
     * average salary of
     * the employees
     */
    @Test
    public void avgAndTotalSalary() {
        Map<String, Long> expected = new HashMap<>();
        expected.put("total", 4314000L);
        expected.put("average", 4314000L / 5);

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
