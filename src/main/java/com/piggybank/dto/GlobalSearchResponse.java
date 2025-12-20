package com.piggybank.dto;

import java.util.List;

public class GlobalSearchResponse {
    private String entityType;
    private Long id;
    private String displayLabel;
    private String description;

    public GlobalSearchResponse() {}

    public GlobalSearchResponse(String entityType, Long id, String displayLabel, String description) {
        this.entityType = entityType;
        this.id = id;
        this.displayLabel = displayLabel;
        this.description = description;
    }

    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDisplayLabel() { return displayLabel; }
    public void setDisplayLabel(String displayLabel) { this.displayLabel = displayLabel; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}