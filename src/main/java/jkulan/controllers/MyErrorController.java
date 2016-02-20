package jkulan.controllers;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Throwables;

/**
 * Bare essentials error page controller.
 * @author fuero
 */
@Controller
public class MyErrorController implements ErrorController {
	private static final String PATH = "/error";

	@RequestMapping(value = PATH)
	public String error(HttpServletRequest request, Model model) {
		int code = getErrorCode(request);
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		String errorMessage = getExceptionMessage(throwable, code);
		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
		if (requestUri == null) {
			requestUri = "Unknown";
		}

		String message = MessageFormat.format("{0} returned for {1} with message {2}", code, requestUri, errorMessage);

		model.addAttribute("errorMessage", message);
		model.addAttribute("errorCode", code);
		return "error";
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}

	private int getErrorCode(HttpServletRequest request) {
		Integer code = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (code == null)
			return 0;
		return code;
	}

	private String getExceptionMessage(Throwable throwable, int statusCode) {
		if (throwable != null) {
			return Throwables.getRootCause(throwable).getMessage();
		}
		if (statusCode >= 200 && statusCode < 600) {
			HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
			return httpStatus.getReasonPhrase();
		}
		return "";
	}
}
