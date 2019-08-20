# SDEC: Scala Fast Decimals

A fast decimal arithmetic implementation using AnyVals and dec-normalized doubles as the underlying primitive. 
By default, allows up to 8 digits of decimal precision (suitable for e.g. cryptocurrency trading applications.) 
Performance is nearly equivalent to vanilla doubles, however, Decimals can be compared to each other and used as map
keys (depending on your precision needs).

For complex calculations, it is advisable to do everything with doubles, and interpret the result as Decimal only 
when the final result is obtained. However, direct arithmetic operators are also provided for convenience.

(Caveat emptor: I use it in production, but obviously I don't know what I am doing. Proceed with extreme caution.)

Usage:

```scala
import ai.reactivity.sdec.Decimal

val expr: Decimal = 0.2 * 0.2
val result: Decimal = 0.04

assert(expr == result, "Decimal arithmetic equality works")

val priceLevels = collection.mutable.Map.empty[Decimal, Decimal]
priceLevels += (0.2 * 0.2 -> 1.2345) 
```