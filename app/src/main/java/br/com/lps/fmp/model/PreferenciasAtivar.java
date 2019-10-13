package br.com.lps.fmp.model;

/**
 * Created by Luiz Paulo Oliveira on 01/04/2017.
 */

public class PreferenciasAtivar {

    public static final int RC_CAMERA = 100;
    public static final int RC_SMS = 110;

    public static final String CHAMADAS = "chamadas";
    public static final String NOTIFICACAO = "notificacao";
    public static final String MULTIMIDIA = "multimidia";
    public static final String DADOS_MOVEIS = "dados_moveis";
    public static final String WIRELESS = "wireless";
    public static final String LANTERNA = "lanterna";
    public static final String PALAVRA_CHAVE = "key_word";

    private boolean toqueChamadas;
    private boolean somNotificacao;
    private boolean sonsMultimidias;
    private boolean dadosMoveis;
    private boolean wireless;
    private boolean lanterna;

    public boolean isToqueChamadas() {
        return toqueChamadas;
    }

    public void setToqueChamadas(boolean toqueChamadas) {
        this.toqueChamadas = toqueChamadas;
    }

    public boolean isSomNotificacao() {
        return somNotificacao;
    }

    public void setSomNotificacao(boolean somNotificacao) {
        this.somNotificacao = somNotificacao;
    }

    public boolean isSonsMultimidias() {
        return sonsMultimidias;
    }

    public void setSonsMultimidias(boolean sonsMultimidias) {
        this.sonsMultimidias = sonsMultimidias;
    }

    public boolean isDadosMoveis() {
        return dadosMoveis;
    }

    public void setDadosMoveis(boolean dadosMoveis) {
        this.dadosMoveis = dadosMoveis;
    }

    public boolean isWireless() {
        return wireless;
    }

    public void setWireless(boolean wireless) {
        this.wireless = wireless;
    }

    public boolean isLanterna() {
        return lanterna;
    }

    public void setLanterna(boolean lanterna) {
        this.lanterna = lanterna;
    }
}
