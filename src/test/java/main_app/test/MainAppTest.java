package main_app.test;

import org.example.MainApp;
import org.example.exceptions.EmployeeNotFound;
import org.example.model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(Lifecycle.PER_CLASS)
public class MainAppTest {

    private final List<Employee> employees = getList();
    private TestInfo testInfo;
    private TestReporter testReporter;

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
    public void createNewInstance(TestInfo testInfo, TestReporter testReporter) {
        this.testInfo = testInfo;
        this.testReporter = testReporter;
        app = new MainApp();
    }

    @AfterEach
    public void separateMethods() {
        System.out.println("------------------------------------------");
    }

    /**
     * It counts the number of male and female employees in a list of employees
     */
    @Test
    @DisplayName("count number of male and female employees")
    public void countMaleAndFemaleTest() {
        Map<String, Long> countMaleAndFemale = app.countMaleAndFemale(employees);
        Map<String, Long> expected = Map.of("male", 3L, "female", 2L);
        assertEquals(expected, countMaleAndFemale, "count should be male :3, female :2");
        testReporter.publishEntry(testInfo.getDisplayName() + " ran successfully");
    }

    /**
     * It tests that the set of departments returned by the allDepartments function
     * contains the
     * expected set of departments
     */
    @Test
    @DisplayName("display all the departments present in the list")
    public void allDepartmentsTest() {

        List<String> departments = List.of("development", "design", "sales", "marketing");

        assertEquals(departments, app.allDepartments(employees),
                "it should contains 'development', 'design', 'sales', 'marketing' ");
        testReporter.publishEntry(testInfo.getDisplayName() + " ran successfully");

    }

    /**
     * It tests that the average age of male employees is 22 and female employees is
     * 24
     */
    @Test
    @DisplayName("calculate average age of male and female employees")
    public void averageAgeOfEmployeesTest() {
        Map<String, Double> averageAge = Map.of("male", 22.33, "female", 24.00);

        assertEquals(averageAge, app.averageAgeOfEmployees(employees), "average age of male :22.33, female :24.00");
        testReporter.publishEntry(testInfo.getDisplayName() + " ran successfully");

    }

    /**
     * This function is used to get the employee with the highest salary
     */
    @Test
    @DisplayName("get maximum salaried employee")
    public void getMaxPayedEmployeeTest() {
        Employee expected = new Employee(1, "rakshith", 23, "male", "development", 2022, 3500000L);
        assertEquals(expected.getName(), app.getMaxPayedEmployee(employees).getName(), "maxPayed employee :rakshith");
        testReporter.publishEntry(testInfo.getDisplayName() + " ran successfully");

    }

    /**
     * It asserts that the result of the function `getEmployeesJoinedAfter2015` is
     * the same as the list
     * passed to it
     */
    @Test
    @DisplayName("get all the employees joined after 2015")
    public void getEmployeesJoinedAfter2015Test() {
        assertEquals(employees, app.getEmployeesJoinedAfter2015(employees), "result should be same list passed");
        testReporter.publishEntry(testInfo.getDisplayName() + " ran successfully");

    }

    /**
     * This function counts the number of employees in each department and returns a
     * map of department
     * names and the number of employees in each department
     */
    @Test
    @DisplayName("count number of employees present in each department")
    public void countNumberOfEmployeesInEachDepartmentTest() {

        var expected = Map.of("development", 2L, "design", 1L, "marketing", 1L, "sales", 1L);

        assertEquals(expected, app.countNumberOfEmployeesInEachDepartment(employees),
                "development :2, design :1, marketing :1, sales :1");
        testReporter.publishEntry(testInfo.getDisplayName() + " ran successfully");

    }

    /**
     * It takes a list of employees and returns a map of department names to average
     * salaries
     */
    @Test
    @DisplayName("calculate average salary of each department")
    public void averageSalaryOfEachDepartmentTest() {
        var expected = Map.of("development", 1925000.00, "design", 34000.00, "marketing", 300000.00, "sales", 130000.00);
        assertEquals(expected, app.averageSalaryOfEachDepartment(employees), "should calculate accurate average");
        testReporter.publishEntry(testInfo.getDisplayName() + " ran successfully");

    }

    /**
     * It tests that the getYoungestMaleEmployee function throws an EmployeeNotFound
     * exception when the
     * list of employees is empty
     */
    @Test
    @DisplayName("get youngest male employee ")
    public void getYoungestMaleEmployeeTest() {
        assertThrows(EmployeeNotFound.class, () -> app.getYoungestMaleEmployee(employees),
                "expected to throw EmployeeNotFound exception");
        testReporter.publishEntry(testInfo.getDisplayName() + " ran successfully");

    }

    /**
     * This function is used to get the employee with maximum experience
     */
    @Test
    @DisplayName("get highest experienced employee")
    public void getMaxExperiencedEmployeeTest() {
        Employee expected = new Employee(1, "rakshith", 23, "male", "development", 2016, 3500000L);
        assertSame(expected.getName(), app.getMaxExperiencedEmployee(employees).getName(),
                "max experienced employee is rakshith");
        testReporter.publishEntry(testInfo.getDisplayName() + " ran successfully");

    }

    /**
     * It returns a map of department names to a map of gender to the number of
     * employees in that
     * department with that gender
     */
    @Test
    @DisplayName("count male and female employees present in sales and marketing department")
    public void employeesInSaleAndMarketingTest() {
        Map<String, Map<String, Long>> expected = new HashMap<>();
        Map<String, Long> sales = new HashMap<>();
        sales.put("male", 1L);
        Map<String, Long> marketing = new HashMap<>();
        marketing.put("male", 1L);
        expected.put("sales", sales);
        expected.put("marketing", marketing);

        assertEquals(expected, app.employeesInSaleAndMarketing(employees), "in each department male=1");
        testReporter.publishEntry(testInfo.getDisplayName() + " ran successfully");

    }

    /**
     * This function takes a list of employees and returns a map of the average
     * salary for each gender
     */
    @Test
    @DisplayName("count average salary of male and female employees")
    public void avgMaleFemaleSalaryTest() {

        var expected = Map.of("male", 1310000.00, "female", 192000.00);

        assertEquals(expected, app.avgMaleFemaleSalary(employees), "avg salary male = 1310000.00, female = 192000.00");
        testReporter.publishEntry(testInfo.getDisplayName() + " ran successfully");

    }

    /**
     * Given a list of employees, return a map of department to list of employees in
     * that department
     */
    @Test
    @DisplayName("separate employees present in each department")
    public void employeesInEachDepartmentTest() {
        var expected = Map.of(
                "development", List.of("rakshith", "ranjini"),
                "design", List.of("ramya"),
                "sales", List.of("kichha"),
                "marketing", List.of("Dilip")
        );

        assertEquals(expected, app.employeesInEachDepartment(employees),
                "sales= Dilip, development= rakshith, ranjini, design= ramya, sales= kichha");
        testReporter.publishEntry(testInfo.getDisplayName() + " ran successfully");

    }

    /**
     * This function takes a list of employees and returns a map with the total and
     * average salary of
     * the employees
     */
    @Test
    @DisplayName("get average and total salary of employees")
    public void avgAndTotalSalary() {

        var expected = Map.of("total", 4314000L, "average", 4314000L / 5);

        assertEquals(expected, app.avgAndTotalSalary(employees), "avg= " + 4314000 / 5 + "total= 4314000");
        testReporter.publishEntry(testInfo.getDisplayName() + " ran successfully");

    }

    /**
     * It takes a list of employees and returns a map with two keys, one for
     * employees under 25 and one
     * for employees above 25
     */
    @Test
    @DisplayName("separate employees based on age")
    public void separateEmployeesTest() {
        Map<String, List<String>> expected = new HashMap<>();
        expected.put("under25", List.of("rakshith", "ramya", "ranjini", "kichha", "Dilip"));
        expected.put("above25", new ArrayList<>());
        assertEquals(expected, app.separateEmployees(employees), "same list has to be returned");
        testReporter.publishEntry(testInfo.getDisplayName() + " ran successfully");

    }

    /**
     * This function is used to find the oldest employee in the list of employees
     */
    @Test
    @DisplayName("get the oldest employee")
    public void oldestEmployee() {
        assertEquals("ramya", app.oldestEmployee(employees).getName(), "ramya is a oldest one");
        testReporter.publishEntry(testInfo.getDisplayName() + " ran successfully");

    }

}
