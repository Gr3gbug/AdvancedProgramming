data Expr a = Const a | Sum (Expr a) (Expr a) | Mul (Expr a) (Expr a) | Div (Expr a) (Expr a)

safeEval (Const x) = Just x
safeEval (Sum x y) = sumMaybe (safeEval x) (safeEval y)
safeEval (Mul x y) = mulMaybe (safeEval x) (safeEval y)
safeEval (Div x y) = divMaybe (safeEval x) (safeEval y)

sumMaybe Nothing _ = Nothing
sumMaybe _ Nothing = Nothing
sumMaybe (Just x) (Just y) = Just (x + y)

mulMaybe Nothing _ = Nothing
mulMaybe _ Nothing = Nothing
mulMaybe (Just x) (Just y) = Just (x * y)

divMaybe Nothing _  = Nothing
divMaybe _ Nothing  = Nothing
divMaybe _ (Just 0) = Nothing
divMaybe (Just x) (Just y) = Just (quot x y)