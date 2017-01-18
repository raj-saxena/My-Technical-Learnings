package recfun

import java.util

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
    * Exercise 1
    */
  def pascal(c: Int, r: Int): Int = {
    if (c == 0 || c == r) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
    * Exercise 2
    */
  def balance(chars: List[Char]): Boolean = {
    val LEFT = '('
    val RIGHT = ')'
    val stack = new util.Stack[Char]

    def checkBalanced(chars: List[Char], stack: util.Stack[Char]): Boolean = {
      if (chars.isEmpty) stack.empty()
      else {
        val next = chars.head

        if (next == LEFT) stack.push(LEFT)
        else if (next == RIGHT) {
          if(!stack.empty() && stack.peek() == LEFT) stack.pop()
          else stack.push(RIGHT)
        }

        checkBalanced(chars.tail, stack)
      }
    }

    checkBalanced(chars, stack)
  }

  /**
    * Exercise 3
    */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money < 0 || coins.isEmpty) 0
    else if (money == 0) 1
    else countChange(money - coins.head, coins) + countChange(money, coins.tail)
  }
}
