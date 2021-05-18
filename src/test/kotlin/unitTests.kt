import org.junit.Test
import kotlin.test.assertEquals

class OrderServiceTest {
    // make sure a typical use case displays as expected
    @Test fun ServiceWorksWithProvidedExample() {
        val order = OrderService(arrayOf("Apple", "Apple", "Orange", "Apple"))
        assertEquals(order.getTotal(), 2.05)
    }

    // make sure total is $0.00 when no items are given
    @Test fun ServiceDisplaysZeroWithNoArguments() {
        val order = OrderService(arrayOf())
        assertEquals(order.getTotal(), 0.00)
    }

    // test with capitalized and lowercase words
    @Test fun ServiceWorksWithCapitalAndLowercase() {
        val order = OrderService(arrayOf("ORANGE", "apple", "ApPlE", "orANge"))
        assertEquals(order.getTotal(), 1.70)
    }

    // test with plural and single items
    @Test fun ServiceWorksWithPluralAndSingleItems() {
        val order = OrderService(arrayOf("Orange", "Oranges", "Apple", "Apples", "oranges", "apples"))
        assertEquals(order.getTotal(), 2.55)
    }

    // test with items that we don't have prices for
    @Test fun ServiceProvidesCorrectPriceWithIncorrectItems() {
        val order = OrderService(arrayOf("Apple", "potatoes", "oranges", "watermelon", "apples", "apples"))
        assertEquals(order.getTotal(), 2.05)
    }

    // THESE ARE SOME OTHER IDEAS YOU COULD POTENTIALLY TEST
    // test with a single item
    // test with all numbers
    // test different command line input styles
    // test with a large number of items
    // test adding individual items vs. using numbers to specify how many you want of each item
}
