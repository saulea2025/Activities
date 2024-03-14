package org.example.models;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ScheduleForPDF {
    private Timestamp timeOfStart;
    private String activityName;
    private String activityPriority;
    private String activityStatus;

    @Override
    public String toString() {
        return "time of start: " + timeOfStart +
                ", name: " + activityName + '\'' +
                ", priority: " + activityPriority + '\'' +
                ", status: " + activityStatus;
    }
}
