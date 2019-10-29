-- * Check if the first letter in a String is 'A'
beginsWithA (c:_) = c == 'A'
beginsWithA _ = False

-- * Check which String in a List starts with letter 'A'
listbeginsWithA [] = []
listbeginsWithA (x:xs) = beginsWithA x : listbeginsWithA xs

-- * Return the length of a generic String
sumStringElem [] = []
ssumStringElem (x:xs) = map (length) (x:xs)

-- * Stefano version Final function
totalLength xs = foldl (+) 0 filteredLengths
        where filteredLengths = map length startingWithA
              startingWithA = filter (\x -> head x == 'A') xs