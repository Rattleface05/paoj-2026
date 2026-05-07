package com.pao.laboratory09.exercise2;
import com.pao.laboratory09.exercise1.TipTranzactie;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;


public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex2.bin";
    private static final int RECORD_SIZE = 32;

    public static String bytesToString(byte[] bytes){
        ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
        int id = buffer.getInt();
        double suma = buffer.getDouble();


        byte[] dataBytes = new byte[10];
        buffer.get(dataBytes);
        String data = new String(dataBytes, StandardCharsets.US_ASCII).trim();

        byte tipByte = buffer.get();
        TipTranzactie tip = (tipByte == 0) ? TipTranzactie.CREDIT : TipTranzactie.DEBIT;

        byte statusByte = buffer.get();
        Status status = switch (statusByte) {
            case 0 -> Status.PENDING;
            case 1 -> Status.PROCESSED;
            case 2 -> Status.REJECTED;
            default -> throw new IllegalArgumentException("Status invalid");
        };




        return String.format("id=%d data=%s tip=%s suma=%.2f RON status=%s", id, data, tip, suma, status);
    }

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data tip)
        // 2. Scrie toate înregistrările în OUTPUT_FILE cu DataOutputStream (format binar, RECORD_SIZE=32 bytes/înreg.)
        //    - bytes 0-3:   id (int, little-endian via ByteBuffer)
        //    - bytes 4-11:  suma (double, little-endian via ByteBuffer)
        //    - bytes 12-21: data (String, 10 chars ASCII, paddat cu spații la dreapta)
        //    - byte 22:     tip (0=CREDIT, 1=DEBIT)
        //    - byte 23:     status (0=PENDING, 1=PROCESSED, 2=REJECTED)
        //    - bytes 24-31: padding (zerouri)
        // 3. Procesează comenzile din stdin până la EOF cu RandomAccessFile:
        //    - READ idx       → seek(idx * RECORD_SIZE), citește și afișează înregistrarea
        //    - UPDATE idx ST  → seek(idx * RECORD_SIZE + 23), scrie noul status (0/1/2)
        //                       afișează "Updated [idx]: STATUS"
        //    - PRINT_ALL      → citește și afișează toate înregistrările
        //
        // Format linie output:
        //   [idx] id=<id> data=<data> tip=<CREDIT|DEBIT> suma=<suma:.2f> RON status=<STATUS>

        Scanner in = new Scanner(System.in);
        int N = Integer.parseInt( in.nextLine());
        DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(OUTPUT_FILE));

        for (int i =1 ;i<=N; i++){
            String[] line = in.nextLine().split(" ");
            byte[] inti = new byte[4];
            byte[] doubli = new byte[8];

            inti = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(Integer.parseInt(line[0])).array();
            doubli = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putDouble(Double.parseDouble(line[1])).array();

            outputStream.write(inti);
            outputStream.write(doubli);

            outputStream.write(line[2].getBytes());

            if (TipTranzactie.valueOf(line[3]) == TipTranzactie.CREDIT){
                outputStream.writeByte(0);
            } else{
                outputStream.writeByte(1);
            }

            //mereu va fi 0 la initializare pentru status
            outputStream.writeByte(0);

            outputStream.write(new byte[8]); //padding

        }

        RandomAccessFile raf = new RandomAccessFile(OUTPUT_FILE, "rw");

        while (in.hasNext()) {
            String[] comanda = in.nextLine().split(" ");
            int idx;
            byte[] buffer = new byte[RECORD_SIZE];

            if(Objects.equals(comanda[0], "READ")){
                idx = Integer.parseInt(comanda[1]);
                raf.seek(idx * RECORD_SIZE);

                raf.read(buffer);

                System.out.println("[" + idx + "] " + bytesToString(buffer));


            } else if (Objects.equals(comanda[0], "UPDATE")) {
                idx = Integer.parseInt(comanda[1]);
                Status status = Status.valueOf(comanda[2]);

                raf.seek(idx * RECORD_SIZE + 23);

                if (status == Status.PENDING){
                    raf.write(0);
                } else if (status == Status.PROCESSED) {
                    raf.write(1);
                } else if (status == Status.REJECTED) {
                    raf.write(2);
                }

                System.out.println("Updated [" + idx + "]: " + status);

            } else if (Objects.equals(comanda[0], "PRINT_ALL")) {
                for (idx =0 ; idx< N; idx++ ){
                    raf.seek(idx * RECORD_SIZE);
                    raf.read(buffer);
                    System.out.println("[" + idx + "] " + bytesToString(buffer));
                }
            }
        }


        //System.out.println("TODO: implementează exercițiul 2");
    }
}
