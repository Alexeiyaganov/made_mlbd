import java.io.{File, PrintWriter}
import breeze.linalg._
import breeze.numerics.sqrt
import scalaglm._
object Hw3 extends App {


  def file_read(name: String): DenseMatrix[Double] = {
    val train_data = csvread(new java.io.File(name))
    Utils.pairs(train_data, List("Freq", "Angle", "Chord", "Velo", "Thick", "Sound"))
    train_data
  }

  def roundUp(d: Double) = math.ceil(d).toInt

  val train_data = file_read("train.csv")
  println("Dim: " + train_data.rows + " " + train_data.cols)

  val y = train_data(0 to roundUp(train_data.rows*0.8), 5)
  val X = train_data(0 to roundUp(train_data.rows*0.8), 0 to 4)
  val X_val = train_data(roundUp(train_data.rows * 0.8) until train_data.rows, 0 to 4)
  val y_val = train_data(roundUp(train_data.rows * 0.8) until train_data.rows, 5)
  val b = DenseVector.ones[Double](5)
  val bias = 0
  val lr = 0.0001/(y.length*y.length)
  val pw = new PrintWriter(new File("mse_validation.txt" ))
  val test = new PrintWriter(new File("answer_test.txt" ))

  def loss(x:DenseMatrix[Double], y:DenseVector[Double], b:DenseVector[Double], bias:Double):Double = sqrt((DenseVector.ones[Double](y.length)*bias+x*b-y).t
    *(DenseVector.ones[Double](y.length)*bias+x*b-y))/y.length

  @scala.annotation.tailrec
  def grad_sec(x:DenseMatrix[Double], x_val:DenseMatrix[Double], y:DenseVector[Double], y_val:DenseVector[Double],
               lr:Double, ix:Int, b:DenseVector[Double], bias: Double, file: PrintWriter):(DenseVector[Double], Double) = {
    val error = y - x*b - bias
    file.write(loss(x_val, y_val, b, bias).toString+"\n")
    if (ix==0) (b, bias)
    else grad_sec(x,x_val, y, y_val, lr, ix - 1, b + lr*x.t*error, bias+lr*sum(error), file)
  }

  def run_lin_reg(x:DenseMatrix[Double], b:DenseVector[Double], bias: Double): DenseVector[Double] = {
    x*b+bias
  }

  val (res, bres) = grad_sec(X, X_val, y, y_val, lr, 20, b, bias, pw)
  pw.close
  val test_data = file_read("test.csv")
  val X_test = test_data(::, 0 to 4)
  val y_test = test_data(::, 5)
  val res_test = run_lin_reg(X_test,res, bres)
  res_test
    .foreach( vec =>
      test.write( vec.toString() + "\n" )
    )
  test.close


}