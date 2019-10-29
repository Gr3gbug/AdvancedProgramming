data Tree a = Leaf a | Node(Tree a, Tree a) deriving Show

instance Functor Tree where
        fmap f (Leaf x) = Leaf (f x)
        fmap f (Node(t1,t2)) = Node (fmap f t1, fmap f t2)