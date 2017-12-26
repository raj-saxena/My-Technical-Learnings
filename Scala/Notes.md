## Extractors
	* Works opposite of `apply()`.
	* Classes that have an `unapply` can be used for pattern matching.
	* Syntax:
		- `def unapply(object: S): Option[T]`
		- `def unapply(object: S): Option[(T1, ..., Tn)]`
		- `def unapply(object: S): Boolean` 
			// Used with '@', eg: `case freeUser @ premiumCandidate()`
		- Infix pattern - eg: case a#::b#::c
			- ```object #:: {
 				 	def unapply[A](xs: Stream[A]): Option[(A, Stream[A])] =
    					if (xs.isEmpty) None
    					else Some((xs.head, xs.tail))
					}
			  ```		
		- Extracting Sequence
			- def unapplySeq(object: S): Option[Seq[T]]
		- Some fixed, rest seq
			- def unapplySeq(object: S): Option[(T1, .., Tn-1, Seq[T])]

## Try-catch
	- Try[A] represents a computation that may result in a value of type A, if it is successful, or in some Throwable if something has gone wrong.

## Random
	* Collect = Filter + Map
		- def collect[B](pf: PartialFunction[-A, +B])
	* By-name things are not evaluated unless needed. Eg:
		- `fn: => <Type>` methods // type starts with '=>'
		- `.getOrElse` alternatives.
		- `lazy x` vals
	* Option should be thought of as special collections of zero or one items.
	* For comprehensions work like chain of flatmaps.

## Future & Promises
	* Enclosed in Future {...} //future.apply 
	* `Future` are read only, the computed value is written via `Promise`.
	* Callbacks on Futures => `onComplete` or partial functions [ `onSuccess` or `onFailure`]
	* Only one `Future` is bound to each corresponding `Promise`. So, calling `myPromise.future` multiple times would return the same `Future`.
	* To complete a `Promise` with a success, you call its `success` method, passing it the value that the Future associated with it is supposed to have:
	* In practice
		 - If you are using a modern Scala web framework, it will allow you to return your responses as something like a Future[Response] instead of blocking and then returning your finished Response. This is important since it allows your web server to handle a huge number of open connections with a relatively low number of threads. By always giving your web server Future[Response], you maximize the utilization of the web server’s dedicated thread pool.
	* Use Futures for - network calls, blocking IO, CPU-bound operations and wrap them in Future.

## Callbacks 
	* The Try[T] is similar to Option[T] or Either[T, S], in that it is a monad potentially holding a value of some type. However, it has been specifically designed to either hold a value or some throwable object. Where an Option[T] could either be a value (i.e. Some[T]) or no value at all (i.e. None), Try[T] is a Success[T] when it holds a value and otherwise Failure[T], which holds an exception. Failure[T] holds more information than just a plain None by saying why the value is not there. In the same time, you can think of Try[T] as a special version of Either[Throwable, T], specialized for the case when the left value is a Throwable.
	f onComplete {
  		case Success(posts) => for (post <- posts) println(post)
  		case Failure(t) => println("An error has occured: " + t.getMessage)
	}

## Combinators - `map`	- one function to another

## Function composition
	* Scala functions provide two composing functions that will help us here: Given two functions f and g, f.compose(g) returns a new function that, when called, will first call g and then apply f on the result of it. Similarly, f.andThen(g) returns a new function that, when called, will apply g to the result of f.

