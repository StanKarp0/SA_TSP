package models

import akka.actor.{Actor, ActorRef, Props}
import akka.routing.BroadcastPool

/**
  * Created by wojciech on 17.02.17.
  */
class CollectorActor[T,R: Numeric](sender: ActorRef, worker: Props, task: SARouter.Task[T], routee: Int) extends Actor {

  val workers = routee
  val router: ActorRef = context.actorOf(BroadcastPool(workers).props(worker), "router")
  var results: List[R] = Nil


  override def preStart(): Unit = {
    super.preStart()
    router ! SAWorker.WorkTask(task.t,task.info)
  }

  def receive = {
    case r: SARouter.Result[R] =>
      results +:= r.res
      println(r.res)
      if(results.size == workers) {
        sender.tell(SARouter.Result(results.min),self)
        context.stop(self)
      }
    case _ =>
  }

}
object CollectorActor {

  def props[T,R: Numeric](sender: ActorRef, worker: Props, task: SARouter.Task[T], routee: Int): Props =
    Props(new CollectorActor(sender, worker, task, routee))
  //Props(classOf[CollectorActor[T,R]],sender, worker, task)

}
