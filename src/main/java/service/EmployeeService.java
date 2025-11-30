package service;

import entities.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import repository.*;

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
    @Path("/all_departments")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Department> getDepartments() {
        return deptRepository.findAllDepartments();
    }

    // ENDPOINT 2
    // GET FULL EMPLOYEE RECORD BY ID
    @GET
    @Path("/full_record/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee getFullRecord(@PathParam("id") Long id) {
        return empRepository.findEmpRecordById(id);
    }

    // ENDPOINT 3
    // GET EMPLOYEE RECORD BY DEPT, SHOW 20 RECORDS PER PAGE
    @GET
    @Path("/departments/{deptNo}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EmployeeDTO> getDepartmentEmployees(
            @PathParam("deptNo") String deptNo,
            @DefaultValue("1") @QueryParam("page") int page) {

        int goToPage = Math.max(1, page);

        return empRepository.findEmpDTOByDeptNo(deptNo, goToPage);
    }

    // ENDPOINT 4
    // PROMOTE AN EMPLOYEE
    @POST
    @Path("/promote")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response promoteEmployee(List<PromotionRequestDTO> promotionRequestDTOS) {
        if  (promotionRequestDTOS == null || promotionRequestDTOS.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Request body is empty.").build();
        }

        try {
            empRepository.promoteEmployee(promotionRequestDTOS);
            return Response.status(Response.Status.OK)
                    .entity("Employee promotion successful.").build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Employee not found.").build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }

    // TEST QUERY
//    @GET
//    @Path("/getEmpSalary/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Salaries getEmpSalary(@PathParam("id") Long id) {
//        return empRepository.findLatestSalary(id);
//    }
//
//    @GET
//    @Path("/getEmpTitle/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Titles getEmpTitle(@PathParam("id") Long id) {
//        return empRepository.findLatestTitle(id);
//    }

//    @POST
//    @Path("/create")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response createEmployee(Employee employee) {
//
//        Employee newEmployee = empRepository.createEmployee(employee);
//
//        return Response.status(Response.Status.OK)
//                .entity(newEmployee).build();
//    }
}
