import scala.util.Random

trait Generator[+T] {
  self =>
  // alias for this

  def generate: T

  def map[S](f: T => S): Generator[S] = new Generator[S] {
    def generate = f(self.generate)
  }

  def flatMap[S](f: T => Generator[S]): Generator[S] = new Generator[S] {
    def generate = f(self.generate).generate
  }
}

val integers = new Generator[Int] {
  def generate: Int = Random.nextInt()
}

val booleans = integers.map(_ >= 0)

def single[T](x: T) = new Generator[T] {
  def generate: T = x
}

// Tree stuff
trait Tree

case class InnerNode(left: Tree, right: Tree) extends Tree

case class Leaf(int: Int) extends Tree

def leaves = for (x <- integers) yield Leaf(x)

def innerNodes = for {
  left <- trees
  right <- trees
} yield InnerNode(left, right)

def trees: Generator[Tree] = for {
  isLeaf <- booleans
  tree <- if (isLeaf) leaves else innerNodes
} yield tree

trees.generate
trees.generate
trees.generate