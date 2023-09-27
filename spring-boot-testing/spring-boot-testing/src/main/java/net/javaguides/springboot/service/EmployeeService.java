package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Employee;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {

    Employee saveEmployee(Employee employee);

}
