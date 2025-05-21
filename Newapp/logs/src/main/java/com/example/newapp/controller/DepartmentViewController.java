package com.example.newapp.controller;

import com.example.newapp.entity.Department;
import com.example.newapp.service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/departments")
public class DepartmentViewController {

    private final DepartmentService departmentService;

    public DepartmentViewController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // Display list of departments
    @GetMapping("/list")
    public String listDepartments(Model model) {
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "department-list";
    }

    // Show form for add/edit department
    @GetMapping("/form")
    public String showDepartmentForm(@RequestParam(required = false) Long id, Model model) {
        Department department = (id != null) ? departmentService.getDepartmentById(id) : new Department();
        model.addAttribute("department", department);
        return "department-form";
    }

    // Handle saving of department
    @PostMapping("/save")
    public String saveDepartment(@ModelAttribute Department department) {
        departmentService.saveDepartment(department);
        return "redirect:/departments/list";
    }

    // Handle delete
    @GetMapping("/delete")
    public String deleteDepartment(@RequestParam Long id) {
        departmentService.deleteDepartment(id);
        return "redirect:/departments/list";
    }
}
