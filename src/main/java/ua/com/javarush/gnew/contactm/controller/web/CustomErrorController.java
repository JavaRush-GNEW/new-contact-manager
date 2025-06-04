package ua.com.javarush.gnew.contactm.controller.web;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

@Controller
public class CustomErrorController implements ErrorController {

  private final ErrorAttributes errorAttributes;

  // Spring Boot will auto-wire the default ErrorAttributes bean
  public CustomErrorController(ErrorAttributes errorAttributes) {
    this.errorAttributes = errorAttributes;
  }

  @RequestMapping("/error")
  public String handleError(HttpServletRequest request, Model model) {
    // Wrap the servlet request so we can ask Spring for all the error details
    ServletWebRequest webRequest = new ServletWebRequest(request);

    // Pick which fields to include in the map
    ErrorAttributeOptions options =
        ErrorAttributeOptions.defaults()
            .including(ErrorAttributeOptions.Include.MESSAGE)
            .including(ErrorAttributeOptions.Include.EXCEPTION)
            .including(ErrorAttributeOptions.Include.STACK_TRACE);

    // Fetch the error attributes
    Map<String, Object> errorMap = errorAttributes.getErrorAttributes(webRequest, options);

    // Push them into the Thymeleaf model
    model.addAllAttributes(errorMap);

    // Render src/main/resources/templates/error/error.html
    return "error/error";
  }
}
