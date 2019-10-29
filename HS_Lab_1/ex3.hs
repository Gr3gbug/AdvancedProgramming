-- * Nota non funziona bene --

listRepl 0 (x:xs) = [] 
listRepl k (x:xs) = map (replicate' k) xs 
    where
        replicate' 0 n = []
        replicate' k x = x : replicate' (k-1) x