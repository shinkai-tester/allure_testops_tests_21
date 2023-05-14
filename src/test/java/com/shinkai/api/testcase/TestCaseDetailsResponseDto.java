package com.shinkai.api.testcase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestCaseDetailsResponseDto {
    private String name,
            description;
    private Integer projectId,
            id;
}

