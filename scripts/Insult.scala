import scala.sys.process._
import scala.util.Try

object Insult {
  def generate(): String = {
    val command = "python3 python/generator.py"
    Try(command.!!).map(_.trim).getOrElse("You're so bad, you broke the insult generator.")
  }
}
