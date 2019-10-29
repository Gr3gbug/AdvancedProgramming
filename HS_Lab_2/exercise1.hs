-- *** Exercise 1 ***
data Expr a = Const a | Sum (Expr a) (Expr a) | Mul (Expr a) (Expr a) | Div (Expr a) (Expr a)
eval :: Num a => Expr a -> a  

eval (Const a) = a
eval (Sum x y) = (eval x) + (eval y)
eval (Mul x y) = (eval x) * (eval y)