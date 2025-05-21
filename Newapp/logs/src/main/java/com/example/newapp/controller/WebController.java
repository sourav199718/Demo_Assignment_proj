package com.example.newapp.controller;

import com.example.newapp.entity.Department;
import com.example.newapp.entity.Employee;
import com.example.newapp.service.DepartmentService;
import com.example.newapp.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class WebController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    // Constructor injection for services
    public WebController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping("/employees")
    public ModelAndView viewEmployeesPage() {
        List<Employee> employees = employeeService.getAllEmployees();

        if (employees.isEmpty()) {
            Employee em1 = new Employee();
            em1.setName("John");
            em1.setEmail("j@j.com");
            em1.setDepartment("hr");

            Employee em2 = new Employee();
            em2.setName("raju");
            em2.setEmail("r@ju.com");
            em2.setDepartment("HOD");

            Employee em3 = new Employee();
            em3.setName("salim");
            em3.setEmail("salim@ju.com");
            em3.setDepartment("Delivery Head of HR");

            employeeService.saveEmployee(em1);
            employeeService.saveEmployee(em2);
            employeeService.saveEmployee(em3);

            employees = employeeService.getAllEmployees();  // refresh list
        }

        ModelAndView modelAndView = new ModelAndView("employee-list");
        modelAndView.addObject("employees", employees);
        return modelAndView;
    }

    @GetMapping("/employees/new")
    public String newEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee-form";
    }

    @GetMapping("/employees/edit/{id}")
    public ModelAndView editEmployeeForm(@PathVariable Long id) {
        Employee emp = employeeService.getEmployeeById(id);
        return new ModelAndView("employee-form", "employee", emp);
    }

    @GetMapping("/departments")
    public ModelAndView listDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return new ModelAndView("department-list", "departments", departments);
    }

    @GetMapping("/departments/new")
    public String newDepartmentForm(Model model) {
        model.addAttribute("department", new Department());
        return "department-form";
    }

    @GetMapping("/departments/edit/{id}")
    public String editDepartmentForm(@PathVariable Long id, Model model) {
        model.addAttribute("department", departmentService.getDepartmentById(id));
        return "department-form";
    }
}
