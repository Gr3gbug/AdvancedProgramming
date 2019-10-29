module Ex2 where
import Ex1

-- TEXT: Define an instance of the constructor class Foldable for the constructor ListBag defined in Exercise 1. 
-- To this aim, choose a minimal set of functions to be implemented, as described in the documentation of Foldable. 
-- Intuitively, folding a ListBag with a binary function should apply the function to the elements of the multiset, 
-- ignoring the multiplicities.
-- EXECUTION: We wrote a map function through which 
-- 1) We map the list contained inside the ListBag into a new list 
-- 2) The new list contain only the first element of each tuple of the original list 
-- 3) Finally we 'foldr' that list.
instance Foldable ListBag where
        foldr f acc (LB list) = foldr f acc (map fst list)

-- EXECUTION: We wrote a function to:
-- 1) Convert the ListBag to a list
-- 2) Map that list 
-- 3) finally we convert the list back to a ListBag.
-- NOTE: i have used the 'function application' operator to properly set the precedence to avoid mistakes
mapLB :: (Eq a, Eq b) => (a -> b) -> ListBag a -> ListBag b
mapLB f bag = fromList $ map f $ toList bag

-- ***************
-- * EXERCISE 3 **
-- ***************
-- The reason because is not possible to define an istance of Functor for 
-- ListBag by providing mapLB is due to the fact that we cannot define an 
-- instance (Functor ListBag) because (ListBag a) requires type 'Eq a' while 
-- 'fmap', defined inside the generic class (Functor f), does not. 
-- So it cannot be done