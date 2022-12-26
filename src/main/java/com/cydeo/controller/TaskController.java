package com.cydeo.controller;

import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getTasks(){
       return ResponseEntity.ok(new ResponseWrapper("Successfully retrieved tasks",taskService.listAllTasks(), HttpStatus.OK));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getTaskById(@PathVariable("id") Long id){
        return ResponseEntity.ok(new ResponseWrapper("Successfully retrieved tasks",taskService.findById(id), HttpStatus.OK));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper> createTask(@RequestBody TaskDTO taskDTO){
        taskService.save(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Task is created",HttpStatus.CREATED));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseWrapper> updateTask(@RequestBody TaskDTO taskDTO){
        taskService.update(taskDTO);
        return ResponseEntity.ok(new ResponseWrapper("Task is created",HttpStatus.OK));

    }

    @GetMapping("/pending-tasks")
    public ResponseEntity<ResponseWrapper> getPendingTasks(){
        return ResponseEntity.ok(new ResponseWrapper("Successfully retrieved pending Tasks",taskService.listAllTasksByStatusIsNot(Status.COMPLETE),HttpStatus.ACCEPTED));
    }

    @GetMapping("/completedTasks")
    public ResponseEntity<ResponseWrapper> getCompletedTasks(){
        return ResponseEntity.ok(new ResponseWrapper("Retrieved Completed Tasks", taskService.listAllTasksByStatus(Status.COMPLETE),HttpStatus.OK));
    }

    @PutMapping("/complete")
    public ResponseEntity<ResponseWrapper> completeTask(@RequestBody TaskDTO task){
        taskService.updateStatus(task);
        return ResponseEntity.ok(new ResponseWrapper("Successfully updated Task's status",HttpStatus.ACCEPTED));

    }


    @DeleteMapping("/{taskId}")
    public ResponseEntity<ResponseWrapper> deleteTask(@PathVariable("taskId")Long id){
        taskService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseWrapper());
    }

}
