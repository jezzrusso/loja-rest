package br.com.alura.loja.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Projeto;

@Path("projetos")
public class ProjetoResource {

	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String busca(@PathParam("id") long id) {
		Projeto projetoBuscado = new ProjetoDAO().busca(id);

		return projetoBuscado.toXml();
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response adiciona(String conteudo) {
		XStream xstream = new XStream();
		xstream.alias("projeto", Projeto.class);
		Projeto projeto = (Projeto) xstream.fromXML(conteudo);
		URI uri = URI.create("/projetos/" + projeto.getId());
		new ProjetoDAO().adiciona(projeto);
		return Response.created(uri).build();

	}

}