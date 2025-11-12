/*
input.txt
START 100
MOVER AREG, B
ADD BREG, C
MOVEM AREG, D
D EQU C
C DS 1
B DC 5
END */

import java.io.*;
import java.util.*;

public class Pass1 {
    static Map<String, String> MOT = new HashMap<>();
    static List<String[]> symbolTable = new ArrayList<>();
    static List<String> intermediate = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        // Initialize Machine Opcode Table (MOT)
        MOT.put("START", "AD,01");
        MOT.put("END", "AD,02");
        MOT.put("ORIGIN", "AD,03");
        MOT.put("EQU", "AD,04");
        MOT.put("LTORG", "AD,05");
        MOT.put("DC", "DL,01");
        MOT.put("DS", "DL,02");
        MOT.put("MOVER", "IS,04");
        MOT.put("MOVEM", "IS,05");
        MOT.put("ADD", "IS,01");
        MOT.put("SUB", "IS,02");
        MOT.put("MULT", "IS,03");
        MOT.put("READ", "IS,06");
        MOT.put("PRINT", "IS,07");

        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        BufferedWriter inter = new BufferedWriter(new FileWriter("intermediate.txt"));
        BufferedWriter sym = new BufferedWriter(new FileWriter("symtab.txt"));

        String line;
        int LC = 0;

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.equals("")) continue;

            String[] parts = line.split("[ ,]+");
            int n = parts.length;

            // ---------- START ----------
            if (parts[0].equals("START")) {
                LC = Integer.parseInt(parts[1]);
                inter.write("(AD,01)\t(C," + LC + ")\n");
                continue;
            }

            // ---------- END ----------
            if (parts[0].equals("END")) {
                inter.write("(AD,02)\n");
                break;
            }

            int startIndex = 0;
            String label = "";

            // ---------- Check for LABEL ----------
            if (!MOT.containsKey(parts[0])) {
                label = parts[0];
                addSymbol(label, LC);
                startIndex = 1;
            }

            // ---------- Process Opcode ----------
            String opcode = parts[startIndex];
            String operands = "";
            for (int i = startIndex + 1; i < n; i++) operands += parts[i] + " ";

            if (MOT.containsKey(opcode)) {
                String[] val = MOT.get(opcode).split(",");
                String type = val[0];
                String code = val[1];

                if (type.equals("IS")) {
                    inter.write("(" + type + "," + code + ")\t" + operands.trim() + "\n");
                    LC++;
                } else if (type.equals("DL")) {
                    inter.write("(" + type + "," + code + ")\t(C," + operands.trim() + ")\n");
                    LC++;
                } else if (type.equals("AD")) {
                    inter.write("(" + type + "," + code + ")\t" + operands.trim() + "\n");
                }
            }
        }

        // ---------- Write Symbol Table ----------
        for (String[] s : symbolTable)
            sym.write(s[0] + "\t" + s[1] + "\n");

        br.close();
        inter.close();
        sym.close();

        System.out.println("✅ Pass 1 completed.");
        System.out.println("Files created: intermediate.txt , symtab.txt");
    }

    // ---------- Add Symbol ----------
    static void addSymbol(String sym, int addr) {
        for (String[] s : symbolTable)
            if (s[0].equals(sym)) return;
        symbolTable.add(new String[]{sym, String.valueOf(addr)});
    }
}
