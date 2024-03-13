package org.example.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PersonActivityDTO {
    private int id;
    private String  activityName;
    private String priority;
    private String status;
    private String personName;
    private String personSurname;
}
