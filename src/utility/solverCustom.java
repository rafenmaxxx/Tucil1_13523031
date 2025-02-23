package src.utility;
import java.io.*;
import java.util.*;

public class solverCustom extends mainSolver {
    
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
    public String printPapan() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < barisPapan; i++) {
            result.append("[" + papan[i][0]);

            for (int j = 1; j < kolomPapan; j++) {
                    result.append(", " +  papan[i][j]);
            }

            result.append("]\n");
        }

        return result.toString();
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