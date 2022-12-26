package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;
    private final UserMapper userMapper;

    public ProjectController(ProjectService projectService, UserMapper userMapper) {
        this.projectService = projectService;
        this.userMapper = userMapper;
    }

    @GetMapping()
    public ResponseEntity<ResponseWrapper> getProjects(){
        return ResponseEntity.ok(new ResponseWrapper("Retrieved Projects", projectService.listAllProjects(),HttpStatus.OK));

    }

    @GetMapping("/{projectCode}")
    public ResponseEntity<ResponseWrapper> getProjectsByProjectCode(@PathVariable("projectCode")String code){

        return ResponseEntity.ok(new ResponseWrapper("Retrieved Project by ProjectCode", projectService.getByProjectCode(code),HttpStatus.OK));

    }

    @GetMapping("/{manager}")
    public ResponseEntity<ResponseWrapper> getProjectsByManager(@PathVariable("manager")UserDTO userDTO){
       return ResponseEntity.ok().body(new ResponseWrapper("Retrieved Project by Manager", projectService.listAllProjectDetails(),HttpStatus.ACCEPTED));

    }

    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody ProjectDTO project){
        projectService.save(project);
        return  ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Project is created",HttpStatus.CREATED));
    }

    @PutMapping("/update/{projectCode}")
    public ResponseEntity<ResponseWrapper> updateProject(@RequestBody ProjectDTO project){
        projectService.update(project);
        return ResponseEntity.ok().body(new ResponseWrapper("Project is updated",HttpStatus.ACCEPTED));
    }


    @DeleteMapping("/delete/{projectCode}")
    public ResponseEntity<ResponseWrapper> deleteProject(@PathVariable("projectCode") String id){
        projectService.getByProjectCode(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseWrapper());
    }

    @PutMapping("/{projectCode}")
    public ResponseEntity<ResponseWrapper> completeProject(@PathVariable("projectCode") String projectCode){
        projectService.complete(projectCode);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseWrapper());
    }
}
