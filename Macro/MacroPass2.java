/*
 MDT.txt
 1	MEND

 MNT.txt
 ADDONE	1
LDA	1
ADD	1
STA	1

ALA.txt
ALA for ADDONE: [&ARG1, &ARG2]
ALA for LDA: [&ARG1]
ALA for ADD: [&ARG2]
ALA for STA: [&ARG1]
 */

import java.io.*;
import java.util.*;

public class MacroPass2 {
    public static void main(String[] args) throws Exception {
        BufferedReader input = new BufferedReader(new FileReader("macro_input.txt"));
        BufferedReader mntFile = new BufferedReader(new FileReader("MNT.txt"));
        BufferedReader mdtFile = new BufferedReader(new FileReader("MDT.txt"));
        BufferedWriter expanded = new BufferedWriter(new FileWriter("expanded_output.txt"));

        // Step 1: Load MNT into a map
        Map<String, Integer> MNT = new HashMap<>();
        String line;
        while ((line = mntFile.readLine()) != null) {
            String[] parts = line.trim().split("\\s+");
            if (parts.length == 2) {
                MNT.put(parts[0], Integer.parseInt(parts[1]));
            }
        }

        // Step 2: Load MDT into a list
        List<String> MDT = new ArrayList<>();
        while ((line = mdtFile.readLine()) != null) {
            String[] parts = line.split("\\t", 2);
            if (parts.length == 2)
                MDT.add(parts[1]);
        }

        // Step 3: Process the input file
        boolean inMacroDef = false;
        while ((line = input.readLine()) != null) {
            line = line.trim();

            // Skip macro definition section
            if (line.equalsIgnoreCase("MACRO")) {
                inMacroDef = true;
                continue;
            }
            if (line.equalsIgnoreCase("MEND")) {
                inMacroDef = false;
                continue;
            }
            if (inMacroDef) continue;

            // Split the line
            String[] tokens = line.split("[ ,]+");
            String name = tokens[0];

            // If line is a macro call
            if (MNT.containsKey(name)) {
                int mdtIndex = MNT.get(name) - 1;

                // Create ALA for this call
                List<String> arguments = new ArrayList<>();
                for (int i = 1; i < tokens.length; i++) {
                    arguments.add(tokens[i]);
                }

                // Expand macro
                for (int i = mdtIndex; i < MDT.size(); i++) {
                    String temp = MDT.get(i);
                    if (temp.equalsIgnoreCase("MEND")) break;

                    String expandedLine = temp;
                    for (int j = 0; j < arguments.size(); j++) {
                        expandedLine = expandedLine.replace("#" + (j + 1), arguments.get(j));
                    }
                    expanded.write(expandedLine + "\n");
                }
            } else {
                // Regular assembly line
                expanded.write(line + "\n");
            }
        }

        input.close();
        mntFile.close();
        mdtFile.close();
        expanded.close();

        System.out.println("✅ Macro Pass 2 completed successfully!");
        System.out.println("Expanded code saved in 'expanded_output.txt'");
    }
}
