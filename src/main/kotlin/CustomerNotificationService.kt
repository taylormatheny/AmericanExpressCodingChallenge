import java.util.*

// for customer notifications, we will need several thins.
// 1. Order Placed Successfully
// 2. Order Out for Delivery
// 3. Order Delivered

enum class OrderStatus() {
    SUBMITTED {
              override fun statusMessage() {
                  val deliveryTime = Calendar.getInstance()
                  deliveryTime.add(Calendar.MINUTE, +1)
                  println("Your order has been completed successfully. " +
                          "Estimated delivery time: " + deliveryTime.time)
              }
              },
    DELIVERY {
            override fun statusMessage() = println("Your order is out for delivery.")
             },
    COMPLETE{
            override fun statusMessage() = println("Your order has been delivered.")
    };

    abstract fun statusMessage()
}

// This class is a basic implementation. No observables or subscriptions are used because there is no front-end or
// servers for them to work with and so this is the simplest implementation I could think of.
class CustomerNotificationService {
    var status: OrderStatus
        set(_status) {
            field = _status
            status.statusMessage()
        }

    constructor(_status: OrderStatus) {
        status = _status
    }
}