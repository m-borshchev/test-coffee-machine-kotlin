// This is the refactored version of your code into a class-based design
// that fulfills the final task requirements: handling input via a single method

package machine

enum class State {
    CHOOSING_ACTION, CHOOSING_COFFEE, FILLING_WATER, FILLING_MILK, FILLING_BEANS, FILLING_CUPS
}

class CoffeeMachine {
    private var water = 400
    private var milk = 540
    private var beans = 120
    private var cups = 9
    private var money = 550
    private var state = State.CHOOSING_ACTION

    fun process(input: String) {
        when (state) {
            State.CHOOSING_ACTION -> handleAction(input)
            State.CHOOSING_COFFEE -> handleBuy(input)
            State.FILLING_WATER -> {
                water += input.toInt()
                state = State.FILLING_MILK
                println("Write how many ml of milk you want to add:")
            }
            State.FILLING_MILK -> {
                milk += input.toInt()
                state = State.FILLING_BEANS
                println("Write how many grams of coffee beans you want to add:")
            }
            State.FILLING_BEANS -> {
                beans += input.toInt()
                state = State.FILLING_CUPS
                println("Write how many disposable cups you want to add:")
            }
            State.FILLING_CUPS -> {
                cups += input.toInt()
                state = State.CHOOSING_ACTION
                println("\nWrite action (buy, fill, take, remaining, exit):")
            }
        }
    }

    private fun handleAction(input: String) {
        when (input) {
            "buy" -> {
                println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
                state = State.CHOOSING_COFFEE
            }
            "fill" -> {
                state = State.FILLING_WATER
                println("Write how many ml of water you want to add:")
            }
            "take" -> {
                println("I gave you \$$money")
                money = 0
                println("\nWrite action (buy, fill, take, remaining, exit):")
            }
            "remaining" -> {
                printState()
                println("\nWrite action (buy, fill, take, remaining, exit):")
            }
            "exit" -> {
                // exit handled in main loop
            }
        }
    }

    private fun handleBuy(choice: String) {
        when (choice) {
            "1" -> makeCoffee(250, 0, 16, 4)
            "2" -> makeCoffee(350, 75, 20, 7)
            "3" -> makeCoffee(200, 100, 12, 6)
            "back" -> println("\nWrite action (buy, fill, take, remaining, exit):")
        }
        state = State.CHOOSING_ACTION
    }

    private fun makeCoffee(w: Int, m: Int, b: Int, cost: Int) {
        when {
            water < w -> println("Sorry, not enough water!")
            milk < m -> println("Sorry, not enough milk!")
            beans < b -> println("Sorry, not enough coffee beans!")
            cups < 1 -> println("Sorry, not enough disposable cups!")
            else -> {
                println("I have enough resources, making you a coffee!")
                water -= w
                milk -= m
                beans -= b
                cups--
                money += cost
            }
        }
        println("\nWrite action (buy, fill, take, remaining, exit):")
    }

    private fun printState() {
        println("""
The coffee machine has:
$water ml of water
$milk ml of milk
$beans g of coffee beans
$cups disposable cups
$money of money""".trimIndent())
    }
}

fun main() {
    val machine = CoffeeMachine()
    while (true) {
        val input = readLine() ?: break
        if (input == "exit") break
        machine.process(input)
    }
}