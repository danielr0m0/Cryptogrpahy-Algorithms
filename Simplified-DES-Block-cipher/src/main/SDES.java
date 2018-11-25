package main;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SDES {

    public static void main(String[] args) {

    ArrayList<byte[][]> table= new ArrayList<>();
        table.add(new byte[][]{{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},null});
        table.add(new byte[][]{{1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},null});
        table.add(new byte[][]{{0,0,0,0,0,1,1,1,1,1},{0,0,0,0,0,0,0,0},null});
        table.add(new byte[][]{{0,0,0,0,0,1,1,1,1,1},{1,1,1,1,1,1,1,1},null});
        table.add(new byte[][]{{1,0,0,0,1,0,1,1,1,0},null,{0,0,0,1,1,1,0,0}});
        table.add(new byte[][]{{1,0,0,0,1,0,1,1,1,0},null,{1,1,0,0,0,0,1,0}});
        table.add(new byte[][]{{0,0,1,0,0,1,1,1,1,1},null,{1,0,0,1,1,1,0,1}});
        table.add(new byte[][]{{0,0,1,0,0,1,1,1,1,1},null,{1,0,0,1,0,0,0,0}});

        System.out.println("   Key   \t pText  \t cText");
        for (byte[][] row: table) {
            print(row[0]);
            System.out.print("\t");
            if(row[1]==null){
                row[1]=Decrypt(row[0],row[2]);
                print(row[1]);
                System.out.print("\t");
                print(row[2]);

            }
            else if(row[2]==null){
                print(row[1]);
                System.out.print("\t");
                row[2]= Encrypt(row[0],row[1]);
                print(row[2]);

            }
            System.out.println("\n");
        }

    }

    public static byte[] Encrypt(byte[] rawkey, byte[] plaintext){
        byte[] ciphertext;
        byte[] ki= shift(p10(rawkey));
        byte[]k1= p8(ki);
        byte[]k2= p8(shift(shift(ki)));
        ciphertext=IPinverse(fK(swap(fK(IP(plaintext), k1)),k2));
        return ciphertext;
    }

    public static  byte[] Decrypt(byte[]rawkey, byte[]ciphertext){
        byte[] plaintext;
        byte[] ki= shift(p10(rawkey));
        byte[]k1= p8(ki);
        byte[]k2= p8(shift(shift(ki)));

        plaintext= IPinverse(fK(swap(fK(IP(ciphertext), k2)),k1));
        return plaintext;
    }

    public static byte[] p10(byte[] rawKey){
        byte[] p10= {3,5,2,7,4,10,1,9,8,6};
        byte[]key=new byte[10];
        for (int i = 0; i < p10.length; i++) {
            key[i] = rawKey[p10[i] - 1];
        }
        return key;
    }

    public static byte[] p8(byte[] rawKey){
        byte[] p8 = {6,3,7,4,8,5,10,9};
        byte[] key = new byte[p8.length];
        for (int i = 0; i < p8.length; i++) {
            key[i] = rawKey[p8[i] - 1];
        }
        return key;
    }

    public static byte[]p4(byte[] text){
        byte[] p4 = {2,4,3,1};
        byte[] p4Text = new byte[p4.length];
        for (int i = 0; i < p4.length; i++) {
            p4Text[i] = text[p4[i] - 1];
        }
        return p4Text;
    }

    public static byte[] IP(byte[] text){
        byte[] IP={2,6,3,1,4,8,5,7};
        byte[] IPtext= new byte[IP.length];
        for (int i = 0; i < text.length; i++) {
            IPtext[i]= text[IP[i]-1];
        }
        return IPtext;
    }

    public static byte[] IPinverse(byte[] text){
        byte[] IPi={4,1,3,5,7,2,8,6};
        byte[] IPtext= new byte[IPi.length];
        for (int i = 0; i < text.length; i++) {
            IPtext[i]= text[IPi[i]-1];
        }
        return IPtext;
    }

    public static byte[] shift(byte[] key){
        byte[] lh = lh(key);
        byte[] rh = rh(key);

        byte lhTemp= lh[0];
        byte rhTemp= rh[0];

        for (int i = 0; i < lh.length; i++) {
            if (i == (key.length / 2) - 1) {
                lh[i] = lhTemp;
                rh[i] = rhTemp;
            } else{
                lh[i] = lh[i + 1];
                rh[i]= rh[i+1];
            }
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            output.write(lh);
            output.write(rh);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output.toByteArray();
    }

    public static byte[] fK(byte[] text, byte[] SK){
        byte[] f = F(rh(text),SK);
        byte[] lh = lh(text);
        byte[] results = new byte[text.length];
        for (int i = 0; i < results.length; i++) {
            if(i<results.length/2)
                results[i]= (byte) (lh[i] ^ f[i]);
            else
                results[i]= text[i];
        }
        return results;
    }

    public static byte[] F(byte[] R, byte[]SK){

        byte[] expansionPermutation= {4,1,2,3,2,3,4,1};
        byte[] ep= new byte[expansionPermutation.length];
        for (int i = 0; i < ep.length; i++) {
            ep[i]= (byte)(R[expansionPermutation[i]-1] ^ SK[i]);
        }
        return  p4(sBox(ep));
    }

    public static byte[] sBox(byte[] text){
        byte[] lh= lh(text);
        int s0row= byteArrToInt(new byte[]{lh[0],lh[3]});
        int s0column= byteArrToInt(new byte[]{lh[1],lh[2]});
        byte[] results;

        int[][] s0= {{1,0,3,2},
                     {3,2,1,0},
                     {0,2,1,3},
                     {3,1,3,2}};

        byte[] rh= rh(text);

        int s1row= byteArrToInt(new byte[]{rh[0],rh[3]});
        int s1column= byteArrToInt(new byte[]{rh[1],rh[2]});
        int[][] s1= {{0,1,2,3},
                {2,0,1,3},
                {3,0,1,0},
                {2,1,0,3}};

        results = intArrToByteArrB2(new int[]{s0[s0row][s0column], s1[s1row][s1column]});
        return results;
    }

    public static byte[] lh(byte[] text){
        byte[] lh = new byte[(text.length/2)];
        for (int i = 0; i < text.length; i++) {
            if(i<text.length/2)
                lh[i]=text[i];
        }
        return lh;
    }

    public static byte[] rh(byte[] text){
        byte[] rh = new byte[text.length/2];
        for (int i = 0; i < text.length; i++) {
            if(i>=(text.length/2))
                rh[i%(text.length/2)]=text[i];
        }
        return rh;
    }

    public static int byteArrToInt(byte[] b){
        int n= 0;
        for (int i = 0; i < b.length; i++) {
            if(b[b.length-i-1]==1) {
                n += Math.pow(2,i);
            }
        }
        return n;
    }

    public static byte[] intArrToByteArrB2(int[] n){
        String intToByte = "";
        for (int i = 0; i < n.length; i++) {
            if(Integer.toBinaryString(n[i]).length()<2)
                intToByte +="0"+Integer.toBinaryString(n[i]);
            else
                intToByte +=Integer.toBinaryString(n[i]);
        }
        byte[] results = new byte[intToByte.length()];
        for (int i = 0; i < results.length; i++) {
            results[i]=(byte)Integer.parseInt(intToByte.charAt(i)+"");
        }
        return results;
    }

    public static byte[] swap(byte[] text){
        byte[] swap = new byte[text.length];
        byte[] lh= lh(text);
        byte[]rh= rh(text);
        System.arraycopy(rh,0,swap,0,rh.length);
        System.arraycopy(lh, 0,swap,rh.length,lh.length);
        return swap;
    }

    public static void print(byte[] b){
        for (int i = 0; i < b.length; i++) {
            System.out.print(b[i]);
        }
    }

}
