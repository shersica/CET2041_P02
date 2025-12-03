package controller;

import entities.Department;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.DepartmentService;
import java.util.List;

/**
 * REST controller exposing endpoints for {@code Department}-related operations
 */
@Path("/departments")
public class DepartmentController {

    /**
     * Service responsible for handling {@code Department}-related business logic.
     */
    private final DepartmentService departmentService = new DepartmentService();

    /**
     * Display a list of departments in the company and returns a success message.
     * @return list of departments
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDepartments() {
        List<Department> departments = departmentService.findAllDepartments();
        return Response.ok().entity(departments).build();
    }

}
