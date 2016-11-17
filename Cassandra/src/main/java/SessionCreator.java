import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class SessionCreator {
    public static Session setup() {
        //creating Cluster object
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        //Creating Session object
        return cluster.connect();
    }
}
