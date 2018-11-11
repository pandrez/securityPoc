package io.mb.sellercenter.security.util;

import io.mb.sellercenter.security.auth.basicauth.BasicAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class HashPasswords {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please specify the password");
            return;
        }
        System.out.println("Hash for " + args[0] + " is: " + BCrypt.hashpw(args[0], BCrypt.gensalt(BasicAuthenticationConfigurerAdapter.PASSWORD_HASH_STRENGTH)));
    }
}
