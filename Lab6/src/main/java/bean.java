import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless

@TransactionManagement(TransactionManagementType.CONTAINER)

public class TestServiceBean {

    @PersistenceContext(name = "Students")
    private EntityManager entityManager;

    public void test() {

        TestEntity entity = new TestEntity();

        entity.setDescription("test");

        entityManager.persist(entity);
    }
}