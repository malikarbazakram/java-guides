package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Employee;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeRepositoryTests {
    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee;

    @BeforeEach
    public void setup() {
        employee = Employee.builder().firstName("Ramesh").lastName("Kakar").email("Ramesh@kakar.com").build();
    }
    // given_when_then naming conventions

    // JUnit test for save Employee Operation
    @Order(1)
    @Test
    @DisplayName("JUnit test for save employee operation")
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        // given - preconditions or setup
//        Employee employee = Employee.builder()
//                .firstName("Ramesh")
//                .lastName("Kakar")
//                .email("Ramesh@kakar.com")
//                .build();
        // when - actions or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.save(employee);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    // JUnit test for get all employees operation
    @Order(2)
    @Test
    @DisplayName("JUnit test for get all employees operation")
    public void givenEmployeesList_whenFindAll_thenEmployeeList() {
        // given - preconditions or setup
//        Employee employee = Employee.builder()
//                .firstName("Ramesh")
//                .lastName("Kakar")
//                .email("Ramesh@kakar.com")
//                .build();
        Employee employee1 = Employee.builder().firstName("John").lastName("Smith").email("John@Cena.com").build();

        employeeRepository.save(employee);
        employeeRepository.save(employee1);


        // when - actions or the behaviour that we are going test

        List<Employee> employeeList = employeeRepository.findAll();

        // then - verify the output

        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);

    }


    // JUnit test for get employee by id operation
    @Order(3)
    @Test
    @DisplayName("JUnit test for get employee by id operation")
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {
        // given - preconditions or setup
//        Employee employee = Employee.builder()
//                .firstName("John")
//                .lastName("Smith")
//                .email("John@Cena.com")
//                .build();
        employeeRepository.save(employee);

        // when - actions or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        // then - verify the output
        assertThat(employeeDB).isNotNull();

    }

    // JUnit test for get employee by email operation
    @Order(4)
    @Test
    @DisplayName("JUnit test for get employee by email operation")
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {
        // given - preconditions or setup
//        Employee employee = Employee.builder()
//                .firstName("John")
//                .lastName("Smith")
//                .email("John@Cena.com")
//                .build();
        employeeRepository.save(employee);

        // when - actions or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

        // then - verify the output
        assertThat(employeeDB).isNotNull();

    }

    // JUnit test for update employee operation
    @Order(5)
    @Test
    @DisplayName("JUnit test for update employee operation")
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        // given - preconditions or setup
//        Employee employee = Employee.builder()
//                .firstName("John")
//                .lastName("Smith")
//                .email("John@Cena.com")
//                .build();
        employeeRepository.save(employee);

        // when - actions or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("ram@gmail.com");
        savedEmployee.setFirstName("Ram");

        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        // then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("ram@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Ram");
    }

    // JUnit test for delete employee operation
    @DisplayName("JUnit test for delete employee operation")
    @Test
    @Order(6)
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee() {
        // given - preconditions or setup
//        Employee employee = Employee.builder()
//                .firstName("John")
//                .lastName("Smith")
//                .email("John@Cena.com")
//                .build();
        employeeRepository.save(employee);

        // when - actions or the behaviour that we are going test
        employeeRepository.delete(employee);
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        // then - verify the output
        assertThat(employeeOptional).isEmpty();

    }

    // JUnit test for custom query using JPQL with index
    @Test
    @Order(7)
    @DisplayName("JUnit test for custom query using JPQL with index")
    public void givenFirstAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {

        // given - preconditions or setup
//        Employee employee = Employee.builder()
//                .firstName("Ramesh")
//                .lastName("FadaTare")
//                .email("John@Cena.com")
//                .build();
        employeeRepository.save(employee);

//        String firstName = "Ramesh";
//        String lastName = "FadaTare";

        // when - actions or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByJPQL(employee.getFirstName(), employee.getLastName());

        // then - verify the output
        assertThat(savedEmployee).isNotNull();

    }

    // JUnit test for custom query using JPQL with named params
    @Test
    @Order(8)
    @DisplayName("JUnit test for custom query using JPQL with named params")
    public void givenFirstAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject() {

        // given - preconditions or setup
//        Employee employee = Employee.builder()
//                .firstName("Ramesh")
//                .lastName("FadaTare")
//                .email("John@Cena.com")
//                .build();
        employeeRepository.save(employee);

//        String firstName = "Ramesh";
//        String lastName = "FadaTare";

        // when - actions or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(employee.getFirstName(), employee.getLastName());

        // then - verify the output
        assertThat(savedEmployee).isNotNull();

    }

    // JUnit test for custom query using SQL with index
    @Test
    @Order(9)
    @DisplayName("JUnit test for custom query using SQL with index")
    public void givenFirstAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject() {

        // given - preconditions or setup
        // given - preconditions or setup
//        Employee employee = Employee.builder()
//                .firstName("Ramesh")
//                .lastName("FadaTare")
//                .email("John@Cena.com")
//                .build();
        employeeRepository.save(employee);

        // when - actions or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(), employee.getLastName());

        // then - verify the output
        assertThat(savedEmployee).isNotNull();

        // JUnit test for custom query using SQL with named params

    }

    @Test
    @Order(10)
    @DisplayName("JUnit test for custom query using SQL with named params")
    public void givenFirstAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject() {

        // given - preconditions or setup
        // given - preconditions or setup
//        Employee employee = Employee.builder()
//                .firstName("Ramesh")
//                .lastName("FadaTare")
//                .email("John@Cena.com")
//                .build();
        employeeRepository.save(employee);

        // when - actions or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByNativeSQLNamedParams(employee.getFirstName(), employee.getLastName());

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }
}