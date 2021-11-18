import org.junit.Test;
import static org.junit.Assert.*;
/** An Integer tester created by Flik Enterprises. */
public class Flik {
    public static boolean isSameNumber(Integer a, Integer b) {
        return a.equals(b);
//        return a == b; 比较地址和值
        /** a = {Integer@604} 127
            b = {Integer@604} 127
            a = {Integer@607} 128
            b = {Integer@608} 128*/
    }

    @Test
    public void test01() {
        boolean sameNumber = isSameNumber(Integer.valueOf(256), Integer.valueOf(256));
        assertEquals(true, sameNumber);
    }
}
