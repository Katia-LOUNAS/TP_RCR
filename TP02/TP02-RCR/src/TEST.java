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
import java.util.List;

public class TEST {
    public static void main(String[] args) throws IOException {
        FolSignature sig = new FolSignature(true);

        //Add sort
        Sort sortLivre = new Sort("Livre");
        sig.add(sortLivre);
        Sort sortAuteur = new Sort("Auteur");
        sig.add(sortAuteur);

        //Add constants
        Constant vh = new Constant("victor-hugo",sortAuteur);
        Constant ms = new Constant("les-miserables",sortLivre);
        sig.add(vh, ms);

        //Add predicates
        List<Sort> predicateList = new ArrayList<Sort>();
        predicateList.add(sortLivre);
        Predicate p = new Predicate("Livre",predicateList);
        List<Sort> predicateList2 = new ArrayList<Sort>();
        predicateList2.add(sortAuteur);
        predicateList2.add(sortLivre);
        Predicate p2 = new Predicate("Aecrit",predicateList2); //Add Predicate a ecrit(auteur,livre)
        List<Sort> auteurList = new ArrayList<Sort>();
        auteurList.add(sortAuteur);
        Predicate p3 = new Predicate("Auteur", auteurList);
        sig.add(p, p2,p3);
        System.out.println("Signature: " + sig);

        /*
         * Example 2: Parse formulas with FolParser using the signature defined above
         */
        FolParser parser = new FolParser();
        parser.setSignature(sig); //Use the signature defined above
        FolBeliefSet bs = new FolBeliefSet();
//        (forall Z: (Aecrit(Z,X) => Auteur(Z)))
        FolFormula f1 = (FolFormula)parser.parseFormula("forall X: (Livre(X) => exists Y: (Auteur(Y) && Aecrit(Y,X) && forall Z: (Aecrit(Z,X) => Auteur(Z))))");
        FolFormula f2 = (FolFormula)parser.parseFormula("Livre(les-miserables)");
        FolFormula f3 = (FolFormula)parser.parseFormula("Aecrit(victor-hugo, les-miserables)");
        bs.add(f1, f2, f3);
        System.out.println("\nParsed BeliefBase: " + bs);


        /*
         * Example 3: Use one of the provers to check whether various formulas can be inferred from the knowledge base parsed in Example 2.
         */
        FolReasoner.setDefaultReasoner(new SimpleFolReasoner()); //Set default prover, options are NaiveProver, EProver, Prover9
        FolReasoner prover = FolReasoner.getDefaultReasoner();
        System.out.println("ANSWER 1: " + prover.query(bs, (FolFormula)parser.parseFormula("Auteur(victor-hugo)")));

    }


}
