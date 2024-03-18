package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleForPDF {
    private Timestamp timeOfStart;
    private String personName;
    private String task;
    private String priority;
    private String status;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public LocalDateTime getLocalDateTime() {
        return timeOfStart.toLocalDateTime();
    }

    @Override
    public String toString() {
        return "Time of Start: " + timeOfStart.toLocalDateTime().format(formatter) +
                ", Person's Name: " + personName +
                ", Task: " + task +
                ", Priority: " + priority +
                ", Status: " + status;
    }
}
