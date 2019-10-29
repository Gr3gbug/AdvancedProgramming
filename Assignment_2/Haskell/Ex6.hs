module Ex6 where
import Ex1

-- Class declaration
class MultiSet t where
        emptyMS     :: Eq a => t a
        singletonMS :: Eq a => a -> t a
        fromListMS  :: Eq a => [a] -> t a
        isEmptyMS   :: Eq a => t a -> Bool
        toListMS    :: Eq a => t a -> [a]
        sumMS       :: Eq a => t a -> t a -> t a
        mulMS       :: Eq a => a -> t a -> Int

-- Instance declaration
instance MultiSet ListBag where
        emptyMS     = empty
        singletonMS = singleton
        fromListMS  = fromList
        isEmptyMS   = isEmpty
        toListMS    = toList
        sumMS       = sumBag
        mulMS       = mul

-- Another instance of MultiSet, it's similar to a MultiBag but uses a list
-- instead of a tuple to store elements.
-- The list referring to an element contains that element repeated a number
-- of times equal to its multiplicity.
data ListBag2 a = LB2 [[a]] deriving (Show, Eq)

-- Instead of implementing every function required by the MultiSet class
-- from scratch, we provide 2 helper functions which "translates" a ListBag2
-- into a ListBag and viceversa, then we use these to implement the required functions.

-- We flatten the list of lists and then use the obtained list to create a ListBag.
fromListBag2 :: Eq a => ListBag2 a -> ListBag a
fromListBag2 (LB2 xs) = fromList $ concat xs

-- We map each tuple referring to an element to a list which contains the element
-- multiplicity time, then create a ListBag2 out of the outer list.
toListBag2 :: Eq a => ListBag a -> ListBag2 a
toListBag2 (LB xs) = LB2 (map expand xs)
        where expand (element, multiplicity) = replicate multiplicity element

instance MultiSet ListBag2 where
        emptyMS         = toListBag2 empty
        singletonMS     = toListBag2 . singleton
        fromListMS      = toListBag2 . fromList
        isEmptyMS       = isEmpty . fromListBag2
        toListMS        = toList . fromListBag2
        sumMS bag1 bag2 = toListBag2 $ sumBag (fromListBag2 bag1) (fromListBag2 bag2)
        mulMS v bag     = mul v (fromListBag2 bag)

-- A simple function which should always return False (it adds 1 to the MultiSet and
-- then checks if the MultiSet is empty).
manipulateMultiSet :: (MultiSet t, Eq a, Num a) => t a -> Bool
manipulateMultiSet ms = isEmptyMS $ sumMS ms (singletonMS 1)
