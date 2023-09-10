from pysat.solvers import Glucose3

def read_cnf(file_path):
    clauses = []
    with open(file_path, 'r') as file:
        for line in file:
            if line.startswith('c') or line.startswith('p'):
                continue
            clause = [int(literal) for literal in line.strip().split() if literal != '0']
            clauses.append(clause)
    return clauses

def inference_BC(BC, phi):
    solver = Glucose3()  # Créer un solveur SAT
    
    # Ajouter BC à la base de connaissances
    for clause in BC:
        solver.add_clause(clause)
    
    # Ajouter la négation de phi au solveur SAT
    solver.add_clause([-phi])
    
    # Vérifier la satisfiabilité de la base de connaissances étendue
    if solver.solve():
        # BC n'infère pas phi
        return False
    else:
        # BC infère phi
        return True

# Exemple d'utilisation
file_path = "test1.cnf" # Chemin vers le fichier CNF
BC = read_cnf(file_path)
#BC = [[1, -2], [-2, -3], [3]]  # Exemple de base de connaissances (BC)
print("la base de fait est la suivante :")
print(BC)
phi = 3  # Exemple de formule (phi)
print("le litéral est:")
print(phi)

result = inference_BC(BC, -phi)  # Négation de phi
if result:
    print("BC infère phi.")
else:
    print("BC n'infère pas phi.")
