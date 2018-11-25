package main;

import java.util.ArrayList;

public class TripleSDES {

    public static void main(String[] args) {
        ArrayList<byte[][]> table= new ArrayList<>();
        table.add(new byte[][]{{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},null});
        table.add(new byte[][]{{1,0,0,0,1,0,1,1,1,0},{0,1,1,0,1,0,1,1,1,0},{1,1,0,1,0,1,1,1},null});
        table.add(new byte[][]{{1,0,0,0,1,0,1,1,1,0},{0,1,1,0,1,0,1,1,1,0},{1,0,1,0,1,0,1,0},null});
        table.add(new byte[][]{{1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1},{1,0,1,0,1,0,1,0},null});
        table.add(new byte[][]{{1,0,0,0,1,0,1,1,1,0},{0,1,1,0,1,0,1,1,1,0},null,{1,1,1,0,0,1,1,0}});
        table.add(new byte[][]{{1,0,1,1,1,0,1,1,1,1},{0,1,1,0,1,0,1,1,1,0},null,{0,1,0,1,0,0,0,0}});
        table.add(new byte[][]{{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},null,{1,0,0,0,0,0,0,0}});
        table.add(new byte[][]{{1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1},null,{1,0,0,1,0,0,1,0}});

        System.out.println("   Key1   \t    Key2   \t pText  \t cText");
        for (byte[][] row: table) {
            byte[] key1 = row[0];
            byte[] key2 = row[1];
            print(key1);
            System.out.print("\t");
            print(key2);
            System.out.print("\t");
            if(row[2]==null){
                print(Decrypt(key1,key2,row[3]));
                System.out.print("\t");
                print(row[3]);
            }
            else if(row[3]==null){
                print(row[2]);
                System.out.print("\t");
                print(Encrypt(key1,key2,row[2]));
            }
            System.out.println("\n");
        }
    }

    public static byte[] Encrypt( byte[] rawkey1, byte[] rawkey2, byte[] plaintext ){
        return SDES.Encrypt(rawkey1,SDES.Decrypt(rawkey2,SDES.Encrypt(rawkey1,plaintext)));
    }

    public static byte[] Decrypt( byte[] rawkey1, byte[] rawkey2, byte[] ciphertext ){
        return SDES.Decrypt(rawkey1,SDES.Encrypt(rawkey2,SDES.Decrypt(rawkey1,ciphertext)));
    }

    public static void print(byte[] b){
        for (int i = 0; i < b.length; i++) {
            System.out.print(b[i]);
        }
    }
}
