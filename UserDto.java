package blogapp_api.payload;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;

    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 4, message = "Username must be at least 4 characters")
    private String name;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 3, max = 10, message = "Password should be between 3 and 10 characters")
    private String password;

    @NotEmpty(message = "About cannot be empty")
    private String about;
    @JsonIgnore
    public String getPassword() {
    	return this.password;
    }
  @JsonProperty
    public  void setPassword(String password) {
    	this.password=password;
    }
    
}

