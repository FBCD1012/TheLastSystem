
import org.junit.jupiter.api.Test;

import java.util.Random;

public class theMailTest {
    @Test
    public static void Test1(String name) {
        System.out.println(name);
    }
    public static void main(String[] args) throws Exception {
        Random random=new Random();
        String randomString= String.valueOf(random.nextInt(9999));
        System.out.println(randomString);
    }
}
