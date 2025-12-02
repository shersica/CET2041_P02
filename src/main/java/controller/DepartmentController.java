package controller;

import entities.Department;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.DepartmentService;
import util.JPAUtil;
import java.util.List;

@Path("/departments")
public class DepartmentController {

    private final DepartmentService departmentService = new DepartmentService();


    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDepartments() {
        List<Department> departments = departmentService.findAllDepartments();
        return Response.ok().entity(departments).build();
    }

}
