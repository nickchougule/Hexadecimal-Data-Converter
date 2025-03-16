import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class HexConverterGUI extends JFrame {
    private JTextField inputField, checksumField;
    private JTextArea outputArea;
    private JComboBox<String> conversionOptions, fileConversionOptions, delimiterOptions;
    private JButton convertButton, fileButton, checksumButton;

    public HexConverterGUI() {
        // 🔹 Frame Setup
        setTitle("Hexadecimal Data Converter");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🔹 Theme Setup
        Color backgroundColor = new Color(45, 45, 45);
        Color textColor = new Color(220, 220, 220);
        Color buttonColor = new Color(70, 130, 180);
        getContentPane().setBackground(backgroundColor);

        // 🔹 Tabs for UI
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        // ✅ SINGLE CONVERSION PANEL
        JPanel convertPanel = new JPanel(new FlowLayout());
        convertPanel.setBackground(backgroundColor);
        inputField = new JTextField(20);
        convertButton = new JButton("Convert");
        convertButton.setBackground(buttonColor);
        convertButton.setForeground(Color.WHITE);

        String[] options = {
                "Binary to Hex", "Binary to Decimal", "Binary to ASCII",
                "Decimal to Binary", "Decimal to Hex", "Decimal to ASCII",
                "Hex to Binary", "Hex to Decimal", "Hex to ASCII"
        };
        conversionOptions = new JComboBox<>(options);
        conversionOptions.setBackground(buttonColor);
        conversionOptions.setForeground(Color.WHITE);

        convertPanel.add(new JLabel("Enter Value:"));
        convertPanel.add(inputField);
        convertPanel.add(conversionOptions);
        convertPanel.add(convertButton);

        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(60, 60, 60));
        outputArea.setForeground(textColor);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 14));

        convertPanel.add(new JScrollPane(outputArea));

        tabbedPane.add("Convert", convertPanel);

        // ✅ BATCH FILE PROCESSING PANEL
        JPanel filePanel = new JPanel(new FlowLayout());
        filePanel.setBackground(backgroundColor);

        fileButton = new JButton("Select File for Processing");
        fileButton.setBackground(buttonColor);
        fileButton.setForeground(Color.WHITE);

        fileConversionOptions = new JComboBox<>(options);
        fileConversionOptions.setBackground(buttonColor);
        fileConversionOptions.setForeground(Color.WHITE);

        String[] delimiters = { "Comma (,)", "Space", "Newline" };
        delimiterOptions = new JComboBox<>(delimiters);
        delimiterOptions.setBackground(buttonColor);
        delimiterOptions.setForeground(Color.WHITE);

        filePanel.add(new JLabel("Choose Conversion Type:"));
        filePanel.add(fileConversionOptions);
        filePanel.add(new JLabel("File Delimiter:"));
        filePanel.add(delimiterOptions);
        filePanel.add(fileButton);

        tabbedPane.add("Batch Processing", filePanel);

        // ✅ CHECKSUM PANEL
        JPanel checksumPanel = new JPanel(new FlowLayout());
        checksumPanel.setBackground(backgroundColor);
        checksumField = new JTextField(20);
        checksumButton = new JButton("Verify Checksum");
        checksumButton.setBackground(buttonColor);
        checksumButton.setForeground(Color.WHITE);

        checksumPanel.add(new JLabel("Enter Data:"));
        checksumPanel.add(checksumField);
        checksumPanel.add(checksumButton);

        tabbedPane.add("Checksum", checksumPanel);

        // 🔹 Add Panels
        add(tabbedPane, BorderLayout.CENTER);

        // 🔹 Button Actions
        convertButton.addActionListener(e -> {
            String input = inputField.getText().trim();
            int choice = conversionOptions.getSelectedIndex() + 1;
            String result = convert(input, choice);
            outputArea.setText("Converted Value:\n" + result);
        });

        fileButton.addActionListener(e -> batchFileProcessing());

        checksumButton.addActionListener(e -> {
            String data = checksumField.getText().trim();
            int checksum = generateChecksum(data);
            JOptionPane.showMessageDialog(null, "Generated Checksum: " + checksum);
        });

        setVisible(true);
    }

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

    public static String convert(String input, int choice) {
        try {
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
        } catch (Exception e) {
            return "Error: Invalid Input";
        }
    }
    


    public void batchFileProcessing() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File inputFile = fileChooser.getSelectedFile();
    
            JFileChooser saveChooser = new JFileChooser();
            returnValue = saveChooser.showSaveDialog(null);
            
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File outputFile = saveChooser.getSelectedFile();
                int conversionChoice = fileConversionOptions.getSelectedIndex() + 1;
                String delimiter = delimiterOptions.getSelectedItem().toString();
                
                // Pass the conversion choice as a parameter
                processFile(inputFile.getAbsolutePath(), outputFile.getAbsolutePath(), conversionChoice, delimiter);
            }
        }
    }
    
    public static void processFile(String inputFile, String outputFile, int conversionChoice, String delimiter) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
    
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values;
                if (delimiter.contains("Comma")) {
                    values = line.split(",");
                } else if (delimiter.contains("Space")) {
                    values = line.split(" ");
                } else {
                    values = new String[]{line};
                }
    
                for (String value : values) {
                    String convertedValue = convert(value.trim(), conversionChoice);
                    int checksum = generateChecksum(convertedValue);
                    writer.write(convertedValue + " | Checksum: " + checksum);
                    writer.newLine();
                }
            }
    
            JOptionPane.showMessageDialog(null, "Batch processing completed. Output saved to: " + outputFile);
    
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error processing file: " + e.getMessage());
        }
    }
    
    // 🔹 Checksum Functions
    public static int generateChecksum(String data) {
        int checksum = 0;
        for (char c : data.toCharArray()) {
            checksum ^= c;
        }
        return checksum;
    }

    public static void main(String[] args) {
        new HexConverterGUI();
    }
}
