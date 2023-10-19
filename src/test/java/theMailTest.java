import com.aliyun.sample.Sample;
import org.junit.jupiter.api.Test;

public class theMailTest {
    @Test
    public static void Test1(String name) {
        System.out.println(name);
    }
    public static void main(String[] args) throws Exception {
        Test1("dongqing");
        Sample sample = new Sample();
        Sample.MailPush();
    }
}
