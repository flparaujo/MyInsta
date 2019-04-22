package com.parse.starter.util;

import com.parse.ParseException;

import java.util.*;

public class ParseErrors {

    private Map<Integer, String> erros;

    public ParseErrors() {
        this.erros = new HashMap<>();
        this.erros.put(ParseException.USERNAME_TAKEN, "usuário já cadastrado!");
        this.erros.put(ParseException.EMAIL_TAKEN, "email já cadastrado!");
        this.erros.put(ParseException.USERNAME_MISSING, "Insira um nome de usuário!");
        this.erros.put(ParseException.EMAIL_MISSING, "Insira um email válido!");
        this.erros.put(ParseException.INVALID_EMAIL_ADDRESS, "Insira um email válido!");
        this.erros.put(ParseException.PASSWORD_MISSING, "Insira uma senha!");
    }

    public String getErro(int codigo) {
        return this.erros.get(codigo);
    }

}