import javax.persistence.*;
import java.util.List;

@Entity
public class Student {
    @Id
    private long student_id;

    @OneToMany(mappedBy = "student")
    private List<mail> mails;

    private String student_name;

    public Student(long student_id, String student_name, List<mail> mails) {
        this.student_id = student_id;
        this.student_name = student_name;
    }

    public long getStudent_id() {
        return student_id;
    }

    public List<mail> getMails() {
        return mails;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setMails(List<mail> mails) {
        this.mails = mails;
    }

}
