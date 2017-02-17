package tsp

import akka.actor.Props
import models.SARouter

/**
  * Created by wojciech on 17.02.17.
  */
class TspRouter extends SARouter[List[Point], Double]{

  def worker: Props = {
    Props[TspWorker]
  }

}
