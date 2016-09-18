import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.TypeCodec;

public class CreateKeyspace {
    //Query
    private static final String CREATE_KEYSPACE = "CREATE KEYSPACE tp WITH replication "
            + "= {'class':'SimpleStrategy', 'replication_factor':1};";

    private static final String CREATE_TABLE = "CREATE TABLE emp(emp_id int PRIMARY KEY, "
            + "emp_name text, "
            + "emp_city text, "
            + "emp_sal varint, "
            + "emp_phone varint );";
    private static final String INSERT_QUERY = "INSERT INTO emp (emp_id, emp_name, emp_city, emp_phone, emp_sal) " +
            "VALUES(1,'raj', 'Hyderabad', 2313131231, 50000);";
    private static final String INSERT_QUERY_PARTIAL = "INSERT INTO emp (emp_id, emp_name, emp_sal) " +
            "VALUES(2,'Gowtham', 3141241);";
    private static final String SELECT_QUERY = "Select * from emp";

    public static void main(String[] args) {
        Session session = setup();
        //createKeyspace(session);
//        createTable(session);
        insertIntoTable(session);
        queryTable(session);
    }

    private static void queryTable(Session session) {
        session.execute("use tp");
        ResultSet rs = session.execute(SELECT_QUERY);
        System.out.println(String.format("rows => %s", rs.all()));
    }

    private static void insertIntoTable(Session session) {
        session.execute("use tp");
        session.execute(INSERT_QUERY);
        session.execute(INSERT_QUERY_PARTIAL);
        System.out.println("row inserted");
    }

    private static void createTable(Session session) {
        session.execute("use tp");
        session.execute(CREATE_TABLE);
        System.out.println("table created");
    }

    private static void createKeyspace(Session session) {
        //Executing the query
        session.execute(CREATE_KEYSPACE);

        //using the KeySpace
        session.execute("USE tp");
        System.out.println("Keyspace created");
    }

    private static Session setup() {
        //creating Cluster object
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        //Creating Session object
        return cluster.connect();
    }
}
