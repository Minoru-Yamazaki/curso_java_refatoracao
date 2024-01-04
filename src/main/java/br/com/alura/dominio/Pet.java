package br.com.alura.dominio;

public class Pet extends Domain{

    private String tipo;
    private String nome;
    private String raca;
    private int idade;
    private String cor;
    private Float peso;

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNome() {
        return nome;
    }

    public String getRaca() {
        return raca;
    }

    public int getIdade() {
        return idade;
    }

    public String getCor() {
        return cor;
    }

    public Float getPeso() {
        return peso;
    }

    public void imprimirPetCadastrado(){
        System.out.println(this.getId() + " - " + this.getTipo() + " - " + this.getNome() + " - " + this.getRaca() + " - " + this.getIdade() + " ano(s)");
    }
}
