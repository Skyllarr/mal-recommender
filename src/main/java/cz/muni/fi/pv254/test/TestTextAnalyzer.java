package cz.muni.fi.pv254.test;

import cz.muni.fi.pv254.algorithms.TextAnalyzer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suomiy on 24.11.15.
 */
public class TestTextAnalyzer {

    public static void run() {
        List<String> corpus = new ArrayList<>();
        corpus.add("The year 2071 A.D.\n" +
                "            That future is now. Driven out of their terrestrial eden,\n" +
                "            humanity chose the stars as the final frontier. With the\n" +
                "            section-by-section collapse of the former nations a mixed\n" +
                "            jumble of races and peoples came. They spread to the stars,\n" +
                "            taking with them the now confused concepts of freedom,\n" +
                "            violence, illegality and love, where new rules and a new\n" +
                "            generation of outlaws came into being. People referred to\n" +
                "            them as Cowboys.\n" +
                "            \n" +
                "            Meet Spike and Jet, a drifter and a retired cyborg cop who\n" +
                "            have started a bounty hunting operation. In the converted\n" +
                "            ship The Bebop, Spike and Jet search the galaxy for\n" +
                "            criminals with bounties on their heads. They meet a lot of\n" +
                "            unusual characters, including the unusually intelligent\n" +
                "            dog, Ein, and the voluptuous and vexing femme fatale, Faye\n" +
                "            Valentine.\n" +
                "            \n" +
                "            (Source: Crunchyroll)");
        corpus.add("As the Cowboy Bebop\n" +
                "            crew travels the stars, they learn of the largest bounty\n" +
                "            yet, a huge 300 million Woolongs. Apparently, someone is\n" +
                "            wielding a hugely powerful chemical weapon, and of course\n" +
                "            the authorities are at a loss to stop it. The war to take\n" +
                "            down the most dangerous criminal yet forces the crew to\n" +
                "            face a true madman, with bare hope to succeed.\n" +
                "            \n" +
                "            (Source: ANN) ");
        corpus.add("Echizen Ryoma is a\n" +
                "            young tennis prodigy who has won 4 consecutive tennis\n" +
                "            championships but who constantly lies in the shadow of his\n" +
                "            father, a former pro tennis player. He joins the Seishun\n" +
                "            Gakuen junior highschool, one of the best tennis schools in\n" +
                "            Japan, and there along with his teamates he learns to find\n" +
                "            his own type of tennis in an attempt to defeat his biggest\n" +
                "            obstacle of all: his father as well as himself. \n" +
                "            \n" +
                "            (Source: ANN)");

        TextAnalyzer textAnalyzer = new TextAnalyzer(corpus);
        textAnalyzer.computeTfIdfSimilarity(1);
    }
}
