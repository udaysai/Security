object IntermediateBruteForce {
  def main(args: Array[String]): Unit = {
    val target = "hunter2"
    val guesses = List("123456", "password", "letmein", "hunter2", "trustno1")

    for (guess <- guesses) {
      if (guess == target) {
        println(s"Password cracked: '$guess', ${Insult.generate()}!")
        return
      } else {
        println(s"Tried '$guess' - no luck, ${Insult.generate()}!")
      }
    }
  }
}
