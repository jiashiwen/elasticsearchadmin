package com.leju.esrestful.configuration;

import java.net.UnknownHostException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.leju.com.entities.RestErrorEntity;

/**
 * Performs the same exception handling as {@link ExceptionHandlingController}
 * but offers them globally. The exceptions below could be raised by any
 * controller and they would be handled here, if not handled in the controller
 * already.
 * 
 * @author Paul Chapman
 */
@ControllerAdvice
public class GlobalExceptionHandlingControllerAdvice {

	protected Logger logger;

	public GlobalExceptionHandlingControllerAdvice() {
		logger = LoggerFactory.getLogger(getClass());
	}

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
	/* . . . . . . . . . . . . . EXCEPTION HANDLERS . . . . . . . . . . . . . . */
	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	/**
	 * Convert a predefined exception to an HTTP Status code
	 */
//	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation")
	// 409
//	@ExceptionHandler(DataIntegrityViolationException.class)
//	public void conflict() {
//		logger.error("Request raised a DataIntegrityViolationException");
//		// Nothing to do
//	}

	/**
	 * Convert a predefined exception to an HTTP Status code and specify the
	 * name of a specific view that will be used to display the error.
	 * 
	 * @return Exception view.
	 */
//	@ExceptionHandler({ SQLException.class, DataAccessException.class })
//	public String databaseError(Exception exception) {
//		// Nothing to do. Return value 'databaseError' used as logical view name
//		// of an error page, passed to view-resolver(s) in usual way.
//		logger.error("Request raised " + exception.getClass().getSimpleName());
//		return "databaseError";
//	}

	/**
	 * Demonstrates how to take total control - setup a model, add useful
	 * information and return the "support" view name. This method explicitly
	 * creates and returns
	 * 
	 * @param req
	 *            Current HTTP request.
	 * @param exception
	 *            The exception thrown - always {@link SupportInfoException}.
	 * @return The model and view used by the DispatcherServlet to generate
	 *         output.
	 * @throws Exception
	 */
	@ExceptionHandler({org.elasticsearch.index.IndexNotFoundException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody RestErrorEntity  handleIndexNotFoundException(HttpServletRequest request, Exception ex){	     
		RestErrorEntity  response = new RestErrorEntity();
	    response.setUrl(request.getRequestURL().toString());
	    response.setError_message(ex.getMessage());
	    response.setException("IndexNotFoundException");
	    logger.error(ex.getLocalizedMessage(), ex);
	    return response;
	}
	
	@ExceptionHandler({org.elasticsearch.action.search.SearchPhaseExecutionException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody RestErrorEntity  handleSearchPhaseExecutionException(HttpServletRequest request, Exception ex){	     
		RestErrorEntity  response = new RestErrorEntity();
	    response.setUrl(request.getRequestURL().toString());
	    response.setError_message(ex.getMessage());
	    response.setException("SearchPhaseExecutionException");
	    logger.error(ex.getLocalizedMessage(), ex);
	    return response;
	}
	
//    public ErrorResponse errorResponse(Exception exception) {
//		logger.error(exception.getLocalizedMessage(), exception);
//	        return new ErrorResponse(exception.getLocalizedMessage());
//	       
//    }
//	
//	public class ErrorResponse {
//	    private String message;
//	    public ErrorResponse(String message) {
//	        this.message = message;	        
//	    }
//	    public String getMessage() {
//	        return message;
//	    }
//	}
	
	
//	
//	public ModelAndView handleError(HttpServletRequest req, Exception exception)
//			throws Exception {
//
//		// Rethrow annotated exceptions or they will be processed here instead.
//		if (AnnotationUtils.findAnnotation(exception.getClass(),
//				ResponseStatus.class) != null)
//			throw exception;
//
////		logger.error("Request: " + req.getRequestURI() + " raised " + exception);
//
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("exception", exception);
//		mav.addObject("url", req.getRequestURL());
//		mav.addObject("timestamp", new Date().toString());
//		mav.addObject("status", 500);
//
//		mav.setViewName("support");
//		return mav;
//	}
}