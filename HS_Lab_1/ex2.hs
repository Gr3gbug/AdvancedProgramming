-- * prelude version --
mySumOdd_p (x:xs) = t*x + mySumOdd_p xs where 
        t=mod x 2 -- t check if number is odd
mySumOdd_p _ = 0 

-- * Recursive version --
mySumOdd_r arr = sum (filter odd arr) 

-- *** Recursive with dot point notation ***
-- mySumOdd = sum . filter odd    