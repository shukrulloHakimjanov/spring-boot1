package com.clean.code.springBoot2.servece;

import com.clean.code.springBoot2.domMain.Employee;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.clean.code.springBoot2.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
    public Employee findById(Long id) {
        return employeeRepository.findById(id).get();
    }
    public List<Employee> findByName(String name) {
        return employeeRepository.findByNameQuery(name);
    }
    public List<Employee> findAllByParam(String name) {
        return employeeRepository.findAllByLike(name);
    }

    public void delete(Long id){
        Employee employee=employeeRepository.getOne(id);
        employeeRepository.delete(employee);
    }
    @Scheduled(cron = "0 18 16 * * *")
    public Employee saveSchedudel() {
        Employee employee1= new Employee();
        employee1.setName("name");
        employee1.setLast("Last");
        return employeeRepository.save(employee1);
    }
}
