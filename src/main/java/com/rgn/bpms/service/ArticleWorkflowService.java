package com.rgn.bpms.service;

import com.rgn.bpms.domain.Request;
import com.rgn.bpms.domain.RequestType;
import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArticleWorkflowService {
   @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    RepositoryService repositoryService;

    @Transactional
    public void startProcess(Request request) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("creator", request.getCreator());

        System.out.println(runtimeService.startProcessInstanceByKey("BankingProcess", variables));
    }
    @Transactional
    public List<Request> getTasks(String assignee) {
        List<Task> tasks = taskService.createTaskQuery()
                .taskCandidateGroup(assignee)
                .list();
        return tasks.stream()
                .map(task -> {
                    Map<String, Object> variables = taskService.getVariables(task.getId());
                    return new Request(task.getId(), (String) variables.get("name"), (String) variables.get("creator"));
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void submitTask(RequestType type) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("approved", type.isStatus());
        taskService.complete(type.getId(), variables);
    }

    public String deployProcessDefinition(MultipartFile model) throws IOException {

        String result="the process list is \n";
        Deployment deployment =
                repositoryService
                        .createDeployment()
                        .addInputStream(model.getName() ,  model.getInputStream())
                        .deploy();
        List<ProcessDefinition> list=repositoryService.createProcessDefinitionQuery().list();
        for(ProcessDefinition definition:list)
            result=result.concat(definition.getName()).concat("  uploading from console");
     return  result;

     }
   }



