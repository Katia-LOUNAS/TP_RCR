package be.fnord.DefaultLogic;


import be.fnord.util.logic.DefaultReasoner;
import be.fnord.util.logic.WFF;
import be.fnord.util.logic.defaultLogic.DefaultRule;
import be.fnord.util.logic.defaultLogic.RuleSet;
import be.fnord.util.logic.defaultLogic.WorldSet;

import java.util.HashSet;

public class DesExemple {
    public static void main(String[] args) {
        // Ajout du monde initial
        WorldSet myWorld = new WorldSet();
        myWorld.addFormula("TheoryOfEvolution");


        // Ajout des règles de défauts
        DefaultRule rule1 = new DefaultRule();
        rule1.setPrerequisite("TheoryOfEvolution");
        rule1.setJustificatoin(("GeneticSimilarity"));
        rule1.setConsequence("TheoryOfEvolutionLikely");

        DefaultRule rule2 = new DefaultRule();
        rule2.setPrerequisite("TheoryOfEvolution");
        rule2.setJustificatoin(("IntermediateForms"));
        rule2.setConsequence("TheoryOfEvolutionLikely");

        RuleSet myRules = new RuleSet();
        myRules.addRule(rule1);
        myRules.addRule(rule2);

        // Raisonnement sur les extensions possibles
        DefaultReasoner reasoner = new DefaultReasoner(myWorld, myRules);
        HashSet<String> extensions = reasoner.getPossibleScenarios();

        System.out.println("Given the world: \n" + myWorld.toString());
        System.out.println("And the rules:\n" + myRules.toString());
        System.out.println("Possible Extensions:");

        for (String extension : extensions) {
            System.out.println("\tExt: " + extension);
        }
    }
}
