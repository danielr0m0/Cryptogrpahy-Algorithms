package main;

import java.io.File;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

public class RSAEncrypt {

    public static void main(String[] args) throws Exception {
        BigInteger e = BigInteger.ZERO;
        BigInteger n = BigInteger.ZERO;

        if(args.length==2){
            File text = new File(args[0]);
            File pubKey = new File(args[1]);

            if(!pubKey.exists()){
                System.out.println("public key file not found");
                System.exit(-1);
            }else if(!text.exists()){
                System.out.println("text file not found");
                System.exit(-1);
            }

            Scanner textScanner = new Scanner(text);
            Scanner keyScanner = new Scanner(pubKey);
            //get values form key e and n
            while (keyScanner.hasNext()) {
                String line = keyScanner.nextLine();
                if (line.startsWith("e")) {
                    String[] parsed = line.split(" ");
                    e = new BigInteger(parsed[2]);
                } else if (line.startsWith("n")) {
                    String[] parsed = line.split(" ");
                    n = new BigInteger(parsed[2]);
                }
            }


            //write to file
            File encrypt = new File("test.enc");
            if(!encrypt.exists()){
                encrypt.createNewFile();
            }

            PrintWriter writer = new PrintWriter(encrypt);
            while(textScanner.hasNext()){
                String message = textScanner.nextLine();
                message.toUpperCase();

                writer.write(encrypt(message,e,n) +"\n");
            }
            writer.close();

        }else {
            System.out.println("please input file to encrypt then public key file");
        }

    }

    //m^e mod n m being the text in numbers
    //text is in 3 blocks so 6 characters in each block
    //needs padding at the end 00-31
    public static String encrypt(String text, BigInteger e, BigInteger n) throws Exception{
        int sizeOfCipher= (""+n).length();

        for (int i = 0; i < text.length()%3; i++) {
            text+=" ";
        }

        text = text.toUpperCase();
        String cipher = "";
        for (int i = 0; i < text.length(); i+=3) {
            String block = convert(text.charAt(i))+convert(text.charAt(i+1))+convert(text.charAt(i+2));
            BigInteger m =  BigInteger.valueOf((long)Integer.parseInt(block));
            //this is crypted message
            BigInteger cm = m.pow(e.intValue()).mod(n);
            String c = ""+cm;
            //add leading 0 to string
            if(c.length()!=sizeOfCipher){
                String padding="";
                for (int j = 0; j < sizeOfCipher-c.length(); j++) {
                    padding+="0";
                }
                c= padding+c;
            }
            cipher+=c;
        }

        return cipher;
    }


    public static String convert(char c) throws Exception {
        String Space = "00";
        String A = "01";
        String B = "02";
        String C = "03";
        String D = "04";
        String E = "05";
        String F = "06";
        String G = "07";
        String H = "08";
        String I = "09";
        String J = "10";
        String K = "11";
        String L = "12";
        String M = "13";
        String N = "14";
        String O = "15";
        String P = "16";
        String Q = "17";
        String R = "18";
        String S = "19";
        String T = "20";
        String U = "21";
        String V = "22";
        String W = "23";
        String X = "24";
        String Y = "25";
        String Z = "26";
        String Comma = "27";
        String Question = "28";
        String Colon = "29";
        String Period = "30";
        String Apostrophe = "31";
        switch ( c )
        {
            case 'A':
                return A;

            case 'B':
                return B;

            case 'C':
                return C;

            case 'D':
                return D;

            case 'E':
                return E;

            case 'F':
                return F;

            case 'G':
                return G;

            case 'H':
                return H;

            case 'I':
                return I;

            case 'J':
                return J;

            case 'K':
                return K;

            case 'L':
                return L;

            case 'M':
                return M;

            case 'N':
                return N;

            case 'O':
                return O;

            case 'P':
                return P;

            case 'Q':
                return Q;

            case 'R':
                return R;

            case 'S':
                return S;

            case 'T':
                return T;

            case 'U':
                return U;

            case 'V':
                return V;

            case 'W':
                return W;

            case 'X':
                return X;

            case 'Y':
                return Y;

            case 'Z':
                return Z;

            case '?':
                return Question;

            case '\'':
                return Apostrophe;

            case ':':
                return Colon;

            case ',':
                return Comma;

            case '.':
                return Period;

            case ' ':
                return Space;

            default:
                throw new Exception("message not in rage");
        }
    }
}
