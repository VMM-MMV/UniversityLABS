from collections import defaultdict

class Grammar:
    def __init__(self, grammar):
        self.parsed_grammar = self.parseGrammar(grammar)
        self.start_symbol = "Add a start symbol"
        self.end_symbols = []

    def addEndSymbols(self, end_symbol):
        if isinstance(end_symbol, list):
            self.end_symbols.extend(end_symbol)
        else:
            self.end_symbols.append(end_symbol)
    
    def setStartSybol(self, start_symbol):
        self.start_symbol = start_symbol

    def parseGrammar(self, grammar):
        productions = defaultdict(list)
        for line in grammar.split('\n'):
            if line.strip():
                non_terminal, production = line.split('→')
                productions[non_terminal.strip()].append(production.strip())

        return productions
        
    def getStrings(self):
        result_strs = set()
        size = 5
        def iter(grammar_str, result_str, visited, NT):
            if len(result_strs) == size:
                return
            
            for chars in grammar_str:
                for char in chars:
                    if self.parsed_grammar.get(char):
                        visited.setdefault(char, 0)
                        visited[char] += 1
                        if visited[char] < 4: 
                            iter(self.parsed_grammar[char], result_str.copy(), visited.copy(), char)
                        if result_str:
                            result_str.pop()
                    else:
                        result_str.append(char)
            if len(result_strs) == size:
                return
            if NT in self.end_symbols:
                result_strs.add("".join(result_str))
                
        iter(self.parsed_grammar[self.start_symbol], [], {}, self.start_symbol)
        return result_strs

    def printGrammarSet(self):
        for non_terminal, productions in self.parsed_grammar.items():
            print(f"{non_terminal}: {productions}")

def main():
    grammar = """
    S → aA     
    A → bS    
    A → aB   
    B → bC    
    C → aA   
    B → aB     
    C → b
    """
    grammar = Grammar(grammar)
    grammar.printGrammarSet()
    allStrings = grammar.getStrings()
    print(allStrings)

if __name__ == "__main__":
    main()