package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    private int id;
    private String  name;
    private String priority;
    private String status;
    private int personId;

    public Activity(String name, String priority, String status, int personId) {
        this.name = name;
        this.priority = priority;
        this.status = status;
        this.personId = personId;
    }
}
