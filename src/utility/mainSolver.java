package src.utility;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class mainSolver {

    public StringBuilder MessageIntro = new StringBuilder();
    public StringBuilder MessageEntry = new StringBuilder();
    public StringBuilder MessageOutro = new StringBuilder();

    public void solve(String inputString) {
    
        Scanner scanner = new Scanner(inputString);

        int[] validasi = readConfigurations(scanner);
        if (validasi == null) {
            MessageIntro.append("Puzzle tidak dapat diselesaikan.");
            return;
        } 
        
        List<List<Character>> inputPapan = new ArrayList<>();
        if (validasi[3] == 2) {
            inputPapan = readPapan(scanner, validasi[0], validasi[1]);
            if (inputPapan == null) {
                MessageIntro.append("Puzzle tidak dapat diselesaikan.");
                return;
            }
        }
        
        // for (int i = 0; i < inputPapan.size(); i++) {
        //     Message.append(inputPapan.get(i));
        // }

        matrix[] puzzles = readPuzzles(scanner, validasi[2]);
        if (puzzles == null) {
            MessageIntro.append("Puzzle tidak dapat diselesaikan.");
            return;
        }

        if (validasi[3] == 1) {
            solver solver = new solver(validasi[0], validasi[1]);
            long startTime = System.nanoTime();
            int[] hasilBruteForce = solver.solve(puzzles, 0, 1);

            if (hasilBruteForce[0] == 1) {
                MessageIntro.append("Puzzle dapat diselesaikan!");
                MessageEntry.append(solver.printPapan());
            } else {
                MessageIntro.append("Puzzle tidak dapat diselesaikan.");
                if (solver.gagal) {
                    MessageEntry.append("Potongan puzzle tidak dapat menutupi keseluruhan papan.");
                }
            }
                            
            long endTime = System.nanoTime(); long duration = endTime - startTime;
            MessageOutro.append("Waktu eksekusi: " + (duration / 1000000.0) + " ms\n");
            MessageOutro.append("Banyak kasus yang ditinjau: " + hasilBruteForce[1]);

            // if (hasilBruteForce[0] == 1) {
            //     Scanner save = new Scanner(System.in);
                
            //     while (true) {
            //         System.out.print("\nApakah Anda ingin menyimpan solusi? (iya / tidak): ");
                    
            //         String input = save.nextLine().trim();
    
            //         if (input.equalsIgnoreCase("iya")) {
            //             Message.append("Melakukan penyimpanan solusi...");
            //             solver.savePapan();
            //             break;
            //         } else if (input.equalsIgnoreCase("tidak")) {
            //             Message.append("Solusi tidak disimpan.");
            //             break;
            //         } else {
            //             Message.append("Input tidak valid! silakan masukan 'iya' atau 'tidak'.");
            //         }
            //     }
            //     Message.append(" ");
    
            //     save.close();
            // }

            return;

        } else {
            solverCustom solver = new solverCustom(inputPapan, validasi[0], validasi[1]);
            long startTime = System.nanoTime();
            int[] hasilBruteForce = solver.solve(puzzles, 0, 1);

            if (hasilBruteForce[0] == 1) {
                MessageIntro.append("Puzzle dapat diselesaikan!");
                MessageEntry.append(solver.printPapan());
            } else {
                MessageIntro.append("Puzzle tidak dapat diselesaikan.");
                if (solver.gagal) {
                    MessageEntry.append("Potongan puzzle tidak dapat menutupi keseluruhan papan.");
                }
            }
                            
            long endTime = System.nanoTime(); long duration = endTime - startTime;
            MessageOutro.append("Waktu eksekusi: " + (duration / 1000000.0) + " ms\n");
            MessageOutro.append("Banyak kasus yang ditinjau: " + hasilBruteForce[1]);

            // if (hasilBruteForce[0] == 1) {
            //     Scanner save = new Scanner(System.in);
                
            //     while (true) {
            //         System.out.print("\nApakah Anda ingin menyimpan solusi? (iya / tidak): ");
                    
            //         String input = save.nextLine().trim();
    
            //         if (input.equalsIgnoreCase("iya")) {
            //             Message.append("Melakukan penyimpanan solusi...");
            //             solver.savePapan();
            //             break;
            //         } else if (input.equalsIgnoreCase("tidak")) {
            //             Message.append("Solusi tidak disimpan.");
            //             break;
            //         } else {
            //             Message.append("Input tidak valid! silakan masukan 'iya' atau 'tidak'.");
            //         }
            //     }
            //     Message.append(" ");

            //     save.close();
            // }
            
            return;
        }
    }

    /* validasi terhadap input variabel M, N, dan P */
    public boolean isValidNumber(String str) {
        if (str.matches("-?\\d+")) {
            int number = Integer.parseInt(str);
            
            if (number > 0) {
                return true;
            }
        }
        return false;
    }

    /* membaca konfigurasi papan dari file .txt */
    public int[] readConfigurations(Scanner scanner) {

            char[] variabel = {'M', 'N', 'P'};
            List<Integer> nilaiVariabel = new ArrayList<>();

            for (int i = 0; i < 3; i++) {
                if (scanner.hasNext()) {
                    String token = scanner.next();
                    if (isValidNumber(token)) {
                        nilaiVariabel.add(Integer.parseInt(token));
                        // Message.append("Nilai " + variabel[i] + ": " + nilaiVariabel.get(i));
                    } else {
                        MessageEntry.append(token + " bukan nilai yang valid untuk variabel " + variabel[i] + ".");
                        scanner.close();
                        return null;
                    }
                } else {
                    MessageEntry.append("Tidak ada nilai yang dibaca untuk variabel " + variabel[i] + ".");
                    scanner.close();
                    return null;
                }
            }

            String token = scanner.next();
            if (!(token.equals("DEFAULT") || token.equals("CUSTOM"))) {
                MessageEntry.append("Bukan kasus konfigurasi yang valid.");
                scanner.close();
                return null;
            }

            int konfigurasi;
            if (token.equals("DEFAULT")) {
                konfigurasi = 1;
            } else {
                konfigurasi = 2;
            }

            scanner.nextLine();
            return new int[]{nilaiVariabel.get(0), nilaiVariabel.get(1), nilaiVariabel.get(2), konfigurasi};
    }

    /* membaca konfigurasi puzzles dari file .txt */
    public matrix[] readPuzzles(Scanner scanner, int jumlahPuzzle) {
        
        List<matrix> inputPuzzle = new ArrayList<>();
        List<List<Integer>> bentukPuzzle = new ArrayList<>();
        char currentChar = '.';

        int barisPuzzle = 0, kolomPuzzle = 0;
        String line = scanner.nextLine(); barisPuzzle++;
        char[] currentLine = line.toCharArray(); kolomPuzzle = currentLine.length;

        // pengecekan satu baris input terdiri atas huruf yang sama
        for (int i = 0; i < kolomPuzzle; i++) {
            if (currentLine[i] != ' ') {
                if (currentLine[i] == '.' || currentLine[i] == 'X') {
                    MessageEntry.append("Input papan custom tidak sesuai dengan nilai variabel M dan N.");
                    return null;
                }
                currentChar = currentLine[i];
                break;
            }
        }

        for (int i = 0; i < kolomPuzzle; i++) {
            if (currentLine[i] != ' ' && currentLine[i] != currentChar) {
                MessageEntry.append("Terdapat huruf yang berbeda pada satu baris puzzle, bukan input yang valid.");
                scanner.close();
                return null;
            }
        } 

        List<Integer> bentukPuzzleHolder = new ArrayList<>();
        for (int i = 0; i < kolomPuzzle; i++) {
            if (currentLine[i] == currentChar) {
                bentukPuzzleHolder.add(1);
            } else {
                bentukPuzzleHolder.add(0);
            }
        }
        bentukPuzzle.add(bentukPuzzleHolder);
        
        while (scanner.hasNextLine()) {

            char charToCheck = currentChar;
            line = scanner.nextLine();
            currentLine = line.toCharArray();
            
            // pengecekan satu baris input terdiri atas huruf yang sama
            for (int i = 0; i < currentLine.length; i++) {
                if (currentLine[i] != ' ') {
                    currentChar = currentLine[i];
                    break;
                }
            }

            for (int i = 0; i < currentLine.length; i++) {
                if (currentLine[i] != ' ' && currentLine[i] != currentChar) {
                    MessageEntry.append("Terdapat huruf yang berbeda pada satu baris puzzle, bukan input yang valid.");
                    scanner.close();
                    return null;
                }
            } 

            for (int i = 0; i < currentLine.length; i++) {            
                if (currentLine[i] != ' ' && currentLine[i] != charToCheck) {
                    // Message.append("currentLine: " + line + "]");
                    // Message.append("currentLine[i]: " + currentLine[i]);
                    // Message.append("barisPuzzle: " + barisPuzzle);
                    // Message.append("kolomPuzzle: " + kolomPuzzle);
                    matrix currentPuzzle = new matrix(charToCheck, barisPuzzle, kolomPuzzle);

                    for (int m = 0; m < barisPuzzle; m++) {
                        int iterator = 0;
                        List<Integer> currentBentukPuzzle = bentukPuzzle.get(m);
                        
                        for (int n = 0; n < kolomPuzzle; n++) {
                            if (iterator < currentBentukPuzzle.size()) {
                                currentPuzzle.matrix[m][n] = currentBentukPuzzle.get(iterator);
                            } else {
                                currentPuzzle.matrix[m][n] = 0;
                            }
                            iterator++;
                        }
                    }

                    // currentPuzzle.printMatrix();
                    inputPuzzle.add(currentPuzzle);

                    bentukPuzzle = new ArrayList<>();
                    barisPuzzle = 0; kolomPuzzle = 0;
                    currentChar = currentLine[i];
                    break;
                }
            }

            bentukPuzzleHolder = new ArrayList<>();
            for (int i = 0; i < currentLine.length; i++) {
                if (currentLine[i] == currentChar) {
                    bentukPuzzleHolder.add(1);
                } else {
                    bentukPuzzleHolder.add(0);
                }
            }
            bentukPuzzle.add(bentukPuzzleHolder);

            if (currentLine.length > kolomPuzzle) {
                kolomPuzzle = currentLine.length;
            }
            barisPuzzle++;
        }
       
        matrix currentPuzzle = new matrix(currentChar, barisPuzzle, kolomPuzzle);
        for (int m = 0; m < barisPuzzle; m++) {
            int iterator = 0;
            List<Integer> currentBentukPuzzle = bentukPuzzle.get(m);
            
            for (int n = 0; n < kolomPuzzle; n++) {
                if (iterator < currentBentukPuzzle.size()) {
                    currentPuzzle.matrix[m][n] = currentBentukPuzzle.get(iterator);
                } else {
                    currentPuzzle.matrix[m][n] = 0;
                }
                iterator++;
            }
        }

        // currentPuzzle.printMatrix();
        inputPuzzle.add(currentPuzzle);

        if (inputPuzzle.size() != jumlahPuzzle) {
            MessageEntry.append("Jumlah potongan puzzle tidak sama dengan nilai variabel P.");
            scanner.close();
            return null;
        }

        matrix[] inputPuzzleMatrix = inputPuzzle.toArray(new matrix[inputPuzzle.size()]);
        return inputPuzzleMatrix;
    }

    /* membaca konfigurasi papan custom dari file .txt */
    public List<List<Character>> readPapan(Scanner scanner, int barisPapan, int kolomPapan) {
        List<List<Character>> hasil = new ArrayList<>();
        String line = scanner.nextLine();
        char[] currentLine = line.toCharArray();

        for (int i = 0; i < barisPapan; i++) {
            if (currentLine.length != kolomPapan) {
                MessageEntry.append("Input papan custom tidak sesuai dengan nilai variabel M dan N.");
                return null;
            }

            List<Character> currentHasil = new ArrayList<>();
            for (int j = 0; j < kolomPapan; j++) {
                if (currentLine[j] != '.' && currentLine[j] != 'X') {
                    MessageEntry.append("Input papan custom memiliki char selain '.' dan 'X'.");
                    return null;
                }

                currentHasil.add(currentLine[j]);
            }

            hasil.add(currentHasil);

            if (i != barisPapan - 1) {
                line = scanner.nextLine();
                currentLine = line.toCharArray();
            }
        }

        return hasil;
    }
}