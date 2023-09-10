import org.tweetyproject.commons.Formula;
import org.tweetyproject.logics.commons.syntax.Constant;
import org.tweetyproject.logics.commons.syntax.Predicate;
import org.tweetyproject.logics.commons.syntax.Sort;
import org.tweetyproject.logics.fol.parser.FolParser;
import org.tweetyproject.logics.fol.reasoner.FolReasoner;
import org.tweetyproject.logics.fol.reasoner.SimpleFolReasoner;
import org.tweetyproject.logics.fol.syntax.FolBeliefSet;
import org.tweetyproject.logics.fol.syntax.FolFormula;
import org.tweetyproject.logics.fol.syntax.FolSignature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MorExTP02 {
    public static void main(String[] args) throws IOException {
        // Activation de la logique du premier ordre
        FolSignature signature = new FolSignature(true);

        // Création des types
        Sort planteSort = new Sort("Plante");
        Sort feuilleSort = new Sort("Feuille");
        Sort ensoleilleSort = new Sort("Ensoleille");

        // Création des constantes
        Constant a = new Constant("a", Sort.ANY);
        Constant tulipe = new Constant("Tulipe", Sort.ANY);
        signature.add(a, tulipe);

        // Création des prédicats
        Predicate plante = new Predicate("Plante", Collections.singletonList(planteSort));
        Predicate feuille = new Predicate("Feuille", Collections.singletonList(feuilleSort));
        Predicate ensoleille = new Predicate("Ensoleille", Collections.singletonList(ensoleilleSort));

        signature.add(plante, feuille, ensoleille);
        System.out.println("Les prédicats sont : \n" + signature);

        // Création des propositions
        FolParser parser = new FolParser();
        parser.setSignature(signature);
        FolBeliefSet beliefSet = new FolBeliefSet();

        // Exemple de création de formules
        FolFormula formula1 = (FolFormula) parser.parseFormula("forall X: (Plante(X) => exists Y: (Feuille(X) && forall Z: (Feuille(Z) => ==(Z, X))))");
        FolFormula formula2 = (FolFormula) parser.parseFormula("exists X: (Plante(X) && exists Y: (Feuille(Y) && Ensoleille(X)))");
        FolFormula formula3 = (FolFormula) parser.parseFormula("exists X: (Plante(X) && forall Y: (Feuille(Y) => !Ensoleille(X)))");
        System.out.println("Formule 1 : " + formula1);
        System.out.println("Formule 2 : " + formula2);
        System.out.println("Formule 3 : " + formula3);
        beliefSet.add(formula1, formula2, formula3);

        // Affichage des propositions
        System.out.println("Propositions :");
        for (Formula formula : beliefSet) {
            System.out.println(formula);
        }

        // Évaluation
        FolReasoner.setDefaultReasoner(new SimpleFolReasoner()); // Définition du raisonneur par défaut, options : NaiveProver, EProver, Prover9
        FolReasoner prover = FolReasoner.getDefaultReasoner();
        FolBeliefSet beliefSet1 = new FolBeliefSet();

        FolFormula formula4 = (FolFormula) parser.parseFormula("!Plante(katia)");
        FolFormula formula5 = (FolFormula) parser.parseFormula("Plante(lyse)");
        beliefSet1.add(formula1, formula2, formula3, formula4, formula5);

        FolFormula formula6 = (FolFormula) parser.parseFormula("exists X: (Plante(X) && !Feuille(X))");
        beliefSet1.add(formula6);
        System.out.println("-------------------------------");
        System.out.println("Formule à inférer : " + formula6);

        // Vérifier si la formule est inférée
        boolean isEntailed = prover.query(beliefSet1, formula6);

        if (isEntailed) {
            System.out.println("La formule est inférée.");
        } else {
            System.out.println("La formule n'est pas inférée.");
        }

        // Une autre formule à évaluer
        FolFormula formula7 = (FolFormula) parser.parseFormula("forall X: (Plante(X) => exists Y: (Plante(Y) && Feuille(X)))");
        beliefSet1.add(formula7);

        System.out.println("-------------------------------");
        System.out.println("Formule à évaluer : " + formula7);

        // Vérifier si la formule est inférée
        isEntailed = prover.query(beliefSet1, formula7);

        if (isEntailed) {
            System.out.println("La formule est inférée.");
        } else {
            System.out.println("La formule n'est pas inférée.");
        }
    }
}

