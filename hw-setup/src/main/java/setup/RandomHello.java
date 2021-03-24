package setup;

import java.util.Random;

/** RandomHello selects and prints a random greeting. */
public class RandomHello {

    /**
     * Prints a random greeting to the console.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        RandomHello randomHello = new RandomHello();
        System.out.println(randomHello.getGreeting());
    }

    /**
     *
     */
    public  String getGreeting() {
        Random randomGenerator = new Random();
        int n = randomGenerator.nextInt(5 );
        String[] greetings = new String[5];
        greetings[0] = "Hello World";
        greetings[1] = "Hola Mundo";
        greetings[2] = "Bonjour Monde";
        greetings[3] = "Hallo Welt";
        greetings[4] = "Ciao Mondo";
        return greetings[n];

    }
}