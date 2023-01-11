import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, contains, and size

    /**
     * Test for constructor with no arguments.
     */
    @Test
    public final void constructorTest1() {
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef();

        assertEquals(s, sExpected);
    }

    /**
     * Test for constructor with no arguments in increasing order.
     */
    @Test
    public final void constructorTest2() {
        Set<String> s = this.createFromArgsTest("A", "B", "C");
        Set<String> sExpected = this.createFromArgsRef("A", "B", "C");

        assertEquals(s, sExpected);
    }

    /**
     * Test for constructor with no arguments in decreasing order.
     */
    @Test
    public final void constructorTest3() {
        Set<String> s = this.createFromArgsTest("C", "B", "A");
        Set<String> sExpected = this.createFromArgsRef("C", "B", "A");

        assertEquals(s, sExpected);
    }

    /**
     * Test for constructor with no arguments in mixed order.
     */
    @Test
    public final void constructorTest4() {
        Set<String> s = this.createFromArgsTest("C", "E", "B", "A", "D");
        Set<String> sExpected = this.createFromArgsRef("C", "E", "B", "A", "D");

        assertEquals(s, sExpected);
    }

    /**
     * Test for add with empty set.
     */
    @Test
    public final void addTest1() {
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef("A");

        s.add("A");

        assertEquals(s, sExpected);
    }

    /**
     * Test for add with non empty set.
     */
    @Test
    public final void addTest2() {
        Set<String> s = this.createFromArgsTest("A");
        Set<String> sExpected = this.createFromArgsRef("A", "B");

        s.add("B");

        assertEquals(s, sExpected);
    }

    /**
     * Test for add with increasing multi variable set.
     */
    @Test
    public final void addTest3() {
        Set<String> s = this.createFromArgsTest("A", "B", "C", "D");
        Set<String> sExpected = this.createFromArgsRef("A", "B", "C", "D", "E");

        s.add("E");

        assertEquals(s, sExpected);
    }

    /**
     * Test for add with decreasing multi variable set.
     */
    @Test
    public final void addTest4() {
        Set<String> s = this.createFromArgsTest("E", "D", "C", "B");
        Set<String> sExpected = this.createFromArgsRef("E", "D", "C", "B", "A");

        s.add("A");

        assertEquals(s, sExpected);
    }

    /**
     * Test for add with mixed multi variable set.
     */
    @Test
    public final void addTest5() {
        Set<String> s = this.createFromArgsTest("C", "E", "B", "A");
        Set<String> sExpected = this.createFromArgsRef("C", "E", "B", "A", "D");

        s.add("D");

        assertEquals(s, sExpected);
    }

    /**
     * Removing first entry.
     */
    @Test
    public final void removeTest1() {
        Set<String> s = this.createFromArgsTest("C", "E", "B", "A", "D");
        Set<String> sExpected = this.createFromArgsRef("E", "B", "A", "D");

        s.remove("C");

        assertEquals(s, sExpected);
    }

    /**
     * Empty set expected.
     */
    @Test
    public final void removeTest2() {
        Set<String> s = this.createFromArgsTest("C");
        Set<String> sExpected = this.createFromArgsRef();

        s.remove("C");

        assertEquals(sExpected, s);
    }

    /**
     * Remove multiple to empty.
     */
    @Test
    public final void removeTest3() {
        Set<String> s = this.createFromArgsTest("C", "E", "B", "A", "D");
        Set<String> sExpected = this.createFromArgsRef();

        s.remove("C");
        s.remove("E");
        s.remove("B");
        s.remove("A");
        s.remove("D");

        assertEquals(sExpected, s);
    }

    /**
     * Remove in middle of set.
     */
    @Test
    public final void removeTest4() {
        Set<String> s = this.createFromArgsTest("C", "E", "B", "A", "D");
        Set<String> sExpected = this.createFromArgsRef("C", "E", "A", "D");

        s.remove("B");

        assertEquals(s, sExpected);
    }

    /**
     * Remove last entry.
     */
    @Test
    public final void removeTest5() {
        Set<String> s = this.createFromArgsTest("C", "E", "B", "A", "D");
        Set<String> sExpected = this.createFromArgsRef("C", "E", "B", "A");

        s.remove("D");

        assertEquals(s, sExpected);
    }

    /**
     * Remove any value.
     */
    @Test
    public final void removeAnyTest1() {
        Set<String> s = this.createFromArgsTest("C", "E", "B", "A", "D");
        Set<String> sExpected = this.createFromArgsRef("C", "E", "B", "A", "D");

        String remove = s.removeAny();
        sExpected.remove(remove);

        assertEquals(s, sExpected);
    }

    /**
     * Empty set expected.
     */
    @Test
    public final void removeAnyTest2() {
        Set<String> s = this.createFromArgsTest("C");
        Set<String> sExpected = this.createFromArgsRef();

        s.removeAny();

        assertEquals(s, sExpected);
    }

    /**
     * Multiple values to empty.
     */
    @Test
    public final void removeAnyTest3() {
        Set<String> s = this.createFromArgsTest("C", "E", "B");
        Set<String> sExpected = this.createFromArgsRef();

        s.removeAny();
        s.removeAny();
        s.removeAny();

        assertEquals(s, sExpected);
    }

    /**
     * Test middle of values.
     */
    @Test
    public final void containsTest1() {
        Set<String> s = this.createFromArgsTest("C", "E", "B", "A", "D");
        Set<String> sExpected = this.createFromArgsRef("C", "E", "B", "A", "D");

        boolean contain = s.contains("B");

        assertTrue(contain);
        assertEquals(s, sExpected);
    }

    /**
     * Test first value.
     */
    @Test
    public final void containsTest2() {
        Set<String> s = this.createFromArgsTest("C", "E", "B", "A", "D");
        Set<String> sExpected = this.createFromArgsRef("C", "E", "B", "A", "D");

        boolean contain = s.contains("C");

        assertTrue(contain);
        assertEquals(s, sExpected);
    }

    /**
     * Test last value.
     */
    @Test
    public final void containsTest3() {
        Set<String> s = this.createFromArgsTest("C", "E", "B", "A", "D");
        Set<String> sExpected = this.createFromArgsRef("C", "E", "B", "A", "D");

        boolean contain = s.contains("D");

        assertTrue(contain);
        assertEquals(s, sExpected);
    }

    /**
     * Test value not in set.
     */
    @Test
    public final void containsTest4() {
        Set<String> s = this.createFromArgsTest("C", "E", "B", "A", "D");
        Set<String> sExpected = this.createFromArgsRef("C", "E", "B", "A", "D");

        boolean contain = s.contains("Z");

        assertTrue(!contain);
        assertEquals(s, sExpected);
    }

    /**
     * Test set size of 5.
     */
    @Test
    public final void sizeTest1() {
        Set<String> s = this.createFromArgsTest("C", "E", "B", "A", "D");
        Set<String> sExpected = this.createFromArgsRef("C", "E", "B", "A", "D");

        final int five = 5;

        assertEquals(s.size(), five);
        assertEquals(s, sExpected);
    }

    /**
     * Test set size of 0.
     */
    @Test
    public final void sizeTest2() {
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef();

        final int zero = 0;

        assertEquals(s.size(), zero);
        assertEquals(s, sExpected);
    }

    /**
     * Test set size after using remove().
     */
    @Test
    public final void sizeTest3() {
        Set<String> s = this.createFromArgsTest("C");
        Set<String> sExpected = this.createFromArgsRef();

        s.remove("C");

        final int zero = 0;

        assertEquals(s.size(), zero);
        assertEquals(s, sExpected);
    }
}
