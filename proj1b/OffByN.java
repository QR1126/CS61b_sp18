public class OffByN implements CharacterComparator{
    private int n;

    public OffByN(int n) {
        this.n = n;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int res = Math.abs(x - y);
        return res == n ? true : false;
    }
}
