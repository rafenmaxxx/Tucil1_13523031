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
    public int[] solve(matrix[] puzzles, boolean custom) {
        int iterations = 0;
        int index = 0;

        // menyimpan baris, kolom, dan orientasi puzzle yang sedang diletakkan
        int[][] kondisiPuzzle = new int[puzzles.length][3];
        for (int i = 0; i < puzzles.length; i++) {
            kondisiPuzzle[i] = new int[]{-1, -1, -1};
        }

        while (0 <= index  && index < puzzles.length) {
            preprocessing preprocessing = new preprocessing();
            matrix currentPuzzle = puzzles[index];
            List<matrix> currentPuzzleAllOrientations = preprocessing.otakAtik(currentPuzzle);

            boolean placed = false;

            int barisAwal, kolomAwal, orientasiAwal;
            if (kondisiPuzzle[index][0] == -1) {
                barisAwal = 0;
            } else {
                barisAwal = kondisiPuzzle[index][0];
            }

            if (kondisiPuzzle[index][1] == -1) {
                kolomAwal = 0;
            } else {
                kolomAwal = kondisiPuzzle[index][1];
            }

            if (kondisiPuzzle[index][2] == -1) {
                orientasiAwal = 0;
            } else {
                orientasiAwal = kondisiPuzzle[index][2] + 1;
            }

            for (int currentBaris = barisAwal; currentBaris < barisPapan; currentBaris++) {
                int temp;
                if (currentBaris == barisAwal) {
                    // ketika pengecekan dimulai di baris yang sama, kolom dimulai dari kolom terakhir + 1
                    temp = kolomAwal;
                } else {
                    // ketika pengecekan dilanjutkan ke baris yang baru, kolom dimulai dari 0
                    temp = 0;
                }

                for (int currentKolom = temp; currentKolom < kolomPapan; currentKolom++) {
                    for (int currentOrientasi = orientasiAwal; currentOrientasi < currentPuzzleAllOrientations.size(); currentOrientasi++) {
                        matrix currentPuzzleOrientation = currentPuzzleAllOrientations.get(currentOrientasi);
                        iterations++;

                        if (taruhPuzzle(currentPuzzleOrientation, currentBaris, currentKolom, custom)) {
                            kondisiPuzzle[index] = new int[]{currentBaris, currentKolom, currentOrientasi};
                            placed = true;
                            break;
                        }

                    }
                    if (placed) {
                        break;
                    }
                }
                if (placed) {
                    break;
                }
            }

            if (placed) {
                // puzzle berhasil ditempatkan, lanjut ke puzzle selanjutnya
                index++;
            
            } else {
                // puzzle gagal ditempatkan, coba kemungkinan lain puzzle sebelumnya
                kondisiPuzzle[index] = new int[]{-1, -1, -1};
                index--;

                if (index >= 0) {
                    matrix puzzleUntukDihapus = preprocessing.otakAtik(puzzles[index]).get(kondisiPuzzle[index][2]);
                    buangPuzzle(puzzleUntukDihapus, kondisiPuzzle[index][0], kondisiPuzzle[index][1]);

                    kondisiPuzzle[index][2]++;

                    if (kondisiPuzzle[index][2] >= preprocessing.otakAtik(puzzles[index]).size()) {
                        kondisiPuzzle[index][2] = 0;
                        kondisiPuzzle[index][1]++;  
                
                        if (kondisiPuzzle[index][1] >= kolomPapan) {
                            kondisiPuzzle[index][1] = 0;
                            kondisiPuzzle[index][0]++;
                        }
                    }
                }
            }
        }

        if (index == -1) {
            return new int[]{0, iterations};
        }

        if (cekPapan()) {
            return new int[]{1, iterations};
        } else {
            gagal = true;
            return new int[]{0, iterations};
        }
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