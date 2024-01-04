package br.com.alura.dominio;

import java.util.List;

public class Abrigo extends Domain{

    private String nome;
    private String telefone;
    private String email;
    private List<Pet> pets;

    public Abrigo() {}

    public Abrigo(Long id, String nome, String telefone, String email, List<Pet> pets) {
        super(id);
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.pets = pets;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public void imprimirAbrigoCadastrado(){
        System.out.println(this.getId() + " - " + this.getNome());
    }
}
