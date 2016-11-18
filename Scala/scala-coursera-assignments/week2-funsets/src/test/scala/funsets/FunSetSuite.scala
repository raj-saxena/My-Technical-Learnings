package funsets

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
  * This class is a test suite for the methods in object FunSets. To run
  * the test suite, you can either:
  *  - run the "test" command in the SBT console
  *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
  */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
    * Link to the scaladoc - very clear and detailed tutorial of FunSuite
    *
    * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
    *
    * Operators
    *  - test
    *  - ignore
    *  - pending
    */

  /**
    * Tests are written using the "test" operator and the "assert" method.
    */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
    * For ScalaTest tests, there exists a special equality operator "===" that
    * can be used inside "assert". If the assertion fails, the two values will
    * be printed in the error message. Otherwise, when using "==", the test
    * error message will only say "assertion failed", without showing the values.
    *
    * Try it out! Change the values so that the assertion fails, and look at the
    * error message.
    */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
    * When writing tests, one would often like to re-use certain values for multiple
    * tests. For instance, we would like to create an Int-set and have multiple test
    * about it.
    *
    * Instead of copy-pasting the code for creating the set into every test, we can
    * store it in the test class using a val:
    *
    * val s1 = singletonSet(1)
    *
    * However, what happens if the method "singletonSet" has a bug and crashes? Then
    * the test methods are not even executed, because creating an instance of the
    * test class fails!
    *
    * Therefore, we put the shared values into a separate trait (traits are like
    * abstract classes), and create an instance inside each test method.
    *
    */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val union_1_2 = union(s1, s2)
    val union_1_2_3 = union(union_1_2, s3)
  }

  /**
    * This test is currently disabled (by using "ignore") because the method
    * "singletonSet" is not yet implemented and the test would fail.
    *
    * Once you finish your implementation of "singletonSet", exchange the
    * function "ignore" by "test".
    */
  test("singletonSet(1) contains 1") {

    /**
      * We create a new instance of the "TestSets" trait, this gives us access
      * to the values "s1" to "s3".
      */
    new TestSets {
      /**
        * The string argument of "assert" is a message that is printed in case
        * the test fails. This helps identifying which assertion failed.
        */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      assert(contains(union_1_2, 1), "Union 1")
      assert(contains(union_1_2, 2), "Union 2")
      assert(!contains(union_1_2, 3), "Union 3")
    }
  }

  test("Intersection gives common elements of both sets") {
    new TestSets {
      val intersectNone: Set = intersect(s1, s2)

      assert(contains(intersect(union_1_2, s1), 1), "intersection contains")
      assert(contains(intersect(union_1_2, s2), 2), "intersection contains")
      assert(!contains(intersectNone, 1), "intersection doesn't contains")
      assert(!contains(intersectNone, 2), "intersection doesn't contains")
    }
  }

  test("diff of 2 sets") {
    new TestSets {
      val diffSet = diff(union_1_2, s2)

      assert(contains(diffSet, 1), "contains diff")
      assert(!contains(diffSet, 2), "doesn't contains common")
    }
  }


  test("Filter set") {
    new TestSets {
      val filteredSet: Set = filter(union_1_2, x => x > 1)
      assert(contains(filteredSet, 2))
      assert(!contains(filteredSet, 1))
      assert(!contains(filteredSet, 5))
    }
  }

  test("Forall elements greater than 0 and less than 10") {
    new TestSets {
      assert(forall(union_1_2_3, (x) => x > 0 && x < 5))
      assert(!forall(union_1_2_3, (x) => x > 0 && x < 3))
    }
  }

  test("Exists element in the set") {
    new TestSets {
      assert(exists(union_1_2_3, x => x == 2), "exists")
      assert(!exists(union_1_2_3, x => x == 5), "not exists")
    }
  }

  test("Map function") {
    new TestSets {
      assert(contains(map(s1, _ + 1), 2), "contains 2")
      assert(contains(map(singletonSet(1000), _ - 1), 999), "contains 999")
      assert(!contains(map(s1, _ + 1), 1), "doesn't contains 2")
    }
  }
}
