/**
 *
 */
package com.mindfire.review.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author pratyasa
 */
public class SignupDto {
    @NotNull(message = "First Name cannot be Blank")
    private String firstName;
    private String lastName;
    @NotNull(message = "User Name cannot be Blank")
    @Size(min = 3, max = 20, message = "User name should be between 3 and 20")
    private String userName;
    @NotNull(message = "Password cannot be Blank")
    @Size(min = 6, max = 50, message = "password should be between 6 and 50")
    private String password;
    @NotNull
    private String gender;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignupDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
