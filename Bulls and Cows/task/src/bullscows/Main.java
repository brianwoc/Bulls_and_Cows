package bullscows;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static Random random = new Random();
    static int cows = 0;
    static int bulls = 0;
    static int turn = 0;
    static boolean isNotWinner = true;
    static String correctCode = "";


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the length of the secret code:");
        String lengthNumberInput = scanner.nextLine();
        isNumeric(lengthNumberInput);
        int lengthNumber = Integer.parseInt(lengthNumberInput);

        System.out.println("Input the number of possible symbols in the code:");
        String numberOfPossibleSymbolsInput = scanner.nextLine();
        isNumeric(numberOfPossibleSymbolsInput);
        int numberOfPossibleSymbols = Integer.parseInt(numberOfPossibleSymbolsInput);
        if (numberOfPossibleSymbols < lengthNumber) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.\n",lengthNumber,numberOfPossibleSymbols);
            System.exit(-1);
        }
        if (lengthNumber==0){
            System.out.println("Error:");
            System.exit(-1);

        }

        correctCode = getNumber(lengthNumber, numberOfPossibleSymbols);
        printCode(numberOfPossibleSymbols);

        System.out.println("Okay, let's start a game!");

        while (isNotWinner) {
            System.out.printf("Turn %d:", turn);
            System.out.println();
            String number = scanner.nextLine();

            getGrade(number, correctCode);
            getAnswer();

            System.out.println();
            if (bulls == number.length()) {
                System.out.println("Congratulations! You guessed the secret code.");
                isNotWinner = false;
            }
            turn++;
            bulls = 0;
            cows = 0;
        }
    }

    private static void printCode(int number) {
        String stars = "";
        for (int i = 0; i < number; i++) {
            stars += "*";
        }
        if (number < 11) {
            System.out.printf("The secret is prepared: %s (0-%d).\n", stars, number - 1);
        } else {
            char ch = (char) (number + 86);
            System.out.printf("The secret is prepared: %s (0-9, a-%c).\n", stars, ch);
        }
    }

    private static String getNumber(int lengthNumber, int numberOfPossiblesSymbols) {
        Random random = new Random();
        List<String> lista = new ArrayList<>();
        for (int i = 0; i < numberOfPossiblesSymbols; i++) {
            if (i < 10) {
                lista.add(String.valueOf(i));
            } else if (i > 9 && i < 36) {
                char ch = (char) (87 + i);
                lista.add(String.valueOf(ch));
            } else {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                System.exit(-1);
            }
        }

        String createdValue = "";
        while (lengthNumber != createdValue.length()) {
            String newChar = lista.get(random.nextInt(lista.size()));
            if (!createdValue.contains(newChar)) {
                createdValue += newChar;
            }
        }


        return createdValue;

    }

    private static void getAnswer() {
        if (bulls == 0 && cows == 0) {
            System.out.printf("Grade: None.");
        } else if (bulls == 1 && cows == 0) {
            System.out.printf("Grade: %d bull", bulls);
        } else if (bulls > 1 && cows == 0) {
            System.out.printf("Grade: %d bulls", bulls);
        } else if (cows > 1 && bulls == 0) {
            System.out.printf("Grade: %d cows.", cows);
        } else if (cows == 1 && bulls == 0) {
            System.out.printf("Grade: %d cow.", cows);
        } else if (cows == 1 && bulls == 1) {
            System.out.printf("Grade: %d bull and %d cow.", bulls, cows);
        } else {
            System.out.printf("Grade: %d bulls and %d cows.", bulls, cows);
        }
    }

    private static void getGrade(String number, String correctCode) {

        for (int i = 0; i < number.length(); i++) {
            for (int j = 0; j < correctCode.length(); j++) {
                if (i == j) {
                    if (number.charAt(i) == correctCode.charAt(j)) {
                        bulls++;
                    }
                } else {
                    if (number.charAt(i) == correctCode.charAt(j)) {
                        cows++;
                    }
                }
            }
        }
    }

    public static void isNumeric(String number) {
        try {
            Integer.parseInt(number);
        } catch (NumberFormatException e) {
            System.out.printf("Error: \"%s\" isn't a valid number.",number);
            System.exit(-1);
        }
    }
}
