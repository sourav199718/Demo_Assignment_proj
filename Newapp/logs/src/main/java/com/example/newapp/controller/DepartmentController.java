package com.example.newapp.controller;

import com.example.newapp.entity.Department;
import com.example.newapp.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public Department save(@RequestBody Department dept) {
        return service.saveDepartment(dept);
    }

    @PutMapping("/{id}")
    public Department update(@PathVariable Long id, @RequestBody Department dept) {
        dept.setId(id);
        return service.saveDepartment(dept);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteDepartment(id);
    }

    @GetMapping
    public List<Department> getAll() {
        return service.getAllDepartments();
    }

    @GetMapping("/{id}")
    public Department getById(@PathVariable Long id) {
        return service.getDepartmentById(id);
    }
}
