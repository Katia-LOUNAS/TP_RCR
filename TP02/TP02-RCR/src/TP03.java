import org.tweetyproject.commons.ParserException;
import org.tweetyproject.logics.commons.syntax.Predicate;
import org.tweetyproject.logics.commons.syntax.RelationalFormula;
import org.tweetyproject.logics.fol.syntax.FolFormula;
import org.tweetyproject.logics.fol.syntax.FolSignature;
import org.tweetyproject.logics.ml.parser.MlParser;
import org.tweetyproject.logics.ml.reasoner.SimpleMlReasoner;
import org.tweetyproject.logics.ml.syntax.MlBeliefSet;

import java.io.IOException;

public class TP03 {
    public static void main(String[] args) throws ParserException, IOException {
        // Create a new modal belief set
        MlBeliefSet beliefSet = new MlBeliefSet();

        // Create a parser to parse formulas
        MlParser parser = new MlParser();

        // Define the signature with predicates
        FolSignature signature = new FolSignature();
        signature.add(new Predicate("p", 0));
        signature.add(new Predicate("q", 0));
        parser.setSignature(signature);

        // Parse and add formulas to the belief set
        beliefSet.add((RelationalFormula) parser.parseFormula("<>(p || q)"));
        beliefSet.add((RelationalFormula) parser.parseFormula("[](!p && q)"));
        beliefSet.add((RelationalFormula) parser.parseFormula("[](!q || p)"));

        // Create a SimpleMlReasoner
        SimpleMlReasoner reasoner = new SimpleMlReasoner();

        // Query the truth value of formulas in the belief set
        boolean isPAndQTrue = reasoner.query(beliefSet, (FolFormula) parser.parseFormula("<>(p && q)"));
        boolean isNotPAndQTrue = reasoner.query(beliefSet, (FolFormula) parser.parseFormula("[](!p && q)"));
        boolean isNotQOrPTrue = reasoner.query(beliefSet, (FolFormula) parser.parseFormula("[](!q || p)"));
        boolean isNotPAndNotQTrue = reasoner.query(beliefSet, (FolFormula) parser.parseFormula("[](!p && !q)"));

        // Print the results
        System.out.println("Modal belief set: " + beliefSet);
        System.out.println("<>(p && q) is " + isPAndQTrue);
        System.out.println("[](!p && q) is " + isNotPAndQTrue);
        System.out.println("[](!q || p) is " + isNotQOrPTrue);
        System.out.println("[](!p && !q) is " + isNotPAndNotQTrue);
    }
}