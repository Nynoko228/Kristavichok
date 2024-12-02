import javax.persistence.*;

@Entity
public class mail {
    @Id
    private long mail_id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    private String mail_name;

    public mail(long mail_id, Student student) {
        this.mail_id = mail_id;
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public long getMail_id() {
        return mail_id;
    }

    public void setMail_name(String mail_name) {
        this.mail_name = mail_name;
    }
}
