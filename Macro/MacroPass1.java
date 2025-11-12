
/*MacroPass1.txt
MACRO
ADDONE &ARG1, &ARG2
LDA &ARG1
ADD &ARG2
STA &ARG1
MEND
START 100
ADDONE A, B
END */
import java.io.*;
import java.util.*;

public class MacroPass1 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("macro_input.txt"));
        BufferedWriter mntFile = new BufferedWriter(new FileWriter("MNT.txt"));
        BufferedWriter mdtFile = new BufferedWriter(new FileWriter("MDT.txt"));
        BufferedWriter alaFile = new BufferedWriter(new FileWriter("ALA.txt"));

        Map<String, Integer> mnt = new LinkedHashMap<>();
        List<String> mdt = new ArrayList<>();
        List<String> ala = new ArrayList<>();

        String line;
        boolean inMacro = false;
        int mdtPointer = 1;

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.equalsIgnoreCase("MACRO")) {
                inMacro = true;
                continue;
            }
            if (inMacro) {
                if (line.equalsIgnoreCase("MEND")) {
                    mdt.add("MEND");
                    inMacro = false;
                    continue;
                }

                if (!mnt.containsKey(line.split(" ")[0])) {
                    // First line after MACRO = macro name
                    String[] parts = line.split("[ ,]+");
                    String macroName = parts[0];
                    mnt.put(macroName, mdtPointer);

                    // Store arguments in ALA
                    ala.clear();
                    for (int i = 1; i < parts.length; i++) {
                        ala.add(parts[i]);
                    }

                    // Write arguments to ALA file
                    alaFile.write("ALA for " + macroName + ": " + ala + "\n");
                    continue;
                } else {
                    // Body of macro -> store in MDT
                    String temp = line;
                    for (int i = 0; i < ala.size(); i++) {
                        temp = temp.replace(ala.get(i), "#" + (i + 1));
                    }
                    mdt.add(temp);
                    mdtPointer++;
                }
            }
        }

        // Write tables to files
        for (Map.Entry<String, Integer> e : mnt.entrySet()) {
            mntFile.write(e.getKey() + "\t" + e.getValue() + "\n");
        }
        for (int i = 0; i < mdt.size(); i++) {
            mdtFile.write((i + 1) + "\t" + mdt.get(i) + "\n");
        }

        br.close();
        mntFile.close();
        mdtFile.close();
        alaFile.close();

        System.out.println("✅ Macro Pass 1 completed successfully!");
        System.out.println("Files generated: MNT.txt, MDT.txt, ALA.txt");
    }
}
