/**
 * @author QR1126
 */
public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque<>();
        int n = word.length();
        for(int i = 0; i < n; i++) {
            char c = word.charAt(i);
            deque.addLast(c);
        }
        return deque;
    }

    /**The isPalindrome method should return true if the given word is
     * a palindrome,and false otherwise. A palindrome is defined
     * as a word that is the same whether it is read forwards or backwards.
     * For example “a”, “racecar”, and “noon” are all palindromes. “horse”,
     * “rancor”, and “aaaaab” are not palindromes.
     * Any word of length 1 or 0 is a palindrome.*/
    public boolean isPalindrome(String word) {
        if (word.length() == 1 || word.length() == 0) {
            return true;
        }
        Deque<Character> deque = wordToDeque(word);
        for (int i = 0, j = deque.size() - 1; i < j; i++, j--) {
            if (!deque.get(i).equals(deque.get(j))) {
                return false;
            }
        }
        return true;
    }


    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() == 1 || word.length() == 0) {
            return true;
        }
        OffByOne off = new OffByOne();
        Deque<Character> deque = wordToDeque(word);
        int n = deque.size();
        for (int i = 0, j = n - 1; i < j; i++, j--) {
            if (!off.equalChars(deque.get(i), deque.get(j))) {
                return false;
            }
        }
        return true;
    }
}
