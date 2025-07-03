package com.ems.model;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Employee> employees;
}
