package quickcheck

import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen._
import org.scalacheck.Prop._
import org.scalacheck._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = for {
    item <- arbitrary[A]
    heap <- oneOf(const(empty), genHeap)
  } yield insert(item, heap)

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("min of 2") = forAll { (a: A, b: A) =>
    findMin(insert(a, insert(b, empty))) == Math.min(a, b)
  }

  property("insert and delete") = forAll { (a: A) =>
    isEmpty(deleteMin(insert(a, empty)))
  }

  property("check sorted by finding and deleting min") = forAll { (h: H) =>
    def isSorted(h: H): Boolean =
      if (isEmpty(h)) true
      else {
        val min = findMin(h)
        val newH = deleteMin(h)
        isEmpty(newH) || (min <= findMin(newH) && isSorted(newH))
      }

    isSorted(h)
  }

  property("check min after melding") = forAll { (h1: H, h2: H) =>
    val commonMin = findMin(meld(h1, h2))
      commonMin == Math.min(findMin(h1), findMin(h2))
  }

  property("check meld") = forAll { (first: H, second: H) =>
    def heapEqual(h1: H, h2: H): Boolean =
      if (isEmpty(h1) && isEmpty(h2)) true
      else {
        val min1 = findMin(h1)
        val min2 = findMin(h2)
        min1 == min2 && heapEqual(deleteMin(h1), deleteMin(h2))
      }

    val merged1 = meld(first, second)
    val minFirst = findMin(first)
    val mergedWithElementShuffle = meld(deleteMin(first), insert(minFirst, second))

    heapEqual(merged1, mergedWithElementShuffle)
  }
}
