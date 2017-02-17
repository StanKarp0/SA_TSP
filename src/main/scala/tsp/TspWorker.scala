package tsp

import algo.SATask
import models.SAWorker

/**
  * Created by wojciech on 17.02.17.
  */
class TspWorker extends SAWorker[List[Point],Double] {

  def task(l: List[Point]): SATask[Double] = {
    new TspTask(l)
  }

}
