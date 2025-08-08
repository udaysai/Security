import java.net.{InetSocketAddress, Socket}
import scala.util.Try

object BeginnerPortScan {
  def main(args: Array[String]): Unit = {
    val host = if (args.nonEmpty) args(0) else "localhost"
    val ports = 1 to 1024

    println(s"Scanning $host...")
    for (port <- ports) {
      Try {
        val socket = new Socket()
        socket.connect(new InetSocketAddress(host, port), 200)
        socket.close()
        println(s"Port $port open, ${Insult.generate()}!")
      }
    }
  }
}
