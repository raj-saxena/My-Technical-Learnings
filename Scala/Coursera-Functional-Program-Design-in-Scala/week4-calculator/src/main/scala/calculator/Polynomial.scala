package calculator

object Polynomial {
  def computeDelta(a: Signal[Double], b: Signal[Double], c: Signal[Double]): Signal[Double] = {
    Signal(b() * b() - 4 * a() * c())
  }

  def computeSolutions(a: Signal[Double], b: Signal[Double], c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] = {
    Signal {
      delta() match {
        case del if del < 0 => Set()
        case del if del == 0 => Set(-b() / 2 * a())
        case del => Set((-b() + Math.sqrt(del)) / (2 * a()), (-b() - Math.sqrt(del)) / (2 * a()))
      }
    }
  }
}
