public class OffByOne implements CharacterComparator{
    @Override
    public boolean equalChars(char x, char y) {
        int res = Math.abs(x - y);
        return res == 1 ? true : false;
    }
}
