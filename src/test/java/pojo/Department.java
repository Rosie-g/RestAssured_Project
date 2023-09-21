package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
@Getter @Setter
@ToString

public class Department {

    @JsonProperty("department_id")
    private int departmentId;

    @JsonProperty("department_name")
    private String name;
    private int manager_id;
    private  int location_id;

}
