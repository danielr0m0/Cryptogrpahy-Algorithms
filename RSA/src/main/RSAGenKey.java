package main;

import java.io.*;
import java.math.BigInteger;
import java.util.Random;

public class RSAGenKey {

    public static void main(String[] args) {
	// write your code here
        int k=0;
        BigInteger q = BigInteger.ONE;
        BigInteger p= BigInteger.ONE;
        BigInteger e = BigInteger.ONE;
        BigInteger n;
        BigInteger d;
        BigInteger z;

        if(args.length ==1 ){
            //size of the key randomly picks p and q in k bit
            try{
                k = Integer.parseInt(args[0]);
            }catch (Exception ex){
                System.out.println("please put a bit size");
            }

            p = BigInteger.probablePrime(k, new Random());
            q = BigInteger.probablePrime(k, new Random());

        }else if(args.length == 3){
            //inputs are p,q,e
            try{
                p=new BigInteger(args[0]);
                q=new BigInteger(args[1]);
                e=new BigInteger(args[2]);
            }catch (Exception ex){
                System.out.println("please put your p q e values ");
            }

        }else {
            System.out.println("please put a bit size or your p q e values ");
        }

        //compute n
        n = p.multiply(q);
        //compute z
        z= p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        //compute e

        if(e.equals(BigInteger.ONE)){
            boolean relativePrime= false;
            e=BigInteger.probablePrime(k, new Random());
            while(!relativePrime){
                if(gcd(e,z).equals(BigInteger.ONE)){
                    relativePrime=true;
                }else
                    e=BigInteger.probablePrime(k, new Random());
            }
        }

        //compute d
        d= e.modInverse(z);


        //write to file
        writeKeys(n,e,d);
    }

    public static BigInteger gcd(BigInteger a, BigInteger b) {
        BigInteger t;
        while(b != BigInteger.ZERO){
            t = a;
            a = b;
            b = t.mod(b);
        }
        return a;

    }

    public static void writeKeys(BigInteger n, BigInteger e, BigInteger d){
        File publicKey= new File("pub_key.txt");
        File privateKey= new File("pri_key.txt");
        if(!publicKey.exists() && !privateKey.exists()){
            try{
                publicKey.createNewFile();
                privateKey.createNewFile();
            }catch (IOException e1) {
                e1.printStackTrace();
            }
        }


        try{
            PrintWriter privateWriter = new PrintWriter(privateKey);
            privateWriter.write("d = "+ d+"\nn = "+n);
            privateWriter.close();

            PrintWriter publicWriter = new PrintWriter(publicKey);
            publicWriter.write("e = "+ e+"\nn = "+n);
            publicWriter.close();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }



}
