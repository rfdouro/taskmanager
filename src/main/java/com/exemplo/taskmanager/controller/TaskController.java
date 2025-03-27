package com.exemplo.taskmanager.controller;

import com.exemplo.taskmanager.model.Task;
import com.exemplo.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
 @Autowired
 private TaskService taskService;

 @GetMapping
 public List<Task> findAll() {
  return taskService.findAll();
 }

 @PostMapping
 public Task save(@RequestBody Task task) {
  return taskService.save(task);
 }
}
