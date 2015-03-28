package com.se.services.interceptor;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.Conduit;
import org.springframework.beans.factory.annotation.Autowired;

import com.se.common.logger.AppLogger;
import com.se.common.logger.AppLoggerFactory;
import com.se.common.util.PropertiesReader;
import com.se.common.util.RequestContext;
import com.se.services.constant.ServiceConstants;
import com.se.services.util.ServiceUtil;

public class BasicAuthInterceptor extends AbstractPhaseInterceptor<Message> {

	@Autowired
	private ServiceUtil serviceUtil;

	@Autowired
	private PropertiesReader propertiesReader;

	private static AppLogger logger = AppLoggerFactory.getLogger();

	public BasicAuthInterceptor() {
		super(Phase.RECEIVE);
	}

	@Override
	public void handleMessage(Message message) throws Fault {
		AuthorizationPolicy policy = message.get(AuthorizationPolicy.class);
		if (policy == null) {
			sendErrorResponse(message, HttpURLConnection.HTTP_UNAUTHORIZED);
			return;
		}

		if (!(policy.getUserName().equalsIgnoreCase(
				propertiesReader.getStringValue(
						ServiceConstants.BASIC_AUTH_USER, "")) && policy
				.getPassword().equalsIgnoreCase(
						propertiesReader.getStringValue(
								ServiceConstants.BASIC_AUTH_PASSWORD, "")))) {
			sendErrorResponse(message, HttpURLConnection.HTTP_UNAUTHORIZED);
			return;
		}
	}

	@SuppressWarnings("unchecked")
	private void sendErrorResponse(Message message, int responseCode) {
		Message outMessage = getOutMessage(message);
		outMessage.put(Message.RESPONSE_CODE, responseCode);
		// Set the response headers
		Map<String, List<String>> responseHeaders = (Map<String, List<String>>) message
				.get(Message.PROTOCOL_HEADERS);
		if (responseHeaders != null) {
			responseHeaders.put("WWW-Authenticate",
					Arrays.asList(new String[] { "Basic realm=realm" }));
			responseHeaders.put("Content-Length",
					Arrays.asList(new String[] { "0" }));
		}

		RequestContext context = serviceUtil.getRequestContext();
		String url = (String) message.get(Message.REQUEST_URL);

		logger.error(context, "Basic Auth needed for {}", url);

		message.getInterceptorChain().abort();
		try {
			getConduit(message).prepare(outMessage);
			close(outMessage);
		} catch (Exception e) {
			logger.error(context,
					"Error writing basic auth interceptor response {}", url, e);
		}

	}

	private Message getOutMessage(Message inMessage) {
		Exchange exchange = inMessage.getExchange();
		Message outMessage = exchange.getOutMessage();
		if (outMessage == null) {
			Endpoint endpoint = exchange.get(Endpoint.class);
			outMessage = endpoint.getBinding().createMessage();
			exchange.setOutMessage(outMessage);
		}
		outMessage.putAll(inMessage);
		return outMessage;
	}

	private Conduit getConduit(Message inMessage) throws IOException {
		Exchange exchange = inMessage.getExchange();
		Conduit conduit = exchange.getDestination().getBackChannel(inMessage);
		exchange.setConduit(conduit);
		return conduit;
	}

	private void close(Message outMessage) throws IOException {
		OutputStream os = outMessage.getContent(OutputStream.class);
		os.flush();
		os.close();
	}
}
