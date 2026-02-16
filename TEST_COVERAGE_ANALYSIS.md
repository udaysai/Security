# Test Coverage Analysis

## Current State

**Test coverage: 0%.** The codebase has no tests, no testing frameworks configured, and no test directories.

### Source Files Inventory

| File | Language | Lines | Testable Logic |
|------|----------|-------|----------------|
| `python/generator.py` | Python | 53 | High |
| `scripts/Insult.scala` | Scala | 10 | Low |
| `scripts/BeginnerPortScan.scala` | Scala | 20 | Medium |
| `scripts/IntermediateBruteForce.scala` | Scala | 16 | Medium |
| `scripts/AdvancedSniffer.scala` | Scala | 18 | Low |

---

## Proposed Test Improvements

### Priority 1: Python `MarkovGenerator` (highest value)

`python/generator.py` contains the most testable, pure logic in the codebase. The `MarkovGenerator` class has two clear units:

**`_build_chain()`** - Markov chain construction:
- Test that `_START_` key is populated with first words from each line
- Test that word-to-word transitions are correctly recorded
- Test that the last word in each line maps to `_END_`
- Test that empty lines are skipped
- Test behavior with a single-word line
- Test behavior with an empty file

**`generate_insult(max_length)`** - Text generation:
- Test that output is a non-empty string (with seeded random)
- Test that `max_length` is respected
- Test the fallback message `"You're not worth insulting."` when `_START_` is empty
- Test that generation terminates when hitting `_END_`
- Test that generation terminates when a word has no chain entries

**Setup needed:**
- Add `pytest` to `requirements.txt`
- Create `python/tests/test_generator.py`
- Use `tmp_path` fixtures to create controlled data files for deterministic testing
- Seed `random` for reproducible tests

### Priority 2: Brute Force Matching Logic

`scripts/IntermediateBruteForce.scala` contains password-matching logic that could be extracted and tested:

- Extract a `checkPassword(guess: String, target: String): Boolean` function
- Test matching and non-matching cases
- Test with empty strings and edge cases

**Setup needed:**
- Add a Scala test framework (ScalaTest or munit)
- Create a `build.sbt` or use scala-cli for test execution
- Decouple the matching logic from `println` side effects

### Priority 3: Port Scanner Structure

`scripts/BeginnerPortScan.scala` has argument parsing and connection logic:

- Test default host fallback to `"localhost"` when no args provided
- Test that a provided host argument is used correctly
- Mock `Socket` connections to test open/closed port detection without network access

**Setup needed:**
- Extract connection-checking into a testable function: `def isPortOpen(host: String, port: Int, timeout: Int): Boolean`
- Use dependency injection or a trait to allow mocking the socket layer

### Priority 4: Insult.scala Shell Integration

`scripts/Insult.scala` shells out to Python. Tests here would be integration-level:

- Test that `generate()` returns a non-empty string when Python is available
- Test the fallback message when the Python command fails

### Priority 5: AdvancedSniffer (lowest priority)

This is a thin wrapper around `tcpdump`. Testing value is low since it delegates entirely to an external process. If tested:

- Verify the error message is printed when tcpdump is unavailable
- Integration test with a mock or stub for `tcpdump`

---

## Recommended First Steps

1. **Set up pytest for Python tests** - Add `pytest` to `requirements.txt`, create `python/tests/`, write tests for `MarkovGenerator`. This gives the highest coverage gain for the least effort.

2. **Set up scala-cli or sbt for Scala tests** - Pick a test framework (ScalaTest or munit), add basic project structure.

3. **Refactor Scala scripts for testability** - The current code puts all logic in `main()` methods with side effects (printing, network I/O, shell commands). Extract pure functions that can be unit tested independently.

## Structural Recommendations

- **Separate pure logic from side effects.** Every Scala `main()` method mixes computation with I/O. Extracting functions like `isPortOpen()`, `checkPassword()`, and `buildCommand()` would make the code testable without mocking.
- **Add CI configuration.** Even a simple GitHub Actions workflow running `pytest` on push would prevent regressions as the project grows.
- **Use dependency injection for external calls.** `Insult.generate()` shells out to Python. Wrapping this behind a trait would let Scala tests run without requiring Python.
