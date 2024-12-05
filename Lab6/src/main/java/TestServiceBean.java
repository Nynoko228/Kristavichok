import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TestServiceBean {

    @PersistenceContext(name = "Students") // Подключаемся к DB
    private EntityManager entityManager;

    public void addStudent(String name, String m) {
        Student student = new Student(name);
        mail mail_name = new mail(student, m);
        student.addMail(mail_name);
        entityManager.persist(student);
        entityManager.persist(mail_name);
    }

    public void delStudent(long student_id){
        Student student = entityManager.find(Student.class, student_id);
        entityManager.remove(student);
    }

    public List<Student> getAllStudents() {
        Query nativeQuery = entityManager.createNativeQuery(
                "SELECT * FROM student", Student.class);
//        nativeQuery.setParameter("id", id);
        return nativeQuery.getResultList();
    }

}