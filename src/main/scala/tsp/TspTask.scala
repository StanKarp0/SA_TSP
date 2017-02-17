package tsp

import algo.SATask

import scala.util.Random

/**
  * Created by wojciech on 17.02.17.
  */
class TspTask(list: List[Point]) extends SATask[Double] {

  var path: List[Point] = list
  var dist: Double = TspTask.distance(path)

  def next(f: (Double) => Boolean): Double = {
    // TODO Smth better, combine, distance
    val newPath = TspTask.combine(path)
    val newDist = TspTask.distance(newPath)
    if(f(dist - newDist)) {
      dist = newDist
      path = newPath
    }
    dist
  }

}
object TspTask {

  private def list2Dist(list: List[Point]) = list match {
    case List(e1, e2) => e1.distance(e2)
    case _ => 0.0
  }

  private def combine(path: List[Point]): List[Point] = {
    val List(n1,n2) = List(Random.nextInt(path.size),Random.nextInt(path.size)).sortWith(_ < _)
    path.take(n1) ::: path.slice(n1,n2).reverse ::: path.takeRight(path.size - n2)
  }

  def distance(path: List[Point]): Double = {
    (List(path.headOption,path.lastOption).flatten.sliding(2,1) ++ path.sliding(2,1))
      .map(list2Dist)
      .sum
  }

}
