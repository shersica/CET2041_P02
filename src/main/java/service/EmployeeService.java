package service;

import entities.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import repository.DepartmentRepository;
import repository.EmployeeRepository;

import java.util.List;

@Path("/employees")
public class EmployeeService {

    private final EmployeeRepository empRepository = new EmployeeRepository();
    private final DepartmentRepository deptRepository = new DepartmentRepository();

    @GET
    @Path("/ping")
    public Response ping() {
        return Response.ok().entity("Server online.").build();
    }

    // ENDPOINT 1
    // GET ALL DEPARTMENT NUMBER AND NAME
    @GET
    @Path("/departments")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Department> getDepartments() {
        return deptRepository.findAllDepartments();
    }

    // ENDPOINT 2
    // GET FULL EMPLOYEE RECORD BY ID
    @GET
    @Path("/full_record/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Employee getFullRecord(@PathParam("id") Long id) {
        return empRepository.findEmpRecordById(id);
    }
}
