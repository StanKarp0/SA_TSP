package main

import akka.actor.{ActorSystem, Props}
import algo.SimulatedAnnealing
import tsp.{Points, TspRouter, TspTask}
import akka.pattern.ask
import akka.util.Timeout
import models.SARouter

import scala.concurrent.Await
import scala.concurrent.duration._


/**
  * Created by wojciech on 17.02.17.
  */
object Main extends App {

  //val sa = new SimulatedAnnealing(new TspTask(Points.exemple1),SimulatedAnnealing.SAInfo())
  //println(sa.start())

  val system = ActorSystem("System")
  import system.dispatcher
  val router = system.actorOf(Props[TspRouter],"tspRouter")

  implicit val timeout = Timeout(1000.seconds)

  val a = router ? SARouter.Task(Points.exemple1,SimulatedAnnealing.SAInfo(
    alpha = 0.999,
    iteration = 20
  ),8)
  val res = Await.result(a,Duration.Inf)
  println(s"Result: $res")
  a.onSuccess({case _ => system.terminate()})
}
