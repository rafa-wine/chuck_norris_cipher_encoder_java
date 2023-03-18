package chucknorris;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;

        do {
            System.out.println("Please input operation (encode/decode/exit):");
            String option = scanner.nextLine();
            String input;

            switch (option) {
                case "encode":
                    System.out.println("Input string:");
                    input = scanner.nextLine();
                    System.out.println("Encoded string:");
                    System.out.printf("%s%n%n", toChuckNorris(toBinary(input)));
                    break;
                case "decode":
                    System.out.println("Input encoded string:");
                    input = scanner.nextLine();
                    String decode = decodeBinary(decodeChuckNorris(input));
                    System.out.println("".equals(decode) ? "Encoded string is not valid.\n" :
                            String.format("Decoded string:%n%s%n", decode) );
                    break;
                case "exit":
                    System.out.println("Bye!");
                    exit = true;
                    break;
                default:
                    System.out.printf("There is no '%s' operation%n", option);
            }
        } while (!exit);
    }

    public static String toBinary(String text) {
        String binary = "";
        for (int i = 0; i < text.length(); i++) {
            binary += String.format("%7s", Integer.toBinaryString(text.charAt(i))).replace(" ", "0");
        }
        return binary;
    }

    public static String toChuckNorris(String text) {
        String chuckNorris = "";

        int countOnes = 0;
        int countZeros = 0;

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '1') {
                countZeros = 0;
                countOnes++;
                chuckNorris += countOnes != 1 ? "0" : " 0 0";
            }
            if (text.charAt(i) == '0') {
                countOnes = 0;
                countZeros++;
                chuckNorris += countZeros != 1 ? "0" : " 00 0";
            }
        }

        return chuckNorris.trim();
    }

    public static String decodeChuckNorris(String string) {
        String[] codific = string.split(" ");
        String decoded = "";
        boolean isOne = false;

        if (string.matches("^[0\\s]*$")) {
            for (int i = 0; i < codific.length; i++) {
                if (i % 2 == 0) {
                    if (codific[i].length() > 2) {
                        decoded = "";
                        break;
                    } else {
                        isOne = codific[i].length() == 1 ? true : false;

                    }
                } else {
                    for (int j = 0; j < codific[i].length(); j++) {
                        decoded += isOne ? "1" : "0";
                    }
                }
            }
        } else {
            decoded = "";
        }

        return decoded;
    }

    public static String decodeBinary(String string) {
        String text = "";

        if (string.length() % 7 == 0) {
            String[] binariesBlocks = new String[string.length() / 7];
            String binaryBlock = "";
            int position = 0;

            for (int i = 0; i < string.length(); i++) {
                binaryBlock += string.charAt(i);
                if ((i + 1) % 7 == 0) {
                    binariesBlocks[position] = binaryBlock;
                    binaryBlock = "";
                    position++;
                }
            }

            for (String block : binariesBlocks) {
                int letter = 0;
                for (int i = 0; i < block.length(); i++) {
                    if (block.charAt(i) == '1') {
                        letter += Math.pow(2, (7 - (1 + i)));
                    }
                }
                text += (char) letter;
            }
        }

        return text;
    }

}