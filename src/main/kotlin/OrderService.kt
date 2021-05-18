import kotlin.math.round

@OptIn(ExperimentalStdlibApi::class)

// The Order Service. Not a true service, since there is no front end. Instead, the order information is stored
// inside the OrderService class.
class OrderService(customerOrder: Array<String>) {
    private var order : MutableList<String> = mutableListOf()
    private val mapOfCosts = mapOf("apples" to 0.6, "apple" to 0.6, "oranges" to 0.25, "orange" to 0.25)
    private var total : Double = 0.0

    init {
        // add the order items into the order array after formatting them correctly
        val removePunctuation = Regex("[^a-z0-9]")
        customerOrder.forEach { item ->
            val updatedItem = removePunctuation.replace(item.lowercase(), "")

            if (mapOfCosts[updatedItem] != null) {
                order.add(updatedItem)
                // To ensure correct math on the computer's end and two-decimals of precision
                total = round((total + mapOfCosts[updatedItem]!!) * 100) / 100
            }
        }
    }

    // getters
    fun getTotal(): Double { return total }
    fun getOrder(): MutableList<String> { return order}

    fun displayTotal() = println("Your total is \$" + "%.2f".format(total))

    // a function to calculate the total cost after initialization. No current function, but could be useful later.
    private fun calculateTotal() {
        total = 0.0

        order.forEach { item ->
            if (mapOfCosts[item] != null)
                total += mapOfCosts[item]!!
            else
                println("Item $item in your order was not found in the store.")
        }
    }
}