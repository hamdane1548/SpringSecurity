package net.oussama.miniprojectsecurity.Dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Login {
    private String email;
    private String password;
}
