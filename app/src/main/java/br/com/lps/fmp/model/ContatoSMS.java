package br.com.lps.fmp.model;

/**
 * Created by Luiz Paulo Oliveira on 01/04/2017.
 */

public class ContatoSMS {
    private String numeroTelefone;
    private String palavraChave;

    public ContatoSMS() { }

    public ContatoSMS(String numeroTelefone, String palavraChave) {
        this.numeroTelefone = numeroTelefone;
        this.palavraChave = palavraChave;
    }

    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }

    public String getPalavraChave() {
        return palavraChave;
    }

    public void setPalavraChave(String palavraChave) {
        this.palavraChave = palavraChave;
    }
}
