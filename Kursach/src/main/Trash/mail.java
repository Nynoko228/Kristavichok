import javax.persistence.*;

@Entity
public class mail {
    @Id // mail_id - первичный ключ
    @GeneratedValue(strategy=GenerationType.IDENTITY) // mail_id будет автоматически генерироваться с автоинкрементом
    private long mail_id;

    @ManyToOne // Связь N к 1
    @JoinColumn(name = "student_id", nullable = false) // По столбцу student_id в таблице Mail будем связываться с таблицей Student
    private Student student;

    private String mail_name;

    public mail(){}

    public mail(Student student, String mail_name) {
        this.student = student;
        this.mail_name = mail_name;
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

    public String getMail_name() {
        return mail_name;
    }
}