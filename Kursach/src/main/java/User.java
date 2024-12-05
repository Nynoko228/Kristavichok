import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long user_id;

    private String user_name;
    public String password;

    @ManyToMany
    @JoinTable(name = "security_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    public List<Role> roles;

    User(String user_name) {
        this.user_name = user_name;
    }

    public long getUser_id() {
        return user_id;
    }

//    public List<mail> getMails() {
//        return mails;
//    }

    public String getUser_name() {
        return user_name;
    }

    public void setId(Long id) {
        this.user_id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Long getId() {
        return user_id;
    }

    public String getPassword() {
        return password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }




    public User() {}
}
