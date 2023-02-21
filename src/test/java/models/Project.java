package models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "set")
public class Project {
    private String title;
    private String code;
    private Counts counts;

}
