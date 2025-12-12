package br.unitins.topicos1.lgc.Security.service;

import br.unitins.topicos1.lgc.Usuario.model.Usuario;

public interface SecurityService {

    /**
     * Valida se o usuário logado tem permissão para acessar o recurso.
     * Permite acesso se for Administrador ou se for o dono do recurso.
     * * @param donoDoRecurso O usuário que é dono do dado que está sendo acessado.
     * @throws jakarta.ws.rs.ForbiddenException se não tiver permissão.
     */
    void validarPermissao(Usuario donoDoRecurso);

    /**
     * Sobrecarga para validar apenas pelo ID do usuário.
     * Útil quando você ainda não buscou o objeto Usuario completo.
     * * @param idUsuarioAlvo O ID do usuário dono do dado.
     * @throws jakarta.ws.rs.ForbiddenException se não tiver permissão.
     */
    void validarPermissao(Long idUsuarioAlvo);
}