package com.camunda.consulting.demo.facade;

import com.camunda.consulting.demo.service.StateService;
import io.camunda.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/process")
public class ProcessController {


    @Autowired
    private ZeebeClient zeebe;

    @Autowired
    private StateService stateService;


    @PostMapping("/start")
    public void startProcessInstance(@RequestBody Map<String, Object> variables) {
        zeebe.newCreateInstanceCommand().bpmnProcessId("request-state-process").latestVersion().variables(variables).send();
    }

    @PostMapping("/publish/{myId}/{message}")
    public CompletableFuture<?> publishMessage(@PathVariable String myId, @PathVariable String message, @RequestBody Map<String,
            Object> variables) {
        return stateService.publishMessage(myId, message, variables);
    }

}
