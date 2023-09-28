package net.javaguides.springboot.service;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.service.impl.EmployeeServiceImpl;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    private Employee employee;

    @BeforeEach
    public void setup() {
        //employeeRepository = Mockito.mock(EmployeeRepository.class);
        //employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder().id(1L).firstName("Ramesh").lastName("Fadatare").email("ramesh@gmail.com").build();
    }

    // JUnit test for saveEmployee method
    @Test
    @Order(1)
    @DisplayName("JUnit test for saveEmployee method")
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        // given - preconditions or setup


        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());

        given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        // when - actions or the behaviour that we are going test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        System.out.println(savedEmployee);
        // then - verify the output
        assertThat(savedEmployee).isNotNull();

    }


    // JUnit test for saveEmployee method
    @Test
    @Order(2)
    @DisplayName("JUnit test for saveEmployee method WHICH THROWS Exception")
    public void givenExisitingEmail_whenSaveEmployee_thenThrowsException() {
        // given - preconditions or setup


        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        // when - actions or the behaviour that we are going test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        // then - verify the output
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    // JUnit test for getAllEmployees Method
    @Test
    @Order(3)
    @DisplayName("JUnit test for getAllEmployees Method")
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList() {
        Employee employee1 = Employee.builder().id(2L).firstName("Tony").lastName("Stark").email("tony@gmail.com").build();
        Employee employee2 = Employee.builder().id(3L).firstName("John").lastName("Cena").email("John@gmail.com").build();
        // given - preconditions or setup
        given(employeeRepository.findAll())
                .willReturn(java.util.List.of(employee, employee1, employee2));

        // when - actions or the behaviour that we are going test
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then - verify the output
        assertThat(employeeList.size()).isEqualTo(3);
    }

    @Test
    @Order(4)
    @DisplayName("JUnit test for getAllEmployees Method - negative scenario")
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList() {
        // given - preconditions or setup

        given(employeeRepository.findAll())
                .willReturn(Collections.emptyList());

        // when - actions or the behaviour that we are going test
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then - verify the output
        assertThat(employeeList.size()).isEqualTo(0);
        assertThat(employeeList).isEmpty();
    }

    // JUnit test for getEmployeeByID method
    @Test
    @Order(4)
    @DisplayName("JUnit test for getEmployeeByID method")
    public void givenEmloyeeId_whenGetEmployeeById_thenReturnEmployeeObject(){
        // given - preconditions or setup
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        // when - actions or the behaviour that we are going test
        Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // JUnit test for updateEmployee method
    @Test
    @Order(5)
    @DisplayName("JUnit test for updateEmployee method")
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
        // given - preconditions or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("ram@gmail.com");
        employee.setFirstName("Ram");

        // when - actions or the behaviour that we are going test
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        // then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("ram@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Ram");

    }

    // JUnit test for deleteEmployee Method
    @Test
    @DisplayName("JUnit test for deleteEmployee Method")
    public void givenEmployeeID_whenDeleteEmployee_thenNothing(){

        long employeeId = 1L;
        // given - preconditions or setup
        willDoNothing().given(employeeRepository).deleteById(employeeId);

        // when - actions or the  behaviour that we are going test
        employeeService.deleteEmployee(employeeId);

        // then - verify the output
        verify(employeeRepository,times(1)).deleteById(employeeId);
    }
}
