package org.example.models;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleForPDF {
    private Timestamp timeOfStart;
    private String personName; // Renamed from activityName
    private String task; // Added task field
    private String priority;
    private String status;

    @Override
    public String toString() {
        return "Time of Start: " + timeOfStart +
                ", Person's Name: " + personName + '\'' +
                ", Task: " + task + '\'' +
                ", Priority: " + priority + '\'' +
                ", Status: " + status;
    }
}
