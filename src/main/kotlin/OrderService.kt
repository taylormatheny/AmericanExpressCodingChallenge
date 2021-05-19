import java.util.concurrent.TimeUnit
import kotlin.math.round

@OptIn(ExperimentalStdlibApi::class)
/* currently, this service requires every object in the order to be listed individually. Example: 2 apples does not
 * work. To improve functionality, you would want to make it so that the user could list the quantity of each item.
 * This could be done through the command line, or by asking the user for how many they want of each item passed in.
 */

// The Order Service. Not a true service, since there is no front end. Instead, the order information is stored
// inside the OrderService class.
class OrderService(customerOrder: Array<String>) {
    // private var order : MutableList<String> = mutableListOf()
    // Change the order to a map, so we can track the item and quantity.
    private val order: MutableMap<String, Int> = mutableMapOf()
    private val mapOfCosts = mapOf("apple" to 0.6, "orange" to 0.25)
    private var total : Double = 0.0
    private var notifications: CustomerNotificationService? = null

    init {
        // add the order items into the order array after formatting them correctly
        val removePunctuation = Regex("[^a-z0-9]")
        customerOrder.forEach { item ->
            var updatedItem = removePunctuation.replace(item.lowercase(), "")

            // in case of input such as "apples" instead of "apple"
            if (mapOfCosts[updatedItem] == null)
                updatedItem = updatedItem.dropLast(1)

            if (mapOfCosts[updatedItem] != null) {
                if (order[updatedItem] == null) {
                    order[updatedItem] = 1
                } else {
                    order[updatedItem] = order[updatedItem]!! + 1
                }
            }
        }

        calculateTotal()
    }

    // getters
    fun getTotal(): Double { return total }
    fun getOrder(): MutableMap<String, Int> { return order}

    fun displayTotal() = println("Your total is \$" + "%.2f".format(total))

    // a function to calculate the total cost after initialization. No current function, but could be useful later.
    private fun calculateTotal() {
        total = 0.0

        order.forEach { (item, quantity) ->
            // we round to ensure the computer performs the math operations correctly
            total = round((total + when(item) {
                "apple" -> {
                    val applesToBeChargedFor = calculateDiscountQuantity(quantity, 2, 1)
                    (applesToBeChargedFor * mapOfCosts[item]!!)
                }
                "orange" -> {
                    val orangesToBeChargedFor = calculateDiscountQuantity(quantity, 3, 2)
                    (orangesToBeChargedFor * mapOfCosts[item]!!)
                }
                else -> {
                    (quantity * mapOfCosts[item]!!)
                }
            }) * 100) / 100
        }
    }

    // this function calculates the number of each item to charge someone for based on the discount. For example, if I
    // want to put a deal on apples 3 for price of 2, I would use (# of apples, 3, 2) as the parameters
    private fun calculateDiscountQuantity(quantity: Int, numberToDiscount: Int, discountQuantity: Int): Int {
        return ((quantity / numberToDiscount) * discountQuantity) + (quantity % numberToDiscount)
    }

    // user input variable for testing purposes
    fun placeOrder(userInput: String? = null) {
        println(displayTotal())
        print("Would you like to approve total and place order? (yes/no) ")

        do {
            val answer = userInput ?: readLine()
            var invalid = false

            when (answer) {
                "yes", "Yes", "y", "Y" -> {
                    notifications = CustomerNotificationService(OrderStatus.SUBMITTED)
                    TimeUnit.SECONDS.sleep(45L)

                    // simulate order out for delivery
                    notifications!!.status = OrderStatus.DELIVERY
                    TimeUnit.SECONDS.sleep(15L)

                    // simulate order delivered
                    notifications!!.status = OrderStatus.COMPLETE
                }
                "no", "No", "n", "N" -> {
                    println("Your order has been canceled.")
                }
                "else" -> {
                    invalid = true
                    println("Command not recognized. Please enter \"yes\" or \"no\": ")
                }
            }
        } while (invalid)
    }

}