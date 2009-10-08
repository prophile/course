euclid_gcd :: Integer -> Integer -> Integer

euclid_gcd a 0 = a
euclid_gcd a b = euclid_gcd b (mod a b)

main = do
  putStr ("gcd(3, 4) = " ++ (show (euclid_gcd 3 4)) ++ "\n")
