import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /**
     * Test for constructor with no arguments.
     */
    @Test
    public final void testConstructorEmpty() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected.toString(), m.toString());
    }

    /**
     * Test for constructor with one arguments.
     */
    @Test
    public final void testConstructorFillOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "A");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "A");
        assertEquals(mExpected.toString(), m.toString());
    }

    /**
     * Test for constructor with arguments of increasing value.
     */
    @Test
    public final void testConstructorFillIncrease() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "A",
                "B", "C", "D", "E");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "A", "B", "C", "D", "E");
        assertEquals(mExpected.toString(), m.toString());
    }

    /**
     * Test for constructor with arguments of decreasing value.
     */
    @Test
    public final void testConstructorFillDecrease() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "E",
                "D", "C", "B", "A");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "E", "D", "C", "B", "A");
        assertEquals(mExpected.toString(), m.toString());
    }

    /**
     * Test for constructor with arguments of mixed value.
     */
    @Test
    public final void testConstructorFillMixed() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "E",
                "B", "A", "D", "C");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "E", "B", "A", "D", "C");
        assertEquals(mExpected.toString(), m.toString());
    }

    /**
     * Test for kernel method add starting with no entries.
     */
    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");
        assertEquals(mExpected.toString(), m.toString());
    }

    /**
     * Test for kernel method add starting with one entry.
     */
    @Test
    public final void testAddOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "A");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "A", "B");
        m.add("B");
        assertEquals(mExpected.toString(), m.toString());
    }

    /**
     * Test for kernel method add a new max value with mixed entries.
     */
    @Test
    public final void testAddMax() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "E",
                "D", "C", "B", "A");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "E", "D", "C", "B", "A", "F");
        m.add("F");
        assertEquals(mExpected.toString(), m.toString());
    }

    /**
     * Test for kernel method add a new min value with mixed entries.
     */
    @Test
    public final void testAddMin() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "E",
                "D", "C", "B", "F");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "E", "D", "C", "B", "F", "A");
        m.add("A");
        assertEquals(mExpected.toString(), m.toString());
    }

    /**
     * Test for kernel method add a new middle value with mixed entries.
     */
    @Test
    public final void testAddMid() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "E",
                "D", "B", "F", "A");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "E", "D", "B", "F", "A", "C");
        m.add("C");
        assertEquals(mExpected.toString(), m.toString());
    }

    /**
     * Test for kernel method add a new duplicate middle value with mixed
     * entries.
     */
    @Test
    public final void testAddMidCopy() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "E",
                "D", "B", "F", "A", "C");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "E", "D", "B", "F", "A", "C", "B");
        m.add("B");
        assertEquals(mExpected.toString(), m.toString());
    }

    /**
     * Test for kernel method changeToExtractionMode with no entries.
     */
    @Test
    public final void testChangeToExtractionModeEmpty() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);

        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();

        assertEquals(mExpected.toString(), m.toString());
    }

    /**
     * Test for kernel method changeToExtractionMode with mixed entries.
     */
    @Test
    public final void testChangeToExtractionModeFill() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "A",
                "D", "C", "B");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "A", "D", "C", "B");

        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();

        assertEquals(mExpected.toString(), m.toString());
    }

    /**
     * Test for kernel method removeFirst to empty list.
     */
    @Test
    public final void testRemoveFirstToEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "A");
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, false,
                "A");

        String first = m.removeFirst();
        String firstExpected = mExpected.removeFirst();

        assertEquals(firstExpected, first);
        assertEquals(mExpected, m);
    }

    /**
     * Test for kernel method removeFirst to empty list.
     */
    @Test
    public final void testRemoveFirstIncreasing() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "A",
                "B", "C", "D", "E");
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, false,
                "A", "B", "C", "D", "E");

        String first = m.removeFirst();
        String firstExpected = mExpected.removeFirst();

        assertEquals(firstExpected, first);
        assertEquals(mExpected, m);
    }

    /**
     * Test for kernel method removeFirst to empty list.
     */
    @Test
    public final void testRemoveFirstDecreasing() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "E",
                "D", "C", "B", "A");
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, false,
                "E", "D", "C", "B", "A");

        String first = m.removeFirst();
        String firstExpected = mExpected.removeFirst();

        assertEquals(firstExpected, first);
        assertEquals(mExpected, m);
    }

    /**
     * Test for kernel method removeFirst to empty list.
     */
    @Test
    public final void testRemoveFirstMixed() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "D",
                "B", "A", "E", "C");
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, false,
                "D", "B", "A", "E", "C");

        String first = m.removeFirst();
        String firstExpected = mExpected.removeFirst();

        assertEquals(firstExpected, first);
        assertEquals(mExpected, m);
    }

    // TODO - add test cases for add, changeToExtractionMode, removeFirst,
    // isInInsertionMode, order, and size

    /**
     * Test isInInsertionMode with empty sorting machines starting true.
     */
    @Test
    public final void testIsInInsertionModeEmptyTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        assertEquals(mExpected, m);
        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());
    }

    /**
     * Test isInInsertionMode with empty sorting machines starting false.
     */
    @Test
    public final void testIsInInsertionModeEmptyFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        assertEquals(mExpected, m);
        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());
    }

    /**
     * Test isInInsertionMode with full sorting machines starting True.
     */
    @Test
    public final void testIsInInsertionModeFullTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "A");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "A");
        assertEquals(mExpected, m);
        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());
    }

    /**
     * Test isInInsertionMode with full sorting machines starting False.
     */
    @Test
    public final void testIsInInsertionModeFullFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "A");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "A");
        assertEquals(mExpected, m);
        assertEquals(mExpected.isInInsertionMode(), m.isInInsertionMode());
    }

    /**
     * Test order with empty sorting machines.
     */
    @Test
    public final void testOrderEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        assertEquals(mExpected, m);
        assertEquals(mExpected.order(), m.order());
    }

    /**
     * Test order with full sorting machines.
     */
    @Test
    public final void testOrderFull() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "A");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "A");
        assertEquals(mExpected, m);
        assertEquals(mExpected.order(), m.order());
    }

    /**
     * Test order with different sorting machines.
     */
    @Test
    public final void testOrderDifferent() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "A");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "B");
        assertEquals(mExpected.order(), m.order());
    }

    /**
     * Test size with empty sorting machines.
     */
    @Test
    public final void testSizeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        assertEquals(mExpected.toString(), m.toString());
        assertEquals(mExpected.size(), m.size());
    }

    /**
     * Test size with single element sorting machines.
     */
    @Test
    public final void testSizeSingle() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "A");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "A");

        assertEquals(mExpected.toString(), m.toString());
        assertEquals(mExpected.size(), m.size());
    }

    /**
     * Test size with multiple element sorting machines.
     */
    @Test
    public final void testSizeMultiple() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "A",
                "B", "C", "D");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "A", "B", "C", "D");

        assertEquals(mExpected.toString(), m.toString());
        assertEquals(mExpected.size(), m.size());
    }

}
