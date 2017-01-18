package patmat

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import patmat.Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {

  trait TestTrees {
    val t1 = Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5)
    val t2 = Fork(Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5), Leaf('d', 4), List('a', 'b', 'd'), 9)
  }


  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }


  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a', 'b', 'd'))
    }
  }

  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }


  test("times should return frequency") {
    val result = times(List('a', 'b', 'a'))
    assert(result === List(('a', 2), ('b', 1)) || result === List(('b', 1), ('a', 2)))
  }

  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 3)))
  }

  test("singleton works") {
    assert(!singleton(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3)))))
    assert(singleton(makeOrderedLeafList(List(('t', 2)))))
  }

  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(combine(leaflist) === List(Fork(Leaf('e', 1), Leaf('t', 2), List('e', 't'), 3), Leaf('x', 4)))
  }

  test("until should work") {
    val trees = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    val result = until(singleton, combine)(trees)
    assert(result.size === 1)
    assert(result === List(Fork(Fork(Leaf('e', 1), Leaf('t', 2), List('e', 't'), 3), Leaf('x', 4), List('e', 't', 'x'), 7)))
  }

  test("create code tree") {
    val str = "ettxxxx"
    val result = createCodeTree(string2Chars(str))
    assert(result === Fork(Fork(Leaf('e', 1), Leaf('t', 2), List('e', 't'), 3), Leaf('x', 4), List('e', 't', 'x'), 7))
  }

  test("decoding single character should work") {
    val codeTree = createCodeTree(string2Chars("ettxxxx"))

    val traverseToE = decode(codeTree, List(0,0))
    val traverseToT = decode(codeTree, List(0,1))
    val traverseToX = decode(codeTree, List(1))

    assert(traverseToE == string2Chars("e"))
    assert(traverseToT == string2Chars("t"))
    assert(traverseToX == string2Chars("x"))
  }

  test("decoding string") {
    val codeTree = createCodeTree(string2Chars("ettxxxx"))

    val result = decode(codeTree, List(0, 1, 0, 0, 0, 0, 1))

    assert(result === string2Chars("teex"))
  }

  test("decoding french secret") {
      assert(decodedSecret === string2Chars("huffmanestcool"))

  }

  test("encode") {
    val codeTree = createCodeTree(string2Chars("ettxxxx"))
    assert(encode(codeTree)(string2Chars ("teex")) === List(0, 1, 0, 0, 0, 0, 1))
  }

  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
    }
  }

  test("create CodeTable works") {
    assert(convert(createCodeTree(string2Chars("ettxxxx"))) === List(('e',List(0, 0)), ('t',List(0, 1)), ('x',List(1))))
  }

  test("quickEncode") {
    val codeTree = createCodeTree(string2Chars("ettxxxx"))
    assert(quickEncode(codeTree)(string2Chars ("teex")) === List(0, 1, 0, 0, 0, 0, 1))
  }
}
