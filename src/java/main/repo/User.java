package main.repo;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.time.LocalTime;

/**
 * it s the class that is store data for any user in a database
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotEmpty(message = "Name is mandatory")

    private String userName;
    private String password;

    private String gmailAddress;

    public User() {}

    public User(String userName) {
        this.userName = userName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setGmailAddress(String gmailAddress) { this.gmailAddress = gmailAddress;  }
    public void setPassword(String password) { this.password = password;  }


    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
    public String getGmailAddress() {
        return gmailAddress;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", gmailAddress='" + gmailAddress + '\'' +
                '}';
    }
}

