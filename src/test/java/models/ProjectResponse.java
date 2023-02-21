package models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "set")
public class ProjectResponse {

    private boolean status;
    private Project result;
}
