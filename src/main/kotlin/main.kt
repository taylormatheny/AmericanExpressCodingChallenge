// fpr this problem, I am assuming I can write the code
// using a class for my order service. I will not make it
// into a true service at this point, as I have no front-end
// or database to pull information from.
fun main(args: Array<String>) {
    // pull the orders from the command line
    val order = OrderService(args)

    // display the total
    order.placeOrder()
}