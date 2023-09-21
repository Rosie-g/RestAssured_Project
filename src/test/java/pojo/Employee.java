package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@ToString
// we want to ignore any json field that does not match below pojo class fields
@JsonIgnoreProperties(ignoreUnknown = true)

public class Employee {
    private int employee_id;
    private String first_name;
    private String last_name;
    private int salary;
    private int department_id;
}
