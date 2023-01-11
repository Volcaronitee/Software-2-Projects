import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    /**
     * Test constructor.
     */
    @Test
    public final void testConstructor() {
        NaturalNumber t = this.constructorTest();
        NaturalNumber r = this.constructorRef();
        assertEquals(t, r);
    }

    /**
     * Test int constructor for 0.
     */
    @Test
    public final void testConstructorInt0() {
        final int i = 0;
        NaturalNumber t = this.constructorTest(i);
        NaturalNumber r = this.constructorRef(i);
        assertEquals(t, r);
    }

    /**
     * Test int constructor not 0.
     */
    @Test
    public final void testConstructorInt() {
        final int i = 667;
        NaturalNumber t = this.constructorTest(i);
        NaturalNumber r = this.constructorRef(i);
        assertEquals(t, r);
    }

    /**
     * Test int constructor for max int.
     */
    @Test
    public final void testConstructorIntMax() {
        final int i = Integer.MAX_VALUE;
        NaturalNumber t = this.constructorTest(i);
        NaturalNumber r = this.constructorRef(i);
        assertEquals(t, r);
    }

    /**
     * Test string constructor for 0.
     */
    @Test
    public final void testConstructorString0() {
        final String i = "0";
        NaturalNumber t = this.constructorTest(i);
        NaturalNumber r = this.constructorRef(i);
        assertEquals(t, r);
    }

    /**
     * Test string constructor not 0.
     */
    @Test
    public final void testConstructorString() {
        final String i = "667";
        NaturalNumber t = this.constructorTest(i);
        NaturalNumber r = this.constructorRef(i);
        assertEquals(t.toInt(), r.toInt());
    }

    /**
     * Test string constructor for over max int as string.
     */
    @Test
    public final void testConstructorStringPastMax() {
        final String i = "2147483647667";
        NaturalNumber t = this.constructorTest(i);
        NaturalNumber r = this.constructorRef(i);
        assertEquals(t.toString(), r.toString());
    }

    /**
     * Test natural number constructor for 0.
     */
    @Test
    public final void testConstructorNN0() {
        NaturalNumber i = new NaturalNumber2("0");
        NaturalNumber t = this.constructorTest(i);
        NaturalNumber r = this.constructorRef(i);
        assertEquals(t, r);
    }

    /**
     * Test natural number constructor not 0.
     */
    @Test
    public final void testConstructorNN() {
        NaturalNumber i = new NaturalNumber2("667");
        NaturalNumber t = this.constructorTest(i);
        NaturalNumber r = this.constructorRef(i);
        assertEquals(t, r);
    }

    /**
     * Test natural number constructor for over max int as NN.
     */
    @Test
    public final void testConstructorNNPastMax() {
        NaturalNumber i = new NaturalNumber2("2147483647667");
        NaturalNumber t = this.constructorTest(i);
        NaturalNumber r = this.constructorRef(i);
        assertEquals(t, r);
    }

    /**
     * Test isZero for 0.
     */
    @Test
    public final void testisZero0() {
        NaturalNumber i = new NaturalNumber2("0");
        NaturalNumber t = this.constructorTest(i);
        NaturalNumber r = this.constructorRef(i);
        assertEquals(t, r);
        assertTrue(i.isZero());
    }

    /**
     * Test isZero not 0.
     */
    @Test
    public final void testisZero() {
        NaturalNumber i = new NaturalNumber2("667");
        NaturalNumber t = this.constructorTest(i);
        NaturalNumber r = this.constructorRef(i);
        assertEquals(t, r);
        assertTrue(!i.isZero());
    }

    /**
     * Test isZero for past max int.
     */
    @Test
    public final void testisZeroPastMax() {
        NaturalNumber i = new NaturalNumber2("2147483647667");
        NaturalNumber t = this.constructorTest(i);
        NaturalNumber r = this.constructorRef(i);
        assertEquals(t, r);
        assertTrue(!i.isZero());
    }

    /**
     * Test multiplyBy10 for 0 by 0.
     */
    @Test
    public final void testMultiplyByTen00() {
        NaturalNumber i = new NaturalNumber2("0");
        NaturalNumber t = this.constructorTest(i);
        NaturalNumber r = this.constructorRef(i);

        t.multiplyBy10(0);
        r.multiplyBy10(0);

        assertEquals(t, r);
    }

    /**
     * Test multiplyBy10 for non 0 by 0.
     */
    @Test
    public final void testMultiplyByTen60() {
        NaturalNumber i = new NaturalNumber2("6");
        NaturalNumber t = this.constructorTest(i);
        NaturalNumber r = this.constructorRef(i);

        t.multiplyBy10(0);
        r.multiplyBy10(0);

        assertEquals(t, r);
    }

    /**
     * Test multiplyBy10 for non 0 by non 0.
     */
    @Test
    public final void testMultiplyByTen69() {
        NaturalNumber i = new NaturalNumber2("6");
        NaturalNumber t = this.constructorTest(i);
        NaturalNumber r = this.constructorRef(i);

        final int nine = 9;
        t.multiplyBy10(nine);
        r.multiplyBy10(nine);

        assertEquals(t, r);
    }

    /**
     * Test multiplyBy10 for past max int by 0.
     */
    @Test
    public final void testMultiplyByTenMax0() {
        NaturalNumber i = new NaturalNumber2("2147483647667");
        NaturalNumber t = this.constructorTest(i);
        NaturalNumber r = this.constructorRef(i);

        final int nine = 0;
        t.multiplyBy10(nine);
        r.multiplyBy10(nine);

        assertEquals(t, r);
    }

    /**
     * Test multiplyBy10 for past max int by non 0.
     */
    @Test
    public final void testMultiplyByTenMax9() {
        NaturalNumber i = new NaturalNumber2("2147483647667");
        NaturalNumber t = this.constructorTest(i);
        NaturalNumber r = this.constructorRef(i);

        final int nine = 9;
        t.multiplyBy10(nine);
        r.multiplyBy10(nine);

        assertEquals(t, r);
    }

    /**
     * Test divideBy10 for 0.
     */
    @Test
    public final void testDivideByTen0() {
        NaturalNumber i = new NaturalNumber2("0");
        NaturalNumber t = this.constructorTest(i);
        NaturalNumber r = this.constructorRef(i);

        t.divideBy10();
        r.divideBy10();

        assertEquals(t, r);
    }

    /**
     * Test divideBy10 for non 0.
     */
    @Test
    public final void testDivideByTen667() {
        NaturalNumber i = new NaturalNumber2("667");
        NaturalNumber t = this.constructorTest(i);
        NaturalNumber r = this.constructorRef(i);

        t.divideBy10();
        r.divideBy10();

        assertEquals(t, r);
    }

    /**
     * Test divideBy10 for past max int.
     */
    @Test
    public final void testDivideByTenPastMax() {
        NaturalNumber i = new NaturalNumber2("2147483647667");
        NaturalNumber t = this.constructorTest(i);
        NaturalNumber r = this.constructorRef(i);

        t.divideBy10();
        r.divideBy10();

        assertEquals(t, r);
    }
}
