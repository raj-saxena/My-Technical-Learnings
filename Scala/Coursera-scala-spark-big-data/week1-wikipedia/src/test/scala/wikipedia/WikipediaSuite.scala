package wikipedia

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterAll, FunSuite}

@RunWith(classOf[JUnitRunner])
class WikipediaSuite extends FunSuite with BeforeAndAfterAll {

  def initializeWikipediaRanking(): Boolean =
    try {
      WikipediaRanking
      true
    } catch {
      case ex: Throwable =>
        println(ex.getMessage)
        ex.printStackTrace()
        false
    }

  override def afterAll(): Unit = {
    assert(initializeWikipediaRanking(), " -- did you fill in all the values in WikipediaRanking (conf, sc, wikiRdd)?")
    import WikipediaRanking._
    sc.stop()
  }

  // Conditions:
  // (1) the language stats contain the same elements
  // (2) they are ordered (and the order doesn't matter if there are several languages with the same count)
  def assertEquivalentAndOrdered(given: List[(String, Int)], expected: List[(String, Int)]): Unit = {
    // (1)
    assert(given.toSet == expected.toSet, "The given elements are not the same as the expected elements")
    // (2)
    assert(
      !(given zip given.tail).exists({ case ((_, occ1), (_, occ2)) => occ1 < occ2 }),
      "The given elements are not in descending order"
    )
  }

  test("'occurrencesOfLang' should work for (specific) RDD with one element") {
    assert(initializeWikipediaRanking(), " -- did you fill in all the values in WikipediaRanking (conf, sc, wikiRdd)?")
    import WikipediaRanking._
    val rdd = sc.parallelize(Seq(WikipediaArticle("title", "Java Jakarta")))
    val res = occurrencesOfLang("Java", rdd) == 1
    assert(res, "occurrencesOfLang given (specific) RDD with one element should equal to 1")
  }

  test("'occurrencesOfLang' should work for (specific) RDD with two element") {
    assert(initializeWikipediaRanking(), " -- did you fill in all the values in WikipediaRanking (conf, sc, wikiRdd)?")
    import WikipediaRanking._
    val rdd = sc.parallelize(List(WikipediaArticle("1", "Scala is great"), WikipediaArticle("2", "Java is OK, but Scala is cooler")))
    val res = occurrencesOfLang("Scala", rdd) == 2
    assert(res, "occurrencesOfLang given (specific) RDD with two element should equal to 2")
  }

  test("'rankLangs' should work for RDD with two elements") {
    assert(initializeWikipediaRanking(), " -- did you fill in all the values in WikipediaRanking (conf, sc, wikiRdd)?")
    import WikipediaRanking._
    val langs = List("Scala", "Java")
    val rdd = sc.parallelize(List(
      WikipediaArticle("1", "Scala is great but Scala might be challenging at first."),
      WikipediaArticle("2", "There are more Java folks. Java is OK, but Scala is cooler."),
      WikipediaArticle("3", "What happens if there are more Java and Scala articles"),
      WikipediaArticle("4", "What happens if there are more Scala and Java articles")
      ))
    val ranked = rankLangs(langs, rdd).toSet
    assert(ranked == Set(("Scala", 4), ("Java", 3)))
  }

  test("'makeIndex' creates a simple index with two entries") {
    assert(initializeWikipediaRanking(), " -- did you fill in all the values in WikipediaRanking (conf, sc, wikiRdd)?")
    import WikipediaRanking._
    val langs = List("Scala", "Java")
    val articles = List(
      WikipediaArticle("1", "Groovy is pretty interesting, and so is Erlang"),
      WikipediaArticle("2", "Scala and Java run on the JVM"),
      WikipediaArticle("3", "Scala is not purely functional")
    )
    val rdd = sc.parallelize(articles)
    val index = makeIndex(langs, rdd)
    assert(index.count() === 2)
  }

  test("'rankLangsUsingIndex' should work for a simple RDD with three elements") {
    assert(initializeWikipediaRanking(), " -- did you fill in all the values in WikipediaRanking (conf, sc, wikiRdd)?")
    import WikipediaRanking._
    val langs = List("Scala", "Java")
    val articles = List(
      WikipediaArticle("1", "Groovy is pretty interesting, and so is Erlang"),
      WikipediaArticle("2", "Scala and Java run on the JVM"),
      WikipediaArticle("3", "Scala is not purely functional")
    )
    val rdd = sc.parallelize(articles)
    val index = makeIndex(langs, rdd)
    val ranked = rankLangsUsingIndex(index)
    assert(ranked.head._1 == "Scala")
  }

  test("'rankLangsReduceByKey' should work for a simple RDD with four elements") {
    assert(initializeWikipediaRanking(), " -- did you fill in all the values in WikipediaRanking (conf, sc, wikiRdd)?")
    import WikipediaRanking._
    val langs = List("Scala", "Java", "Groovy", "Haskell", "Erlang")
    val articles = List(
      WikipediaArticle("1", "Groovy is pretty interesting, and so is Erlang"),
      WikipediaArticle("2", "Scala and Java run on the JVM"),
      WikipediaArticle("3", "Scala is not purely functional"),
      WikipediaArticle("4", "The cool kids like Haskell more than Java"),
      WikipediaArticle("5", "Java is for enterprise developers")
    )
    val rdd = sc.parallelize(articles)
    val ranked = rankLangsReduceByKey(langs, rdd)
    assert(ranked.head._1 == "Java")
  }


  test("'rankLangs' run on the dataset provided in object 'WikipediaData' returns the correct ranking (in descending order)") {
    assert(initializeWikipediaRanking(), " -- did you fill in all the values in WikipediaRanking (conf, sc, wikiRdd)?")
    import WikipediaRanking._
    val langs = List("MATLAB", "Ruby", "Python", "Groovy", "C++", "Java", "Perl", "Clojure", "PHP", "Scala", "Haskell", "C#", "JavaScript", "Objective-C", "CSS")
    val expected = Set(("C#", 705), ("CSS", 372), ("Haskell", 54), ("Groovy", 23), ("Python", 286), ("PHP", 279), ("Scala", 43),
      ("Perl", 144), ("Clojure", 26), ("Ruby", 120), ("MATLAB", 295), ("C++", 334), ("Java", 586), ("Objective-C", 47), ("JavaScript", 1692))
    val rankLangs = WikipediaRanking.rankLangs(langs, wikiRdd)
    val rankLangsUsingIndex = WikipediaRanking.rankLangsUsingIndex(makeIndex(langs, wikiRdd))
    val rankLangsReduceByKey = WikipediaRanking.rankLangsReduceByKey(langs, wikiRdd)

    assert(rankLangs.toSet == expected)
    assert(rankLangsUsingIndex.toSet == expected)
    assert(rankLangsReduceByKey.toSet == expected)
  }
}
