package org.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonActivityDTO {
    private String  activityName;
    private String priority;
    private String status;
    private String personName;
    private String personSurname;
}
