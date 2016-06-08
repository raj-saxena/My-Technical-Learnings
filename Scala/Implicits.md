# Implicits 

Implicits can be used to provide default conversion between types or default parameters. There are three places implicits are used in the language: 
* conversions to an expected type, 
* conversions of the receiver of a selection, 
* and implicit parameters.

* `*Implicits conversions*` - To make a String appear to be a subtype of `RandomAccessSeq[T]`, we can define an implicit conversion from String to an adapter class that actually is a subtype of RandomAccessSeq:

```scala
  implicit def stringWrapper(s: String) = 
    new RandomAccessSeq[Char] {
      def length = s.length
      def apply(i: Int) = s.charAt(i)
    }
```

That's it. The implicit conversion is just a normal method. The only thing that's special is the `implicit` modifier at the start. We can apply the conversion explicitly to transform Strings to RandomAccessSeqs:
```bash
  scala> stringWrapper("abc123") exists (_.isDigit)
  res0: Boolean = true
```
But, we can also leave out the conversion and still get the same behavior:
```bash
  scala> "abc123" exists (_.isDigit)
  res1: Boolean = true
```


## Rules
* *Marking Rule*: Only definitions marked implicit are available.
* *Scope Rule* : An inserted implicit conversion must be in scope as a single identifier (via import), or be associated with the source or target type of the conversion.
* *Non-Ambiguity Rule*: An implicit conversion is only inserted if there is no other possible conversion to insert. 
* *One-at-a-time Rule*: Only one implicit is tried and no chaining.
* *Explicits-First Rule*: Whenever code type checks as it is written, no implicits are attempted. 


## View Bound:

 Think of `T <% Ordered[T]` as saying, "I can use any T, so long as T can be treated as an Ordered[T]." This is different from saying that T is an Ordered[T], which is what an upper bound, `T <: Ordered[T]`, would say. For example, even though class Int is not a subtype of Ordered[Int], you could still pass a List[Int] to maxList so long as an implicit conversion from Int to Ordered[Int] is available. Moreover, if type T happens to already be an Ordered[T], you can still pass a List[T] to maxList. The compiler will use an implicit identity function, declared in Predef:
```scala
  implicit def identity[A](x: A): A = x
```


More details [Here](http://www.artima.com/pins1ed/implicit-conversions-and-parameters.html)