package hw2;

import org.junit.Test;
import static org.junit.Assert.*;
public class TestPercolation {
    @Test
    public void test() {
        Percolation percolation = new Percolation(5);
        boolean flag = percolation.isFull(2,2);
        percolation.open(0,0);
        percolation.open(3,2);
        assertFalse(percolation.isOpen(3,3));
        assertFalse(percolation.isFull(3,3));
        assertFalse(percolation.isFull(3,2));
        percolation.open(4,2);
        percolation.open(2,2);
        boolean flag1 = percolation.isOpen(3,2);
        assertFalse(percolation.percolates());
        assertTrue(flag1);
        percolation.open(1,2);
        percolation.open(0,2);
        boolean flag2 = percolation.isFull(3, 2);
        assertTrue(flag2);
        boolean percolates = percolation.percolates();
        assertTrue(percolates);
    }

    @Test
    public void test1() {
        Percolation percolation = new Percolation(3);
        assertFalse(percolation.percolates());
        assertEquals(0, percolation.numberOfOpenSites());
        percolation.open(1, 1);
        assertFalse(percolation.isFull(1, 1));
        assertTrue(percolation.isOpen(1, 1));
        percolation.open(2, 1);
        percolation.open(0, 1);
        boolean flag = percolation.percolates();
        assertTrue(flag);
    }
}
