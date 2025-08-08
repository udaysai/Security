import scala.util.Random

object Insult {
  private val adjectives = Vector(
    "clueless",
    "pathetic",
    "inept",
    "bungling",
    "witless",
    "gullible"
  )

  private val nouns = Vector(
    "script-kiddie",
    "newbie",
    "luser",
    "greenhorn",
    "rookie",
    "buffoon"
  )

  private val rng = new Random()

  def generate(): String = {
    val adj = adjectives(rng.nextInt(adjectives.length))
    val noun = nouns(rng.nextInt(nouns.length))
    s"$adj $noun"
  }
}
