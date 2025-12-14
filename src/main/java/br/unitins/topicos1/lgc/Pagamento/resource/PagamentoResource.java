package br.unitins.topicos1.lgc.Pagamento.resource;

import org.jboss.logging.Logger;

import br.unitins.topicos1.lgc.Pagamento.dto.PagamentoBoletoDTO;
import br.unitins.topicos1.lgc.Pagamento.dto.PagamentoBoletoDTOResponse;
import br.unitins.topicos1.lgc.Pagamento.dto.PagamentoCartaoDTO;
import br.unitins.topicos1.lgc.Pagamento.dto.PagamentoCartaoDTOResponse;
import br.unitins.topicos1.lgc.Pagamento.dto.PagamentoPixDTO;
import br.unitins.topicos1.lgc.Pagamento.dto.PagamentoPixDTOResponse;
import br.unitins.topicos1.lgc.Pagamento.service.PagamentoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/pagamentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PagamentoResource {

    @Inject
    PagamentoService service;

    private static final Logger LOG = Logger.getLogger(PagamentoResource.class);

    @POST
    @Path("/cartao")
    @RolesAllowed({"Usuario", "Administrador"})
    @Transactional
    public Response pagarCartao(@Valid PagamentoCartaoDTO dto) {
        LOG.info("INICIANTO METODO pagarCartao");
        PagamentoCartaoDTOResponse response = service.pagarCartao(dto);
        return Response.status(201).entity(response).build();
    }

    @POST
    @Path("/pix")
    @RolesAllowed({"Usuario", "Administrador"})
    @Transactional
    public Response pagarPix(@Valid PagamentoPixDTO dto) {
        LOG.info("INICIANTO METODO pagarPix");
        PagamentoPixDTOResponse response = service.pagarPix(dto);
        return Response.status(201).entity(response).build();
    }

    @POST
    @Path("/boleto")
    @RolesAllowed({"Usuario", "Administrador"})
    @Transactional
    public Response pagarBoleto(@Valid PagamentoBoletoDTO dto) {
        LOG.info("INICIANTO METODO pagarBoleto");
        PagamentoBoletoDTOResponse response = service.pagarBoleto(dto);
        return Response.status(201).entity(response).build();
    }
}