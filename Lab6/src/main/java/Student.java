import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long student_id;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<mail> mails = new ArrayList<mail>();

    private String student_name;

    public Student() {}

    public Student(String student_name) {
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

    public void addMail(mail m){
        mails.add(m);
    }

}
