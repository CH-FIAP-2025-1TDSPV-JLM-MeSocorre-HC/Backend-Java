package br.com.hc.resource;

import br.com.hc.dao.PacienteDao;
import br.com.hc.dto.paciente.CadastroPacienteDto;
import br.com.hc.dto.paciente.DetalhesPacienteDto;
import br.com.hc.model.paciente.LoginSenha;
import br.com.hc.model.paciente.Paciente;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@Path("/pacientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PacienteResource {

    @Inject
    PacienteDao pacienteDao;

    @Inject
    ModelMapper modelMapper;

    // =========================
    // LISTAR TODOS
    // =========================
    @GET
    public List<DetalhesPacienteDto> listar() throws SQLException {
        return pacienteDao.listar()
                .stream()
                .map(p -> modelMapper.map(p, DetalhesPacienteDto.class))
                .toList();
    }

    // =========================
    // BUSCAR POR ID
    // =========================
    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") int id) throws SQLException {
        Paciente paciente = pacienteDao.buscar(id);
        if (paciente == null) {
            throw new NotFoundException("Paciente não encontrado");
        }
        return Response.ok(modelMapper.map(paciente, DetalhesPacienteDto.class)).build();
    }

    // =========================
    // CADASTRAR
    // =========================
    @POST
    public Response cadastrar(@Valid CadastroPacienteDto dto, @Context UriInfo uriInfo) throws SQLException {
        Paciente paciente = modelMapper.map(dto, Paciente.class);
        paciente.setLoginSenha(new LoginSenha(null, dto.getUsuario(), dto.getSenha()));
        pacienteDao.cadastrar(paciente);

        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(paciente.getId()))
                .build();

        return Response.created(uri)
                .entity(modelMapper.map(paciente, DetalhesPacienteDto.class))
                .build();
    }

    // =========================
// ATUALIZAR
// =========================
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, @Valid CadastroPacienteDto dto)
            throws SQLException {
        Paciente pacienteExistente = pacienteDao.buscar(id);
        if (pacienteExistente == null) {
            throw new NotFoundException("Paciente não encontrado");
        }

        // Atualiza os campos
        pacienteExistente.setNome(dto.getNome());
        pacienteExistente.setCpf(dto.getCpf());
        pacienteExistente.setIdade(dto.getIdade());
        pacienteExistente.getLoginSenha().setUsuario(dto.getUsuario());
        pacienteExistente.getLoginSenha().setSenha(dto.getSenha());

        pacienteDao.atualizar(pacienteExistente);
        return Response.ok(modelMapper.map(pacienteExistente, DetalhesPacienteDto.class)).build();
    }


    // =========================
    // DELETAR
    // =========================
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) throws SQLException {
        pacienteDao.deletar(id);
        return Response.noContent().build();
    }
}
