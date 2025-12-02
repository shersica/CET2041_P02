import controller.DepartmentController;
import controller.EmployeeController;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import service.DepartmentService;
import service.EmployeeService;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class App extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<>();
        s.add(DepartmentController.class);
        s.add(EmployeeController.class);
        return s;
    }
}
