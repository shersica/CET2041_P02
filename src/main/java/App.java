import controller.DepartmentController;
import controller.EmployeeController;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

/**
 * Configures the JAX-RS application with base path {@code /api} and
 * registers all REST controller classes.
 */
@ApplicationPath("/api")
public class App extends Application {

    /**
     * Registers the REST controllers for this application.
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<>();
        s.add(DepartmentController.class);
        s.add(EmployeeController.class);
        return s;
    }
}
