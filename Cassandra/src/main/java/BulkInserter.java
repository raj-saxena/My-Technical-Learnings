import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.sun.javafx.binding.IntegerConstant;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public class BulkInserter {

    public static void main(String[] args) {
        Session session = SessionCreator.setup();
        session.execute("use tp");
        List<String> columns = getColumns();

        final List<List> rows = getRows();
        long start = System.currentTimeMillis();

        final PreparedStatement stmt = getPreparedStatement(session);

        rows.parallelStream().forEach(value -> {
            try {
                session.execute(stmt.bind(value.toArray()));
//                session.execute(QueryBuilder.insertInto("sf_salaries_java").values
//                        (columns, value));
            } catch (Exception e) {
                System.err.println(e + value.toString());
                System.exit(1);
            }
        });

        System.out.println(String.format("Entered into %f", (System.currentTimeMillis()-start)));
    }

    private static PreparedStatement getPreparedStatement(Session session) {
        return session.prepare("insert into sf_salaries_java(Id,EmployeeName,JobTitle," +
                    "BasePay, OvertimePay,OtherPay," +
                    "Benefits, TotalPay,TotalPayBenefits,Year,Notes,Agency,Status) values(?,?,?,?,?,?," +
                    "?, ?, ?, ?, ?, ?, ?)");
    }

    private static List<List> getRows() {
        final List<List> rows = new ArrayList();

        try {
            Files.lines(Paths.get("/Users/raj/Desktop/data/sf-salaries/Salaries_java.csv")).forEach(line -> {
                final List<String> data = Arrays.asList(line.split(","));
                rows.add(curate(data));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rows;
    }

    private static List curate(List<String> data) {
        List<Object> values = new ArrayList<>(13);
        for (int i = 0; i < data.size(); i++) {
            Object item = data.get(i);
            if("".equals(item)) item = null;
            else if(i == 0 || i ==9) item = Integer.parseInt(data.get(i));
            else if(i >= 3 && i <= 8) item = Double.parseDouble(data.get(i));
            values.add(i, item);
        } if(values.size() == 12) values.add(null);
        return values;
    }

    private static List<String> getColumns() {
        return asList("Id","EmployeeName","JobTitle","BasePay","OvertimePay","OtherPay","Benefits","TotalPay",
                "TotalPayBenefits","Year","Notes","Agency","Status");
    }
}
