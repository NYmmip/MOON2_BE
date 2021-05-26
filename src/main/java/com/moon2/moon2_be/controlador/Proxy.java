package com.moon2.moon2_be.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

//@RestController
//@EnableAutoConfiguration
@RequestMapping(path = "api/v1/Proxy")
public class Proxy implements InterfaceProxy{

    private Operaciones operaciones= Operaciones.crearIFacade();

    private final String user;
    private String pass;

    public Proxy(String user, String pass) throws NoSuchAlgorithmException {
        this.user=user;
        this.pass=pass;
    }


    @Override
    public byte[] performOperation(){
        try {
            if(this.operaciones.login(user,pass)){
                System.out.println("Successful Login");
                return this.encrypt(user,operaciones.performOperation());
            }
            System.out.println("Failed Login");
            return null;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println("Failed Login");
            return null;
        }
    }

    private byte[] encrypt(String data, String publicKey){
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            X509EncodedKeySpec temp = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey key = keyFactory.generatePublic(temp);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(data.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            return null;
        } catch (BadPaddingException e) {
            e.printStackTrace();
            return null;
        }

    }
}
