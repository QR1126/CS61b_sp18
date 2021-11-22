import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testOffByOne() {
        System.out.println(offByOne.equalChars('a', 'b'));
        System.out.println(offByOne.equalChars('B', 'a'));
        System.out.println(offByOne.equalChars('a', 'a'));
        System.out.println(offByOne.equalChars('&', '%'));
    }

    // Your tests go here.
    //Uncomment this class once you've created your CharacterComparator interface and OffByOne class.
}
