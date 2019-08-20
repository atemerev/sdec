package ai.reactivity.sdec

/**
 * A numeric class for decimal numbers, dec-normalizing the underlying Double numbers.
 * Much faster that BigDecimal. Can be safely compared, used as keys in maps etc.
 *
 * Default normalizing factor is up to 8 digits (1e8). Can be redefined in normFactor implicit
 * parameter (1e4, 1e16 etc).
 */
class Decimal(private val dbl: Double) extends AnyVal with Ordered[Decimal] {

  /**
   * Underlying double value (always normalized, i.e. "safe").
   */
  def value: Double = dbl

  def /(that: Decimal): Decimal = this.dbl / that.dbl

  def +(that: Decimal): Decimal = this.dbl + that.dbl

  def -(that: Decimal): Decimal = this.dbl - that.dbl

  def *(that: Decimal): Decimal = this.dbl * that.dbl

  def unary_-(): Decimal = -this.dbl

  def abs(): Decimal = math.abs(this.dbl)

  def toInt: Int = dbl.toInt

  def toLong: Long = dbl.toLong

  def toFloat: Float = dbl.toFloat

  def toDouble: Double = dbl

  // Yes, we can do that!
  override def compare(that: Decimal): Int = this.dbl.compare(that.dbl)

  // And that!
  def ==(that: Decimal): Boolean = this.dbl == that.dbl
  def !=(that: Decimal): Boolean = this.dbl != that.dbl

  override def toString: String = if (dbl == dbl.toInt) {
    dbl.toInt.toString
  } else {
    BigDecimal.valueOf(dbl).bigDecimal.stripTrailingZeros.toPlainString
  }
}

object Decimal {

  import scala.language.implicitConversions
  val DEFAULT_SCALE_FACTOR: Double = 1e8

  // can be implicitly lifted from doubles, ints and longs

  implicit def fromDouble(dbl: Double): Decimal = Decimal(dbl)
  implicit def fromInt(int: Int): Decimal = Decimal.apply(int)
  implicit def fromLong(long: Long): Decimal = Decimal.apply(long)

  /**
   * "Normalize" a double value up to 8 (by default) digits of decimal precision
   * @param value Raw double value to normalize
   * @param normFactor Normalization factor. 1e8 is the default (8 digits of decimal precision).
   *                   Override only if you know what you are doing.
   * @return "Safe" double value, rounded up to 8
   */
  def normalize(value: Double)(implicit normFactor: Double = DEFAULT_SCALE_FACTOR): Double = {
    if (value > Long.MaxValue / normFactor || value < -Long.MaxValue / normFactor) {
      value
    }
    else {
      val pre = if (value < 0) {
        value * normFactor - 0.5
      } else {
        value * normFactor + 0.5
      }
      pre.toLong / normFactor
    }
  }

  /**
   * Wrap a double into Decimal, rounding it up to 8 significant digits.
   * @param value A double value to represent as decimal.
   * @param normFactor Norm factor. The default factor (1e8) gives up to 8 digits of decimal precision.
   *                   Can be overridden if necessary (be sure that you know what you are doing)
   * @return Decimal value, safe for comparisons.
   */
  def apply(value: Double)(implicit normFactor: Double = DEFAULT_SCALE_FACTOR): Decimal = {
    val normed = normalize(value)(normFactor)
    new Decimal(normed)
  }

  /**
   * Adds ".toDecimal"
   * @param dbl Double value to extend
   */
  implicit class Extension(val dbl: Double) extends AnyVal {
    def toDecimal(implicit normFactor: Double = DEFAULT_SCALE_FACTOR): Decimal = Decimal(dbl)(normFactor)
  }
}
