module Ex5 where
import Ex1
import Ex2

import Test.HUnit
import Data.List

-- We simply construct a ListBag out of a single element
returnLB :: Eq a => a -> ListBag a
returnLB x = singleton x

-- We apply the function f to every element of the ListBag bag, then
-- merge all the obtained ListBags (just like a flatMap for Java streams)
-- To do that we:
-- 1) transform the ListBag into a list
-- 2) apply f to every element of that list (i.e. every element of the initial ListBag)
--    obtaining a list of ListBags
-- 3) transform each ListBag to a list
-- 4) merge all the lists
-- 5) create a ListBag out of the list 
bindLB :: (Eq a, Eq b) => ListBag a -> (a -> ListBag b) -> ListBag b
bindLB bag f = fromList $ concat $ map toList listOfBags
        where listOfBags = map f $ toList bag

-- *** Question 2 ***
-- It's not possible to define an instance (Monad ListBag) for the same reason
-- we cannot define (Functor ListBag), i.e. The functions defined inside
-- the Monad constructor class do not containt the Eq constraint


-- Test zone !!!
testFunction x = fromList $ replicate 2 x

testWfReturnLB = TestCase $ assertBool
        "wf (returnLB 5) == True"
        $ wf $ returnLB 5

testWfBindLBSingle = TestCase $ assertBool
        "wf $ bindLB (singleton 5) testFunction == True"
        $ wf $ bindLB (singleton 5) testFunction

testWfBindLBMultiple = TestCase $ assertBool
        "wf $ bindLB (fromList [1,4,3,6]) testFunction  == True"
        $ wf $ bindLB (fromList [1,4,3,6]) testFunction

testSort = TestCase $ assertEqual
        "[1,1,3,3,4,4,6,6] == bindLB (fromList [1,4,3,6]) testFunction"
        [1,1,1,1,3,3,4,4,6,6]
        $ sort $ toList $ bindLB (fromList [1,4,3,6,1]) testFunction

testlist = TestList [TestLabel "testWfReturnLB" testWfReturnLB,
                     TestLabel "testWfBindLBSingle" testWfBindLBSingle,
                     TestLabel "testWfBindLBMultiple" testWfBindLBMultiple,
                     TestLabel "testSort" testSort]
