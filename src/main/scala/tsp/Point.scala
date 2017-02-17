package tsp

/**
  * Created by wojciech on 17.02.17.
  */
case class Point(x: Double, y: Double) {

  def distance(p: Point): Double =
    math.sqrt(math.pow(x - p.x, 2) + math.pow(y - p.y, 2))

}
