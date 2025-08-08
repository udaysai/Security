import scala.sys.process._
import scala.util.Try

object AdvancedSniffer {
  def main(args: Array[String]): Unit = {
    val cmd = Seq("tcpdump", "-l", "-n", "-c", "5")
    println("Starting tcpdump...")

    val result = Try {
      cmd.lineStream.foreach(line => println(s"$line -- ${Insult.generate()}"))
    }

    result.failed.foreach { _ =>
      println("tcpdump failed to run. Install it or run with sufficient privileges.")
    }
  }
}
