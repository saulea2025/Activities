package org.example.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PersonActivityWithRoleDTO {
    private String mode;
    private List<PersonActivityDTO> activities;
}
