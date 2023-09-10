import org.tweetyproject.commons.Formula;
import org.tweetyproject.logics.commons.syntax.Constant;
import org.tweetyproject.logics.commons.syntax.Predicate;
import org.tweetyproject.logics.commons.syntax.Sort;
import org.tweetyproject.logics.fol.parser.FolParser;
import org.tweetyproject.logics.fol.reasoner.FolReasoner;
import org.tweetyproject.logics.fol.reasoner.SimpleFolReasoner;
import org.tweetyproject.logics.fol.syntax.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TP02 {
    public static void main(String[] args) throws IOException {

        //activation de la logique du premier ordre
         FolSignature signature = new FolSignature(true);


         //Creation des types
        Sort sportifSort = new Sort("sportif");
        Sort sportSort = new Sort("sport");


        //Creation des constantes
        Constant lyse = new Constant("lyse", Sort.ANY);
        Constant ballet = new Constant("ballet", Sort.ANY);
        signature.add(lyse, ballet);

        //Creation des prédicats
        //le predicat unaire sportif
        List<Sort> predicateList1 = new ArrayList<Sort>();
        predicateList1.add(sportifSort);
        Predicate sportif = new Predicate("sportif", predicateList1);

        //le predicat unaire sport
        List<Sort> predicateList2 = new ArrayList<Sort>();
        predicateList2.add(sportSort);
        Predicate sport = new Predicate("sport", predicateList2);

        //le predicat a deux var pratique
        List<Sort> predicateList3 = new ArrayList<Sort>();
        predicateList3.add(sportSort);
        predicateList3.add(sportifSort);
        Predicate pratique = new Predicate("pratique", predicateList3);


        signature.add(sport, sportif, pratique);
        System.out.println("les predicats sont : \n" +signature);


        //Creation des propositions
        FolParser parser = new FolParser();
        parser.setSignature(signature);
        FolBeliefSet BSet = new FolBeliefSet();

        // Exemple de création de formules
        FolFormula formula1 = (FolFormula) parser.parseFormula("forall Y:( sportif(Y) && exists X: ( pratique(X,Y) => (sport(X) ) ))");
        FolFormula formula2 = (FolFormula) parser.parseFormula("pratique(ballet, lyse)");
        FolFormula formula3 = (FolFormula)parser.parseFormula("sportif(lyse)");
        System.out.println("Formule 1 : " + formula1);
        System.out.println("Formule 2 : " + formula2);
        System.out.println("Formule 3 : " + formula3);
        BSet.add(formula1, formula2,  formula3);

        // Affichage des propositions
        System.out.println("Propositions :");
        for (Formula formula : BSet) {
            System.out.println(formula);
        }


        //evaluation

        FolReasoner.setDefaultReasoner(new SimpleFolReasoner()); //Set default prover, options are NaiveProver, EProver, Prover9
        FolReasoner prover = FolReasoner.getDefaultReasoner();
        FolBeliefSet BSet1 = new FolBeliefSet();
        BSet1.add(formula2, formula3, formula1);

        FolFormula formula4 = (FolFormula) parser.parseFormula("sport(ballet)");
        BSet1.add(formula4);

        System.out.println("-------------------------------");
        System.out.println("Formule a infrer : " + formula4);

        // Vérifier si la formule est inférée
        boolean isEntailed = prover.query(BSet1, formula4);

        if (isEntailed) {
            System.out.println("La formule est inferee.");
        } else {
            System.out.println("La formule n'est pas inferee.");
        }




    }
}
