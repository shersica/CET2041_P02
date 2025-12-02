package controller;

import dtos.EmployeeDTO;
import dtos.PromotionRequestDTO;
import entities.Employee;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.EmployeeService;
import java.util.List;

@Path("/employees")
public class EmployeeController {
    private final EmployeeService employeeService = new EmployeeService();

    @GET
    @Path("/employee/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployee(@PathParam("id") long id) {
        try {
            Employee employee = employeeService.findById(id);
            if (employee == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Employee not found\"}")
                        .build();
            }
            return Response.ok().entity(employee).build();

        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path(("/searchByDept"))
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeesByDept(@QueryParam("deptNo") String deptNo, @QueryParam("page") @DefaultValue("1") int page) {
        try {
            List<EmployeeDTO> employees = employeeService.findEmployeesByDept(deptNo, page);
            if (employees.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\": \"Page number exceeds max available\"}")
                        .build();
            }
            return Response.ok().entity(employees).build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    /* JSON PromotionRequestDTO Example
        {
            "empNo": 10001,
            "newTitle": "Staff Engineer",
            "newSalary": 90000,
            "newDeptNo": "d005",
            "isManager": true
        }
     */
    @POST
    @Path("/promote")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response promoteEmployee(PromotionRequestDTO promotionRequestDTO) {
        try {
            employeeService.promoteEmployee(promotionRequestDTO);
            return Response.ok().entity("{\"success\": \"Employee promoted\"}").build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }
}
