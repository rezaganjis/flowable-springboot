package com.rgn.bpms.controller;

import com.rgn.bpms.domain.Request;import com.rgn.bpms.domain.RequestType;
import com.rgn.bpms.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
public class WorkflowController {
    @Autowired
    private WorkflowService service;
    @GetMapping("/startProcess")
    public void startProcess(Request request) {
        System.out.println(request);
        service.startProcess(request);
    }

    @GetMapping("/tasks")
    public List<Request> getTasks(@RequestParam String assignee) {

        return service.getTasks(assignee);
    }

    @PostMapping("/submitTask")
    public void doTask(@RequestBody RequestType RequestType) {
        System.out.println(RequestType);
        service.submitTask(RequestType);
    }


    @PostMapping(value="/deploy")
    public String deploy(MultipartFile model) throws IOException {
         System.out.println(model);
        return service.deployProcessDefinition(model);

    }

}
