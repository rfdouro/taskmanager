package com.exemplo.taskmanager.controller;

import com.exemplo.taskmanager.model.Responsible;
import com.exemplo.taskmanager.service.ResponsibleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/responsibles")
public class ResponsibleController {
    @Autowired
    private ResponsibleService responsibleService;

    @GetMapping
    public List<Responsible> findAll() {
        return responsibleService.findAll();
    }

    @PostMapping
    public Responsible save(@RequestBody Responsible responsible) {
        return responsibleService.save(responsible);
    }
}
