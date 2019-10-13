package br.com.lps.fmp.model;

/**
 * Created by Luiz Paulo Oliveira on 30/03/2017.
 */

public class ItemLista {

    private int icone;
    private String titulo;
    private boolean exibirSwitch;

    public ItemLista(int icone, String titulo, boolean exibirSwitch) {
        this.icone = icone;
        this.titulo = titulo;
        this.exibirSwitch = exibirSwitch;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getIcone() {
        return icone;
    }

    public void setIcone(int icone) {
        this.icone = icone;
    }

    public boolean isExibirSwitch() {
        return exibirSwitch;
    }

    public void setExibirSwitch(boolean exibirSwitch) {
        this.exibirSwitch = exibirSwitch;
    }
}
