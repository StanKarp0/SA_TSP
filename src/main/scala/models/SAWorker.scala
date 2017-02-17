package models

import akka.actor.Actor
import algo.{SATask, SimulatedAnnealing}
import algo.SimulatedAnnealing.SAInfo

/**
  * Created by wojciech on 17.02.17.
  */
abstract class SAWorker[T,R: Numeric] extends Actor {

  import SAWorker._

  def task(f: T): SATask[R]

  def receive = {
    case w: WorkTask[T] =>
      val sender = context.sender()
      val sa = new SimulatedAnnealing(task(w.t),w.info)
      val res = sa.start()
      sender ! SARouter.Result(res)
    case _ =>
  }

}
object SAWorker {
  case class WorkTask[T](t: T, info: SAInfo)
}
