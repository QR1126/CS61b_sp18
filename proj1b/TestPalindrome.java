import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    } //Uncomment this class once you've created your Palindrome class.

    @Test
    public void testisPalindrome() {
        assertFalse(palindrome.isPalindrome("cat"));
        assertTrue(palindrome.isPalindrome("cac"));
        assertFalse(palindrome.isPalindrome("ca"));
        assertFalse(palindrome.isPalindrome("Acca"));
    }

    @Test
    public void testisPalindrome1() {
        assertTrue(palindrome.isPalindrome("flake", new CharacterComparator() {
            @Override
            public boolean equalChars(char x, char y) {
                int res = Math.abs(x - y);
                return res == 1 ? true : false;
            }
        }));
    }
}
