package ua.com.javarush.gnew.controller.ui;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import ua.com.javarush.gnew.util.ThymeleafUtil;

@Slf4j
public abstract class BaseServlet extends HttpServlet {

  protected ITemplateEngine templateEngine;
  protected WebContext context;

  @Override
  public void init(ServletConfig config) throws ServletException {
    templateEngine = (ITemplateEngine) config.getServletContext().getAttribute("templateEngine");
    super.init(config);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    context = ThymeleafUtil.buildWebContext(req, resp, getServletContext());

    try {
      super.service(req, resp);
    } catch (Exception e) {
      log.warn(e.getMessage());
      context.setVariable("error", e.getMessage());
      templateEngine.process("error", context, resp.getWriter());
    }
  }
}
