import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.map.Map;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, value,
    // hasKey, and size

    /**
     * Test empty constructor.
     */
    @Test
    public final void testConstructor() {
        Map<String, String> m = this.constructorTest();
        Map<String, String> mExpected = this.constructorRef();

        assertEquals(m, mExpected);
    }

    /**
     * Test non empty constructor.
     */
    @Test
    public final void testConstructorFill() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b", "c",
                "d");

        assertEquals(m, mExpected);
    }

    /**
     * Test add.
     */
    @Test
    public final void testAdd1() {
        Map<String, String> m = this.createFromArgsTest("a", "b");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b", "c",
                "d");

        m.add("c", "d");

        assertEquals(m, mExpected);
    }

    @Test
    public final void testAdd2() {
        Map<String, String> m = this.createFromArgsTest("a", "b");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b", "g",
                "h");

        m.add("g", "h");

        assertEquals(m, mExpected);
    }

    /**
     * Test remove to empty.
     */
    @Test
    public final void testRemove1() {
        Map<String, String> m = this.createFromArgsTest("a", "b");
        Map<String, String> mExpected = this.createFromArgsRef();

        m.remove("a");

        assertEquals(m, mExpected);
    }

    /**
     * Test remove with remain.
     */
    @Test
    public final void testRemove2() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b");

        m.remove("c");

        assertEquals(m, mExpected);
    }

    /**
     * Test removeAny to empty.
     */
    @Test
    public final void testRemoveAny1() {
        Map<String, String> m = this.createFromArgsTest("a", "b");
        Map<String, String> mExpected = this.createFromArgsRef();

        m.removeAny();

        assertEquals(m, mExpected);
    }

    /**
     * Test removeAny with remain.
     */
    @Test
    public final void testRemoveAny2() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d");
        Map<String, String> mExpected = this.createFromArgsRef("c", "d");

        m.removeAny();

        assertEquals(m, mExpected);
    }

    /**
     * Test value.
     */
    @Test
    public final void testValue1() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b", "c",
                "d");

        assertEquals(m.value("a"), "b");
        assertEquals(m, mExpected);
    }

    /**
     * Test value.
     */
    @Test
    public final void testValue2() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b", "c",
                "d");

        assertEquals(m.value("c"), "d");
        assertEquals(m, mExpected);
    }

    /**
     * Test hasKey with key in map.
     */
    @Test
    public final void testHasKey1() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b", "c",
                "d");

        assertTrue(m.hasKey("a"));
        assertEquals(m, mExpected);
    }

    /**
     * Test hasKey without key in map.
     */
    @Test
    public final void testHasKey2() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b", "c",
                "d");

        assertTrue(!m.hasKey("e"));
        assertEquals(m, mExpected);
    }

    /**
     * Test hasKey using key as a value.
     */
    @Test
    public final void testHasKey3() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b", "c",
                "d");

        assertTrue(!m.hasKey("b"));
        assertEquals(m, mExpected);
    }

    /**
     * Test size.
     */
    @Test
    public final void testSize1() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b", "c",
                "d");

        assertTrue(m.size() == 2);
        assertEquals(m, mExpected);
    }

    /**
     * Test size.
     */
    @Test
    public final void testSize2() {
        Map<String, String> m = this.createFromArgsTest("a", "b", "c", "d", "h",
                "i");
        Map<String, String> mExpected = this.createFromArgsRef("a", "b", "c",
                "d", "h", "i");

        assertTrue(m.size() == 3);
        assertEquals(m, mExpected);
    }
}
