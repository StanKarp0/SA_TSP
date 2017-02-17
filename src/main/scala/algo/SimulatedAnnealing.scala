package algo

import algo.SimulatedAnnealing.SAInfo

/**
  * Created by wojciech on 17.02.17.
  */
class SimulatedAnnealing[R](task: SATask[R],info: SAInfo)(implicit op: Numeric[R]){

  def start(): R = {
    Iterator.iterate(info.tempStart)(_ * info.alpha)
      .takeWhile(_ > info.tempEnd)
      .flatMap(temp => Iterator.fill(info.iteration)(temp))
      .zip(Iterator.iterate(task)(identity))
      .map{ case (temp, t) => t.next{
          case diff if op.gt(diff, op.zero) => true
          case diff if math.random < math.exp(op.toDouble(diff)/temp) => true
          case _ => false
        }
      }.min
  }

}
object SimulatedAnnealing {
  case class SAInfo(
                     alpha: Double = 0.98,
                     iteration: Int = 5,
                     tempStart: Double = 100.0,
                     tempEnd: Double = 0.5
                   )
}
