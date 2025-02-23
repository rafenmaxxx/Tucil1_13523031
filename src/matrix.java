package src;

public class matrix {
    
    public char id;
    public int[][] matrix;
    public int baris;
    public int kolom;

    /* konstruktor matrix */
    public matrix(char id, int m, int n) {
        this.id = id;
        this.baris = m;
        this.kolom = n;
        this.matrix = new int[baris][kolom];
    }
    
    /* selektor ID */
    public char getID() {
        return id;
    }
    /* selektor baris */
    public int getBaris() {
        return baris;
    }

    /* selektor kolom */
    public int getKolom() {
        return kolom;
    }

    /* mmenampilkan matrix di terminal */
    public void printMatrix() {
        for (int i = 0; i < baris; i++) {
            System.out.print("[");
            if (matrix[i][0] == 1) {
                System.out.print(id);
            } else {
                System.out.print(" ");
            }

            for (int j = 1; j < kolom; j++) {
                System.out.print(", ");
                if (matrix[i][j] == 1) {
                    System.out.print(id);
                } else {
                    System.out.print(" ");
                }
            }

            System.out.print("]");
            System.out.println(" ");
        }
    }
}