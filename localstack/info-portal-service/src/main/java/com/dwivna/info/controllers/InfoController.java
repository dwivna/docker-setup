package com.dwivna.info.controllers;

import com.dwivna.info.exceptions.NoRecordFoundException;
import com.dwivna.info.models.Info;
import com.dwivna.info.services.InfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/info")
public class InfoController {

    private final InfoService infoService;

    @Autowired
    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @Operation(summary = "Save a new information", description = "Create a new item in the database")
    @ApiResponse(responseCode = "200", description = "Information saved successfully")
    @PostMapping
    public Info saveItem(@RequestBody Info info) {
        return infoService.saveInfo(info);
    }

    @Operation(summary = "Get an information by ID and Service", description = "Fetch an item by its ID and Service")
    @ApiResponse(responseCode = "200", description = "Information retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Information not found")
    @GetMapping("/{id}/{service}")
    public Info getInfoByIdAndService(@PathVariable String id, @PathVariable String service) {
        Info info = infoService.getInfoByIdAndService(id,service);
        if (info == null) {
            throw new NoRecordFoundException(String.format("No information for provided id: %s, service: %s", id, service));
        }
        return info;
    }
}
