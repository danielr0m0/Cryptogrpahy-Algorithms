package main;

import java.io.File;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

public class RSADecrypt {

    public static void main(String[] args) throws Exception {
        BigInteger n = BigInteger.ZERO;
        BigInteger d = BigInteger.ZERO;
        if(args.length==2){
            File cipher = new File(args[0]);
            File key = new File(args[1]);

            if(!key.exists()){
                System.out.println("key file not found");
                System.exit(-1);
            }else if(!cipher.exists()){
                System.out.println("cipher file not found");
                System.exit(-1);
            }


            Scanner textScanner = new Scanner(cipher);
            Scanner keyScanner = new Scanner(key);
            //get values form key e and n
            while (keyScanner.hasNext()) {
                String line = keyScanner.nextLine();
                if (line.startsWith("d")) {
                    String[] parsed = line.split(" ");
                    d = new BigInteger(parsed[2]);
                } else if (line.startsWith("n")) {
                    String[] parsed = line.split(" ");
                    n = new BigInteger(parsed[2]);
        }
    }



    File decrypt = new File("test.dec");
            if(!decrypt.exists()){
        decrypt.createNewFile();
    }



    PrintWriter writer = new PrintWriter(decrypt);
            while(textScanner.hasNext()){
                String message = textScanner.nextLine();

                writer.write(decrypt(message,d,n) +"\n");
            }
            writer.close();

        }else{
            System.out.println("enter cipher and key");

        }
    }

    private static String decrypt(String message, BigInteger d, BigInteger n) throws Exception {
        String text ="";
        int sizeOfCipher= (""+n).length();
        for (int i = 0; i < message.length(); i+=sizeOfCipher) {
            BigInteger c = new BigInteger(message.substring(i,i+sizeOfCipher));
            String m= ""+c.pow(d.intValue()).mod(n);
            String zero="";
            for (int j = 0; j < 6-m.length(); j++) {
                zero+="0";
            }
            m = zero+m;



            for (int j = 0; j < m.length(); j+=2) {
                text+= convert(Integer.parseInt(m.substring(j,j+2)));
            }
        }
        return text;
    }

    public static char convert(int val) throws Exception {
        switch (val) {
            case 0:
                return ' ';
            case 1:
                return 'A';
            case 2:
                return 'B';
            case 3:
                return 'C';
            case 4:
                return 'D';
            case 5:
                return 'E';
            case 6:
                return 'F';
            case 7:
                return 'G';
            case 8:
                return 'H';
            case 9:
                return 'I';
            case 10:
                return 'J';
            case 11:
                return 'K';
            case 12:
                return 'L';
            case 13:
                return 'M';
            case 14:
                return 'N';
            case 15:
                return 'O';
            case 16:
                return 'P';
            case 17:
                return 'Q';
            case 18:
                return 'R';
            case 19:
                return 'S';
            case 20:
                return 'T';
            case 21:
                return 'U';
            case 22:
                return 'V';
            case 23:
                return 'W';
            case 24:
                return 'X';
            case 25:
                return 'Y';
            case 26:
                return 'Z';
            case 27:
                return ',';
            case 28:
                return '?';
            case 29:
                return ':';
            case 30:
                return '.';
            case 31:
                return '\'';
            default:
                throw new Exception("symbol not found");
        }
    }
}
