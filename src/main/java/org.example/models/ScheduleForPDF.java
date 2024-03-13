package org.example.models;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScheduleForPDF {
    private Timestamp timeOfStart;
    private String activityName;
    private String activityPriority;
    private String activityStatus;


}
