package src.utility;
import java.util.*;

public class solver extends mainSolver {

    public boolean gagal = false;
    public int barisPapan;
    public int kolomPapan;
    public char[][] papan;

    /* konstruktor solver */
    public solver(int barisPapan, int kolomPapan, boolean custom, List<List<Character>> papanCustom) {
        this.barisPapan = barisPapan;
        this.kolomPapan = kolomPapan;
        this.papan = new char[barisPapan][kolomPapan];

        if (!custom) {
            for (int i = 0; i < barisPapan; i++) {
                for (int j = 0; j < kolomPapan; j++) {
                    papan[i][j] = ' ';
                }
            }
        
        } else {
            for (int i = 0; i < barisPapan; i++) {
                for (int j = 0; j < kolomPapan; j++) {
                    if (papanCustom.get(i).get(j) == 'X') {
                        papan[i][j] = ' ';
                    } else {
                        papan[i][j] = '.';
                    }
                }
            }
        }
    }

    /* meletakkan puzzle pada papan */
    public boolean taruhPuzzle(matrix currentPuzzleOrientation, int currentBaris, int currentKolom, boolean custom) {
        for (int i = 0; i < currentPuzzleOrientation.getBaris(); i++) {
            for (int j = 0; j < currentPuzzleOrientation.getKolom(); j++) {
                if (currentPuzzleOrientation.matrix[i][j] == 1) {
                    int newBaris = currentBaris + i;
                    int newKolom = currentKolom + j;

                    if (!custom) {
                        if (newBaris >= barisPapan || newKolom >= kolomPapan || papan[newBaris][newKolom] != ' ') {
                            return false;
                        }
                    
                    } else {
                        if (newBaris >= barisPapan || newKolom >= kolomPapan || papan[newBaris][newKolom] != ' ' || papan[newBaris][newKolom] == '.') {
                            return false;
                        }
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
    public int[] solve(matrix[] puzzles, int index, int iterations, boolean custom) {

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

                    if (taruhPuzzle(currentPuzzleOrientation, currentBaris, currentKolom, custom)) {
                        int result[] = solve(puzzles, index + 1, iterations, custom);

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
}