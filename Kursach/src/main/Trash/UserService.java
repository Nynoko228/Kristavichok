import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class UserService {

    @PersistenceContext
    private EntityManager entityManager;

    // Метод для хеширования пароля
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(password.getBytes());
        return bytesToHex(encodedhash);
    }

    // Метод для конвертации байтов в строку
    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // Регистрация нового пользователя
    @Transactional
    public void registerUser(String username, String password, String role) throws NoSuchAlgorithmException {
        String hashedPassword = hashPassword(password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setRole(role);

        entityManager.persist(user);
    }

    // Вход пользователя
    public boolean loginUser(String username, String password) throws NoSuchAlgorithmException {
        String hashedPassword = hashPassword(password);

        Optional<User> user = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst();

        return user.isPresent() && user.get().getPassword().equals(hashedPassword);
    }
}
