package br.com.alura.dominio;

public abstract class Domain {

    protected Long id;

    public Domain() {
    }

    public Domain(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
