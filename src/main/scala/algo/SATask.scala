package algo

/**
  * Created by wojciech on 17.02.17.
  */
trait SATask[R] {

  /**
    *
    * @param f diff
    * @return accept
    */
  def next(f: R => Boolean): R

}
