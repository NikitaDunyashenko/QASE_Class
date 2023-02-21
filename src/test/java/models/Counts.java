package models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "set")
public class Counts {
    private int cases;
    private int suites;
    private int milestones;
    private Runs runs;
    private Defects defects;

    @Data
    @Builder(setterPrefix = "set")
    public static class Runs {
        private int total;
        private int active;

    }

    @Data
    @Builder(setterPrefix = "set")
    public static class Defects {
        private int total;
        private int open;
    }
}
