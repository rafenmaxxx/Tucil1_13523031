package src.utility;
import java.util.*;

public class preprocessing {

    /* melakukan rotasi 90 derajat searah jarum jam */
    public matrix rotasi(matrix input) {
        char id = input.getID();
        int baris = input.getBaris();
        int kolom = input.getKolom();
        matrix hasilRotasi = new matrix(id, kolom, baris);

        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                hasilRotasi.matrix[j][baris - i - 1] = input.matrix[i][j];
            }
        }

        return hasilRotasi;
    }

    /* melakukan refleksi horizontal */
    public matrix refleksi(matrix input) {
        char id = input.getID();
        int baris = input.getBaris();
        int kolom = input.getKolom();
        matrix hasilRefleksi = new matrix(id, baris, kolom);

        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                hasilRefleksi.matrix[i][kolom - j - 1] = input.matrix[i][j];
            }
        }

        return hasilRefleksi;
    }

    /* menyimpan seluruh transformasi rotasi dan refleksi */
    public List<matrix> otakAtik(matrix input) {
        List<matrix> hasilTransformasi = new ArrayList<>();

        matrix rotasi90 = rotasi(input);
        matrix rotasi180 = rotasi(rotasi90);
        matrix rotasi270 = rotasi(rotasi180);
        matrix refleksi = refleksi(input);
        matrix refleksi90 = rotasi(refleksi);
        matrix refleksi180 = rotasi(refleksi90);
        matrix refleksi270 = rotasi(refleksi180);

        hasilTransformasi.add(input);
        hasilTransformasi.add(rotasi90);
        hasilTransformasi.add(rotasi180);
        hasilTransformasi.add(rotasi270);
        hasilTransformasi.add(refleksi);
        hasilTransformasi.add(refleksi90);
        hasilTransformasi.add(refleksi180);
        hasilTransformasi.add(refleksi270);

        return hasilTransformasi;
    }
}