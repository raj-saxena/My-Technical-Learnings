

module Main where
c = putStrLn "C!"
 
combine before after =
  do before
     putStrLn "In the middle"
     after
 
main = do combine c c
          let b = combine (putStrLn "Hello!") (putStrLn "Bye!")
          let d = combine (b) (combine c c)
          putStrLn "So long!"


-- Output
-- C!
-- In the middle
-- C!
-- So long!

-- // b and d are not printed because they aren't used.





doubleMe x = x + x  

doubleUsAndAdd x y = doubleMe x + doubleMe y

-- Square numbers only if lesser than 100 else return number itself

squareIfSmallerThan100 x = if x < 100 then x * x else x