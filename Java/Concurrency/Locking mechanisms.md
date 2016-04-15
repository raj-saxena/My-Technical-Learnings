# Lock
  * Interface that provides additional functionalities than the synchronize(d) construct.

## CountDownLatch
  * Serves as barrier which restricts one `Thread` to wait for one or more Threads before starts processing.
  * The waiting thread registers to wait by calling `latch.await()`
  * To do this, initialize the latch with the number of times the latch should be counted-down using `latch.countDown()`. Once the count reaches zero, the awaiting thread would proceed.
  * ###### Important 
    * CountDownLatch **cannot be reused/reset.**
    * CountDownLatch preferred over `Thread.join()` as the former can be used in `ExecutorService` while the latter is used while manually managing the threads.
    * Usage : good for application initialization.

## CyclicBarrier
  * Similar ot `CountDownLatch` but instead of calling `countdown()`, threads mutually wait by calling `await()`.
  * A barrier that restricts threads until X no of threads have called await where X is the counter with which lock was initialized.
  * ###### Important 
    * Can be **reused** by calling `reset()`.
    * Usage : to build mechanism where all threads are waiting for all participants to join or to build fork-join/map-reduce kind of problems.
    * Preferable application context as can be reused.

## Semaphore
  * A Bounded lock that is initialized with a number of permits. 
  * Threads can take the lock as long as the permits are > 0 by calling `acquire()`.
  * Locks are released and permit count incremented by calling `release()`.
  * ###### Important
    * A one permit Semaphore is same as a **lock/synchronize(d) construct.**
    * Semaphores can be used to bound access to collections like implementing ArrayBlockingQueue to bound the element size.