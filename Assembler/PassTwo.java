/*
symtab.txt
D	103
C	103
B	104
intermediate.txt
(AD,01)	(C,100)
(IS,04)	AREG B
(IS,01)	BREG C
(IS,05)	AREG D
(AD,04)	C
(DL,02)	(C,1)
(DL,01)	(C,5)
(AD,02)
 */


import java.io.*;
import java.util.*;

public class PassTwo {
    public static void main(String[] args) throws Exception {

        BufferedReader inter = new BufferedReader(new FileReader("intermediate.txt"));
        BufferedReader sym = new BufferedReader(new FileReader("symtab.txt"));
        BufferedWriter mc = new BufferedWriter(new FileWriter("machinecode.txt"));

        // --- Load Symbol Table ---
        Map<String, String> symtab = new HashMap<>();
        String line;
        while ((line = sym.readLine()) != null) {
            line = line.trim();
            if (line.equals("")) continue;
            String[] parts = line.split("\\s+");
            if (parts.length >= 2)
                symtab.put(parts[0], parts[1]);
        }

        // --- Read Intermediate Code ---
        while ((line = inter.readLine()) != null) {
            line = line.trim();
            if (line.equals("")) continue;

            if (line.startsWith("(AD")) {
                // Assembler Directive -> no machine code
                continue;
            } 
            else if (line.startsWith("(DL")) {
                // Declarative Statements
                if (line.contains("DL,01")) { // DC
                    int cIndex = line.indexOf("(C,");
                    if (cIndex != -1) {
                        String value = line.substring(cIndex + 3, line.indexOf(")", cIndex));
                        mc.write("00 00 " + value + "\n");
                    }
                } else if (line.contains("DL,02")) { // DS
                    mc.write("00 00 00\n");
                }
            } 
            else if (line.startsWith("(IS")) {
                // Example: (IS,04) AREG,ONE
                int start = line.indexOf("(IS,") + 4;
                int end = line.indexOf(")", start);
                String opcode = line.substring(start, end).trim();

                String reg = "00";
                String mem = "000";

                // Split operands (if present)
                String[] parts = line.split("\\s+");
                if (parts.length > 1) {
                    String[] ops = parts[1].split(",");
                    if (ops.length > 0) {
                        String r = ops[0].trim();
                        if (r.equalsIgnoreCase("AREG")) reg = "01";
                        else if (r.equalsIgnoreCase("BREG")) reg = "02";
                        else if (r.equalsIgnoreCase("CREG")) reg = "03";
                    }
                    if (ops.length > 1) {
                        String symbol = ops[1].trim();
                        if (symtab.containsKey(symbol)) mem = symtab.get(symbol);
                    }
                }

                mc.write(opcode + " " + reg + " " + mem + "\n");
            }
        }

        inter.close();
        sym.close();
        mc.close();

        System.out.println("✅ Pass 2 completed successfully!");
        System.out.println("File generated: machinecode.txt");
    }
}
