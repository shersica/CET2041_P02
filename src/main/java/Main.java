import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import service.EmployeeService;

import java.util.*;

@ApplicationPath("/api")
public class Main extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(JSONConfig.class);
        s.add(EmployeeService.class);
        return s;
    }
}
