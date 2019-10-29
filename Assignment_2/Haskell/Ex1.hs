import Data.List
data ListBag a = LB [(a, Int)] deriving (Show, Eq)

isUnique :: (Eq a) => [a] -> Bool
wf :: Eq a => ListBag a -> Bool

-- **********************
-- ** MANDATORY METHOD **
-- **********************

-- * Check if a ListBag is well formed
wf (LB xs) = isUnique (map fst xs)

-- * Returns Empty Bag
empty = LB []

-- * Add single element to List Bag
singleTon x = (LB [(x, 1)])

-- * returning a ListBag containing all and only the elements of lst, each with the right multiplicity
-- I'm going to map the passed list according to the following istruction:
--  1) Through the sort 'list' and the 'group' i can obtain the ordered list grouping elements. 
--     I can do this because i know that for the given semantic of a ListBag i can't multiple elements, but
--     each element must be unique in the ListBag
--  2) Finally through a lambda function i retrieve the lenght and obtaining the first tuple with head, in 
--     this way i'll populate the new obtained ListBag
fromList list = (LB ( map (\l -> (head l, length l)) (group (sort list)))) 

-- * Check if a LB is empty or not 
isEmpty (LB []) = True
isEmpty (LB _) = False

-- Method wich returns a list containing all the elements of the ListBag bag, each one repeated a number of times equal to its multiplicity
-- How does it work > 
-- TODO  *** 
toList (LB xs) = listBagExtension xs []
  where listBagExtension [] support = support
        listBagExtension (x:xs) support = 
          let explodedEl = replicate (snd x) (fst x)
              new_support = support ++ explodedEl
          in listBagExtension xs new_support

-- * returning the multiplicity of v in the ListBag bag if v is an element of bag, and 0 otherwise
-- Here i'm going to check first of all if the element which i want is a candidate element inside the ListBag, if will be, 
-- then, because the filter will give me the lenght of obtained results:
--  1) If there is the element the lenght will equal to 1, so i can get it the multiplicity of the candidate element apply the 
--     'snd' function on the head of the obtained list, otherwise i'll return 0
mul v (LB bag) = if (length ((filter (\x -> (fst x) == v) bag)) == 0) then 0 else snd (head ((filter (\x -> (fst x) == v) bag)))

-- (sumBag bag bag'), returning the ListBag obtained by adding all the elements of bag' to bag
-- I can exploit the '++' operator of haskell 
sumBag (LB bag) (LB bag') = (LB (bag ++ bag'))

-- ***********************
-- ** ADDITIONAL METHOD **
-- *********************** --> Note: this method is not used in the upside code, but simply help me to reason how to obtain the final results

-- * Check if element is equal or not in the string
isUnique list = case list of
        [] -> True
        (x:xs) -> if x `elem` xs then False else isUnique xs

-- * Add generic element 
addElement x y = (LB [(x, y)])

-- Take the elem of the tuple 
takeElem' (LB list) = fst (head (fromBagToList (LB list)))

-- Take multiplicity of the tuble 
takeMult' (LB list) = snd (head (fromBagToList (LB list)))

-- * Convert LB to list
fromBagToList (LB list) = list

    
-- * Take tuple from a ListBag
takeTuple (LB ((a,b):xs)) = (a,b)

-- * Function which take a number 'x' and 'a' value and returns 'x' times 'a' value
mult x 0 = []
mult x 1 = [x]
mult x n = x : mult x (n-1)