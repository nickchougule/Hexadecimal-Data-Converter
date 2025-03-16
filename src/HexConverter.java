import java.io.*;
import java.util.Scanner;

public class HexConverter {

    // 🔹 Conversion Methods
    public static String binaryToHex(String binary) {
        int decimal = Integer.parseInt(binary, 2);
        return Integer.toHexString(decimal).toUpperCase();
    }

    public static String binaryToDecimal(String binary) {
        return String.valueOf(Integer.parseInt(binary, 2));
    }

    public static String binaryToASCII(String binary) {
        int decimal = Integer.parseInt(binary, 2);
        return Character.toString((char) decimal);
    }

    public static String decimalToBinary(String decimal) {
        return Integer.toBinaryString(Integer.parseInt(decimal));
    }

    public static String decimalToHex(String decimal) {
        return Integer.toHexString(Integer.parseInt(decimal)).toUpperCase();
    }

    public static String decimalToASCII(String decimal) {
        return Character.toString((char) Integer.parseInt(decimal));
    }

    public static String hexToBinary(String hex) {
        int decimal = Integer.parseInt(hex, 16);
        return Integer.toBinaryString(decimal);
    }

    public static String hexToDecimal(String hex) {
        return String.valueOf(Integer.parseInt(hex, 16));
    }

    public static String hexToASCII(String hex) {
        int decimal = Integer.parseInt(hex, 16);
        return Character.toString((char) decimal);
    }

    public static String asciiToBinary(char ascii) {
        return Integer.toBinaryString((int) ascii);
    }

    public static String asciiToDecimal(char ascii) {
        return String.valueOf((int) ascii);
    }

    public static String asciiToHex(char ascii) {
        return Integer.toHexString((int) ascii).toUpperCase();
    }

    // 🔹 Checksum Methods
    public static int generateChecksum(String data) {
        int checksum = 0;
        for (char c : data.toCharArray()) {
            checksum ^= c;  // XOR operation
        }
        return checksum;
    }

    public static boolean verifyChecksum(String data, int expectedChecksum) {
        return generateChecksum(data) == expectedChecksum;
    }

    // 🔹 File Processing Method
    public static void processFile(String inputFile, String outputFile, int conversionChoice) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String convertedValue = convert(line.trim(), conversionChoice);
                int checksum = generateChecksum(convertedValue);

                // Write converted value + checksum to output file
                writer.write(convertedValue + " | Checksum: " + checksum);
                writer.newLine();
            }

            System.out.println("✅ Batch processing completed. Output saved to: " + outputFile);

        } catch (IOException e) {
            System.out.println("❌ Error processing file: " + e.getMessage());
        }
    }

    private static String convert(String input, int choice) {
        switch (choice) {
            case 1: return binaryToHex(input);
            case 2: return binaryToDecimal(input);
            case 3: return binaryToASCII(input);
            case 4: return decimalToBinary(input);
            case 5: return decimalToHex(input);
            case 6: return decimalToASCII(input);
            case 7: return hexToBinary(input);
            case 8: return hexToDecimal(input);
            case 9: return hexToASCII(input);
            default: return "Invalid Choice";
        }
    }

    // 🔹 Main Menu for User Interaction
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n🔹 Hexadecimal Data Converter");
        System.out.println("1. Single Value Conversion");
        System.out.println("2. Batch File Processing");
        System.out.println("3. Checksum Verification");
        System.out.print("Choose an option: ");
        int option = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (option) {
            case 1: // Single Value Conversion
                System.out.println("\nChoose conversion type:");
                System.out.println("1. Binary to Hex");
                System.out.println("2. Binary to Decimal");
                System.out.println("3. Binary to ASCII");
                System.out.println("4. Decimal to Binary");
                System.out.println("5. Decimal to Hex");
                System.out.println("6. Decimal to ASCII");
                System.out.println("7. Hex to Binary");
                System.out.println("8. Hex to Decimal");
                System.out.println("9. Hex to ASCII");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Enter value: ");
                String inputValue = scanner.nextLine();
                System.out.println("Converted Value: " + convert(inputValue, choice));
                break;

            case 2: // Batch File Processing
                System.out.print("Enter input file path: ");
                String inputFile = scanner.nextLine();

                System.out.print("Enter output file path: ");
                String outputFile = scanner.nextLine();

                System.out.println("\nChoose conversion type:");
                System.out.println("1. Binary to Hex");
                System.out.println("2. Binary to Decimal");
                System.out.println("3. Binary to ASCII");
                System.out.println("4. Decimal to Binary");
                System.out.println("5. Decimal to Hex");
                System.out.println("6. Decimal to ASCII");
                System.out.println("7. Hex to Binary");
                System.out.println("8. Hex to Decimal");
                System.out.println("9. Hex to ASCII");
                System.out.print("Enter your choice: ");
                int batchChoice = scanner.nextInt();

                processFile(inputFile, outputFile, batchChoice);
                break;

            case 3: // Checksum Verification
                System.out.print("Enter data string: ");
                String data = scanner.nextLine();

                int checksum = generateChecksum(data);
                System.out.println("Generated Checksum: " + checksum);

                System.out.print("Enter checksum to verify: ");
                int inputChecksum = scanner.nextInt();

                if (verifyChecksum(data, inputChecksum)) {
                    System.out.println("✅ Checksum Matched! Data is intact.");
                } else {
                    System.out.println("❌ Checksum Mismatch! Data may be corrupted.");
                }
                break;

            default:
                System.out.println("❌ Invalid Option");
        }

        scanner.close();
    }
}


