package de.daroge.reactiveweb.cqs.application;

import de.daroge.reactiveweb.cqs.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends Validating<UserDto> {

    private String userId;
    @NotNull(message= "fistName is required")
    private  String firstName;
    @NotNull(message = "lastName is required")
    private String lastName;
    @NotNull(message = "email is required")
    private String email;

    public static UserDto toDto(User user){
        return new UserDto(user.getUserId().getValue(),user.getFullName().getFirstName(),user.getFullName().getLastName(),
                user.getEmail().getValue());
    }
}
