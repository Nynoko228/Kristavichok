import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class Bean {
    private Map<String, Integer> requestCounts = new HashMap<>();

    public synchronized void Increment(String requestURI) {
        requestCounts.put(requestURI, requestCounts.getOrDefault(requestURI, 0) + 1);
    }

    public Map<String, Integer> GetCnt() {
        return requestCounts;
    }

}
