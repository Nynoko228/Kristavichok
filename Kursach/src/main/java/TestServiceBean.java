import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class TestServiceBean {

    @PersistenceContext(name = "Students") // Подключаемся к DB
    private EntityManager entityManager;

    public void addUserWithRole(String username, String password, String roleName) {
        // Создаем нового пользователя
        User user = new User();
        user.setUser_name(username);
        user.setPassword(hashPassword(password));  // Хешируем пароль

        // Создаем роль
        Role role = new Role();
        role.setRole_name(roleName);

        // Добавляем роль в список ролей пользователя
        user.setRoles(new ArrayList<>());
        user.getRoles().add(role);

        // Сохраняем пользователя и роль в базе данных
        entityManager.persist(role);  // Сначала сохраняем роль, если она еще не существует
        entityManager.persist(user);  // Затем сохраняем пользователя
    }

    public void delUser(long student_id){
        User user = entityManager.find(User.class, student_id);
        entityManager.remove(user);
    }

    public List<User> getAllUsers() {
        Query nativeQuery = entityManager.createNativeQuery(
                "SELECT * FROM student", User.class);
//        nativeQuery.setParameter("id", id);
        return nativeQuery.getResultList();
    }

    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

}