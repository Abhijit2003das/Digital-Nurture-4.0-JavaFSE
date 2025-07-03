package com.ems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDTO {
    private String name;
    private String email;
    private String departmentName;
}
