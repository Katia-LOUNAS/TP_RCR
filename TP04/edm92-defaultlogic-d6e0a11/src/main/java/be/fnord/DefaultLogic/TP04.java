package be.fnord.DefaultLogic;

import be.fnord.util.logic.DefaultReasoner;
import be.fnord.util.logic.WFF;
import be.fnord.util.logic.defaultLogic.DefaultRule;
import be.fnord.util.logic.defaultLogic.RuleSet;
import be.fnord.util.logic.defaultLogic.WorldSet;

import java.util.HashSet;

public class TP04 {

    public static void example1() {
        WorldSet myWorld = new WorldSet();
        // Start by believing that tweety is a bird
        myWorld.addFormula("Kamel_est_agriculteur"); // We think that x is a bird
        a.e.println("We one day learn that Kamel_est_agriculteur_bio");
        // Learn one day that tweety is a penguin
        myWorld.addFormula("Kamel_est_agriculteur_bio");
        // Learn that although they like to think they can fly
        // penguins in fact can not fly
        myWorld.addFormula("(Kamel_est_agriculteur" + a.e.IMPLIES + " " + a.e.NOT + "utilise_pesticides)");


        DefaultRule rule1 = new DefaultRule();
        rule1.setPrerequisite("Kamel_est_agriculteur");
        rule1.setJustificatoin("utilise_pesticides");
        rule1.setConsequence("utilise_pesticides");

        DefaultRule rule2 = new DefaultRule();
        rule2.setPrerequisite("Kamel_est_agriculteur");
        rule2.setJustificatoin("Kamel_est_agriculteur_bio");
        rule2.setConsequence(a.e.NOT + "utilise_pesticides");

        RuleSet myRules = new RuleSet();
        myRules.addRule(rule1);
        myRules.addRule(rule2);

        DefaultReasoner loader = new DefaultReasoner(myWorld, myRules);
        HashSet<String> extensions = loader.getPossibleScenarios();

        a.e.println("Given the world: \n\t" + myWorld.toString()
                + "\n And the rules \n\t" + myRules.toString());

        a.e.println("Possible Extensions");
        for (String c : extensions) {
            a.e.println("\t Ext: Th(W U (" + c + "))");
            // Added closure operator
            a.e.incIndent();
            WFF world_and_ext = new WFF("(( " + myWorld.getWorld() + " ) & ("
                    + c + "))");
            a.e.println("= " + world_and_ext.getClosure());
            a.e.decIndent();

        }

    }

    public static void example2() {
        //Ajout du monde
        WorldSet myWorld = new WorldSet(); // Empty World
        myWorld.addFormula("Kamel_est_agriculteur");

        DefaultRule rule1 = new DefaultRule();
        rule1.setPrerequisite("Kamel_est_agriculteur");
        rule1.setJustificatoin("utilise_pesticides");
        rule1.setConsequence("utilise_pesticides");
//
//        DefaultRule rule2 = new DefaultRule();
//        rule2.setPrerequisite("Kamel_est_agricuteur_bio");
//        rule2.setJustificatoin(a.e.NOT + "utilise_pesticides");
//        rule2.setConsequence(a.e.NOT + "utilise_pesticides");

        RuleSet myRules = new RuleSet();
        myRules.addRule(rule1);
//		myRules.addRule(rule2);

        DefaultReasoner loader = new DefaultReasoner(myWorld, myRules);
        HashSet<String> extensions = loader.getPossibleScenarios();

        a.e.println("Given the world: \n\t" + myWorld.toString()
                + "\n And the rules \n\t" + myRules.toString());

        a.e.println("Possible Extensions");
        for (String c : extensions) {
            a.e.println("\t Ext: Th(W U (" + c + "))");
            // Added closure operator
            a.e.incIndent();
            WFF world_and_ext = new WFF("(( " + myWorld.getWorld() + " ) & ("
                    + c + "))");
            a.e.println("= " + world_and_ext.getClosure());
            a.e.decIndent();

        }


    }

    public static void example3(){ // Définition du monde initial
        WorldSet myWorld = new WorldSet();
        myWorld.addFormula("((Symptom1) & (Symptom1 -> Disease))");

        // Définition des règles de défauts
        DefaultRule rule1 = new DefaultRule();
        rule1.setPrerequisite("Symptom1");
        rule1.setJustificatoin("Disease");
        rule1.setConsequence("~Symptom1");

        DefaultRule rule2 = new DefaultRule();
        rule2.setPrerequisite("Disease");
        rule2.setJustificatoin("Treatment");
        rule2.setConsequence("~Disease");

        RuleSet myRules = new RuleSet();
        myRules.addRule(rule1);
        myRules.addRule(rule2);

        DefaultReasoner loader = new DefaultReasoner(myWorld, myRules);
        HashSet<String> extensions = loader.getPossibleScenarios();

        a.e.println("Given the world: \n\t" + myWorld.toString()
                + "\n And the rules \n\t" + myRules.toString());

        a.e.println("Possible Extensions");
        for (String c : extensions) {
            a.e.println("\t Ext: Th(W U (" + c + "))");
            // Added closure operator
            a.e.incIndent();
            WFF world_and_ext = new WFF("(( " + myWorld.getWorld() + " ) & ("
                    + c + "))");
            a.e.println("= " + world_and_ext.getClosure());
            a.e.decIndent();

        }
    }

//    Croyance 1 : "Si la théorie de l'évolution est correcte, on devrait observer une similarité génétique entre des espèces étroitement apparentées."
//    Croyance 2 : "Si la théorie de l'évolution est correcte, on devrait trouver des fossiles montrant des formes de vie intermédiaires entre des espèces."

    public static void example4() {
        // Ajout du monde initial
        WorldSet myWorld = new WorldSet();
        myWorld.addFormula("TheoryOfEvolution");

        // Ajout des règles de défauts
        DefaultRule rule1 = new DefaultRule();
        rule1.setPrerequisite("TheoryOfEvolution");
        rule1.setJustificatoin("GeneticSimilarity");
        rule1.setConsequence("TheoryOfEvolutionLikely");

        DefaultRule rule2 = new DefaultRule();
        rule2.setPrerequisite("TheoryOfEvolution");
        rule2.setJustificatoin("IntermediateForms");
        rule2.setConsequence("TheoryOfEvolutionLikely");

        RuleSet myRules = new RuleSet();
        myRules.addRule(rule1);
        myRules.addRule(rule2);

        // Raisonnement sur les extensions possibles
        DefaultReasoner loader = new DefaultReasoner(myWorld, myRules);
        HashSet<String> extensions = loader.getPossibleScenarios();

        a.e.println("Given the world: \n\t" + myWorld.toString()
                + "\n And the rules \n\t" + myRules.toString());

        a.e.println("Possible Extensions");
        for (String c : extensions) {
            a.e.println("\t Ext: Th(W U (" + c + "))");
            // Added closure operator
            a.e.incIndent();
            WFF world_and_ext = new WFF("(( " + myWorld.getWorld() + " ) & ("
                    + c + "))");
            a.e.println("= " + world_and_ext.getClosure());
            a.e.decIndent();

        }
    }

    public static void main(String [] args){
        // Load example
        // Turn on the removal of empty effects from print statements
        a.e.HIDE_EMPTY_EFFECTS_IN_PRINT = true;

        example2();
        example1();

    }
}
