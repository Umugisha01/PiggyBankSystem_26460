package com.piggybank.controller;

import com.piggybank.dto.GlobalSearchResponse;
import com.piggybank.service.GlobalSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@CrossOrigin(origins = "*")
public class SearchController {

    @Autowired
    private GlobalSearchService globalSearchService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<GlobalSearchResponse>> globalSearch(@RequestParam String query) {
        try {
            List<GlobalSearchResponse> results = globalSearchService.globalSearch(query);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}