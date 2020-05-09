package com.crosskey.codingTest.controller;


import com.crosskey.codingTest.model.Prospect;
import com.crosskey.codingTest.service.ProspectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@RestController
public class ProspectsRestController {

    @Autowired
    private ProspectService prospectService;

    @CrossOrigin("*")
    @PostMapping(value = "/prospect", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    String newProspect(@Valid @RequestBody Prospect prospect){
        Prospect response = prospectService.processSingleProspect(prospect);
        return response.toString();
    }

    @CrossOrigin("*")
    @PostMapping("/upload")
    public List<Prospect> singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        return prospectService.processProspect(file);

    }

    @CrossOrigin("*")
    @GetMapping("/")
    public String hello() {
        return "hello";
    }


}
