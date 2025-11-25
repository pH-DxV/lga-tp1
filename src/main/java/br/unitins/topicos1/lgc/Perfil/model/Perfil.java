package br.unitins.topicos1.lgc.Perfil.model;

public enum Perfil {

    ADM (1l, "Administrador"),
    USER (2l, "Usuário");

    public final Long ID;
    public final String LABEL;

    Perfil(Long id, String label) {
        this.ID = id;
        this.LABEL = label;
    }

    public static Perfil valueOf(Long id) {
        if (id == null)
            return null;
        
        for (Perfil perfil : Perfil.values())
            if (perfil.ID.equals(id))
                return perfil;
        
        throw new IllegalArgumentException("id inválido");
    }

}