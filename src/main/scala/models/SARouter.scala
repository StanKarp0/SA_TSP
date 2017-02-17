package models

import akka.actor.{Actor, ActorRef, Props}
import algo.SimulatedAnnealing.SAInfo

/**
  * Created by wojciech on 17.02.17.
  */
abstract class SARouter[T,R: Numeric] extends Actor {

  import SARouter._

  def worker: Props
  var taskSender: Option[ActorRef] = None

  def receive = {
    case t: Task[T] =>
      taskSender = Some(sender())
      val collector = context.actorOf(CollectorActor.props(self,worker,t,t.routee))
      collector ! SAWorker.WorkTask(t.t,t.info)
    case r: Result[R] =>
      taskSender.foreach(_ ! r.res)
      taskSender = None
    case _ =>
  }

}
object SARouter {

  case class Task[T](t: T, info: SAInfo, routee: Int)
  case class Result[R](res: R)

}
