import java.sql.Timestamp;
import java.util.Date;

/**
 * @program: TextServer
 * @description:
 * @author: ma
 * @create: 2023-02-24 09:25
 */
public class Test {
    public static void main(String[] args) {
        java.util.Date date = new Date();
        Timestamp t = new Timestamp(date.getTime());

        System.out.println(t);
    }
}
