package br.com.controleprocessos.view.user;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.controleprocessos.business.domain.UserCP;
import br.com.controleprocessos.business.service.user.UserService;
import br.com.controleprocessos.view.utils.GsonUtils;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

@Path("/users")
public class UserRS {

	private GsonUtils gsonUtils = new GsonUtils();

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response getAll() {
		return Response.ok(gsonUtils.toJson(new UserService().getAll())).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response getById(@PathParam("id") Long id) {
		return Response.ok(gsonUtils.toJson(new UserService().getById(id))).build();
	}

	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response newUser(@FormParam("user") String userJson) {
		return Response.ok(gsonUtils.toJson(new UserService().save(new Gson().fromJson(new JsonParser().parse(userJson), UserCP.class)))).build();
	}

	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response editUser(@PathParam("id") Long id, @FormParam("user") String userJson) {
		UserCP user = new Gson().fromJson(new JsonParser().parse(userJson), UserCP.class);
		user.setId(id);
		return Response.ok(gsonUtils.toJson(new UserService().save(user))).build();
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response deleteById(@PathParam("id") Long id) {
		return Response.ok(gsonUtils.toJson(new UserService().remove(id))).build();
	}

}
