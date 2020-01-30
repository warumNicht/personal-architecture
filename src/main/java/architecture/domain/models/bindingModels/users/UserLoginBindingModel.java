package architecture.domain.models.bindingModels.users;

import javax.validation.constraints.*;

public class UserLoginBindingModel {
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^(?=.*\\S).+$", message = "Username of whitespaces is not valid!")
    private String username;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 4, message = "Password require at least 4 characters!")
    @Pattern(regexp = "^(?=.*[a-z]).+$", message = "Password require at least 1 lowercase letter!")
    @Pattern(regexp = "^(?=.*[A-Z]).+$", message = "Password require at least 1 uppercase letter!")
    @Pattern(regexp = "^(?=.*\\d).+$", message = "Password require at least 1 digit!")
    private String password;

    public UserLoginBindingModel() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
