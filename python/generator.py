import random

class MarkovGenerator:
    def __init__(self, data_file):
        self.data_file = data_file
        self.chain = self._build_chain()

    def _build_chain(self):
        chain = {'_START_': []}
        with open(self.data_file, 'r') as f:
            for line in f:
                words = line.strip().split()
                if not words:
                    continue

                # Add first word to start list
                chain['_START_'].append(words[0])

                # Add word pairs to chain
                for i in range(len(words)):
                    word = words[i]
                    if word not in chain:
                        chain[word] = []

                    if i + 1 < len(words):
                        chain[word].append(words[i+1])
                    else:
                        chain[word].append('_END_')
        return chain

    def generate_insult(self, max_length=10):
        if not self.chain['_START_']:
            return "You're not worth insulting."

        word = random.choice(self.chain['_START_'])
        insult = [word]

        while len(insult) < max_length:
            # Check if the current word is in the chain
            if word not in self.chain or not self.chain[word]:
                break
            next_word = random.choice(self.chain[word])
            if next_word == '_END_':
                break
            insult.append(next_word)
            word = next_word

        return ' '.join(insult)

if __name__ == '__main__':
    generator = MarkovGenerator('python/insults.txt')
    print(generator.generate_insult())
