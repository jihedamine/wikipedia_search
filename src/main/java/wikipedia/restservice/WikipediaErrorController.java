package wikipedia.restservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * Rest controller to intercept exceptions thrown by the Rest services
 * The controller logs the exception and displays an error page with relevant information
 */
@RestController
public class WikipediaErrorController implements ErrorController {
    private final static Logger logger = LoggerFactory.getLogger(WikipediaErrorController.class.getName());

    private static final String ERROR_PATH = "error";

    @RequestMapping(ERROR_PATH)
    @ResponseBody
    public String error(WebRequest request, HttpServletResponse response) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code", RequestAttributes.SCOPE_REQUEST);
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception", RequestAttributes.SCOPE_REQUEST);
        logger.error("Failed to process request", exception);
        return String.format("<html><body><h2>Error Page</h2><div>Status code: <b>%s</b></div>"
                        + "<div>Exception Message: <b>%s</b></div><body></html>",
                statusCode, exception==null? "N/A": exception.getMessage());
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
