package src;
import java.io.*;
import java.util.*;

public class solverCustom {
    
    public boolean gagal = false;
    public int barisPapan;
    public int kolomPapan;
    public char[][] papan;

    /* konstruktor solverCustom */
    public solverCustom(List<List<Character>> input, int barisPapan, int kolomPapan) {
        this.barisPapan = barisPapan;
        this.kolomPapan = kolomPapan;
        this.papan = new char[barisPapan][kolomPapan];

        for (int i = 0; i < barisPapan; i++) {
            for (int j = 0; j < kolomPapan; j++) {
                if (input.get(i).get(j) == 'X') {
                    papan[i][j] = ' ';
                } else {
                    papan[i][j] = '.';
                }
            }
        }
    }

    /* meletakkan puzzle pada papan */
    public boolean taruhPuzzle(matrix currentPuzzleOrientation, int currentBaris, int currentKolom) {
        for (int i = 0; i < currentPuzzleOrientation.getBaris(); i++) {
            for (int j = 0; j < currentPuzzleOrientation.getKolom(); j++) {
                if (currentPuzzleOrientation.matrix[i][j] == 1) {
                    int newBaris = currentBaris + i;
                    int newKolom = currentKolom + j;

                    if (newBaris >= barisPapan || newKolom >= kolomPapan || papan[newBaris][newKolom] != ' ' || papan[newBaris][newKolom] == '.') {
                        return false;
                    }
                }
            }
        }
    
        for (int i = 0; i < currentPuzzleOrientation.getBaris(); i++) {
            for (int j = 0; j < currentPuzzleOrientation.getKolom(); j++) {
                if (currentPuzzleOrientation.matrix[i][j] == 1) {
                    papan[currentBaris + i][currentKolom + j] = currentPuzzleOrientation.getID();
                }
            }
        }

        return true;
    }

    /* melakukan backtrack dengan menghapus puzzle yang telah diletakkan pada papan */
    public void buangPuzzle(matrix currentPuzzleOrientation, int currentBaris, int currentKolom) {
        for (int i = 0; i < currentPuzzleOrientation.getBaris(); i++) {
            for (int j = 0; j < currentPuzzleOrientation.getKolom(); j++) {
                if (currentPuzzleOrientation.matrix[i][j] == 1) {
                    papan[currentBaris + i][currentKolom + j] = ' ';
                }
            }
        }
    }

    /* metode untuk menyelesaikan puzzle */
    public int[] solve(matrix[] puzzles, int index, int iterations) {

        if (index >= puzzles.length) {
            if (cekPapan()) {
                return new int[]{1, iterations};
            } else {
                gagal = true;
                return new int[]{0, iterations};
            }
        }

        if (gagal) {
            return new int[]{0, iterations};
        }

        preprocessing preprocessing = new preprocessing();
        matrix currentPuzzle = puzzles[index];
        List<matrix> currentPuzzleAllOrientations = preprocessing.otakAtik(currentPuzzle);

        for (int k = 0; k < currentPuzzleAllOrientations.size(); k++) {
            matrix currentPuzzleOrientation = currentPuzzleAllOrientations.get(k);

            for (int currentBaris = 0; currentBaris < barisPapan; currentBaris++) {
                for (int currentKolom = 0; currentKolom < kolomPapan; currentKolom++) {
                    iterations++;

                    if (taruhPuzzle(currentPuzzleOrientation, currentBaris, currentKolom)) {
                        int result[] = solve(puzzles, index + 1, iterations);

                        if (result[0] == 1) {
                            return result;
                        }

                        buangPuzzle(currentPuzzleOrientation, currentBaris, currentKolom);
                    }
                }
            }
        }

        return new int[]{0, iterations};
    }

    /* menampilkan hasil akhir papan */
    public void printPapan() {
        String reset = "\u001B[0m";
        List<String> warna = List.of(
"\u001B[30m",           // A: Black
            "\u001B[31m",           // B: Red
            "\u001B[32m",           // C: Green
            "\u001B[33m",           // D: Yellow
            "\u001B[34m",           // E: Blue
            "\u001B[35m",           // F: Magenta
            "\u001B[36m",           // G: Cyan
            "\u001B[37m",           // H: White
            "\u001B[1m\u001B[30m",  // I: Bold Black
            "\u001B[1m\u001B[31m",  // J: Bold Red
            "\u001B[1m\u001B[32m",  // K: Bold Green
            "\u001B[1m\u001B[33m",  // L: Bold Yellow
            "\u001B[1m\u001B[34m",  // M: Bold Blue
            "\u001B[1m\u001B[35m",  // N: Bold Magenta
            "\u001B[1m\u001B[36m",  // O: Bold Cyan
            "\u001B[1m\u001B[37m",  // P: Bold White
            "\u001B[4m\u001B[30m",  // Q: Underscored Black
            "\u001B[4m\u001B[31m",  // R: Underscored Red
            "\u001B[4m\u001B[32m",  // S: Underscored Green
            "\u001B[4m\u001B[33m",  // T: Underscored Yellow
            "\u001B[4m\u001B[34m",  // U: Underscored Blue
            "\u001B[4m\u001B[35m",  // V: Underscored Magenta 
            "\u001B[4m\u001B[36m",  // W: Underscored Cyan
            "\u001B[4m\u001B[37m",  // X: Underscored White
            "\u001B[42m",           // Y: Bright Green Background
            "\u001B[41m"            // Z: Bright Blue Background
        );

        for (int i = 0; i < barisPapan; i++) {
            if (papan[i][0] != '.') {
                System.out.print("[" + warna.get(((int) papan[i][0]) - 65) + papan[i][0] + reset);
            } else {
                System.out.print("[ ");
            }

            for (int j = 1; j < kolomPapan; j++) {
                if (papan[i][j] != '.') {
                    // System.out.println((int) papan[i][j] + ": " + papan[i][j]);
                    System.out.print(", " + warna.get(((int) papan[i][j]) - 65) + papan[i][j] + reset);
                } else {
                    System.out.print(",  ");
                }            }

            System.out.print("]");
            System.out.println(" ");
        }
    }

    /* mengecek apakah semua titik pada papan terisi atau tidak */
    public boolean cekPapan() {
        for (int i = 0; i < barisPapan; i++) {
            for (int j = 0; j < kolomPapan; j++) {
                if (papan[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    /* menyimpan konfigurasi puzzle pada papan ke dalam file .txt */
    public void savePapan() {

        try {
            PrintWriter writer = new PrintWriter("solusi.txt");
            for (int i = 0; i < barisPapan; i++) {
                writer.print("[" + papan[i][0]);

                for (int j = 1; j < kolomPapan; j++) {
                    writer.print(", " + papan[i][j]);
                }

                if (i != barisPapan - 1) {
                    writer.println("]");
                } else {
                    writer.print("]");
                }
            }

            System.out.println("Solusi telah disimpan dalam file solusi.txt");
            writer.close();
    
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan dalam membentuk file .txt");
            e.printStackTrace();
        }
    }
}