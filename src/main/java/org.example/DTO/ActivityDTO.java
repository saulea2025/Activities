package org.example.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ActivityDTO {
    private int id;
    private String name;
    private String priority;
    private String status;
}
