import org.junit.Rule
import org.junit.Test
import java.io.*
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertSame

class OrderServiceTest {
    // make sure a typical use case displays as expected
    @Test fun serviceWorksWithProvidedExample() {
        val order = OrderService(arrayOf("Apple", "Apple", "Orange", "Apple"))
        assertEquals(order.getTotal(), 1.45)
    }

    // make sure total is $0.00 when no items are given
    @Test fun serviceDisplaysZeroWithNoArguments() {
        val order = OrderService(arrayOf())
        assertEquals(order.getTotal(), 0.00)
    }

    // test with capitalized and lowercase words
    @Test fun serviceWorksWithCapitalAndLowercase() {
        val order = OrderService(arrayOf("ORANGE", "apple", "ApPlE", "orANge"))
        assertEquals(order.getTotal(), 1.10)
    }

    // test with plural and single items
    @Test fun serviceWorksWithPluralAndSingleItems() {
        val order = OrderService(arrayOf("Orange", "Oranges", "Apple", "Apples", "oranges", "apples"))
        assertEquals(order.getTotal(), 1.70)
    }

    // test with items that we don't have prices for
    @Test fun serviceProvidesCorrectPriceWithIncorrectItems() {
        val order = OrderService(arrayOf("Apple", "potatoes", "oranges", "watermelon", "apples", "apples"))
        assertEquals(order.getTotal(), 1.45)
    }

    // Test with a single item
    @Test fun orderWithOneItem() {
        val order = OrderService(arrayOf("apple"))
        assertEquals(order.getTotal(), 0.60)
    }

    // Test with invalid number input
    @Test fun numberInput() {
        val order = OrderService(arrayOf("123456", "80", "0"))
        assertEquals(order.getTotal(), 0.00)
    }

    // Test with commas separating items
    @Test fun orderWithCommas() {
        val order = OrderService(arrayOf("apple,", "apple,", "orange,", "apple"))
        assertEquals(order.getTotal(), 1.45)
    }

    // Test with a large input
    @Test fun largeOrder() {
        val order = OrderService(arrayOf("apple", "orange", "orange", "apple", "apple", "apple", "orange", "orange",
        "orange", "orange", "apple", "apple", "apple", "apple", "apple", "orange", "orange", "orange", "orange",
        "orange", "orange", "apple", "apple", "apple", "apple", "apple", "apple", "apple", "orange", "orange", "apple"))
        assertEquals(order.getTotal(), 7.90)
    }

    // THE FOLLOWING TESTS ARE FOR TESTING ORDER SUBMISSIONS
    private val standardOut: PrintStream = System.out
    private val outputStreamCaptor: ByteArrayOutputStream = ByteArrayOutputStream()

    @BeforeTest
    fun setUp() {
       System.setOut(PrintStream(outputStreamCaptor))
    }
    @AfterTest
    fun tearDown() {
        System.setOut(standardOut)
    }

    // Order canceled correctly
    @Test fun cancelOrder() {
        val order = OrderService(arrayOf("Apple", "Apple", "Orange", "Apple"))
        order.placeOrder("no")
        assert(outputStreamCaptor.toString().contains("Your order has been canceled."))
    }

    // Order notifications are correct
    @Test fun placeOrderCorrectNotifications() {
        val order = OrderService(arrayOf("Apple", "Apple", "Orange", "Apple"))
        order.placeOrder("yes")
        assert(outputStreamCaptor.toString().contains("Your order has been completed successfully."))
        assert(outputStreamCaptor.toString().contains("Your order is out for delivery."))
        assert(outputStreamCaptor.toString().contains("Your order has been delivered."))
    }
}
