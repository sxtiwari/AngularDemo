package com.se.services.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

import com.se.common.util.RequestContext;
import com.se.services.enumeration.ServiceHeaders;

/**
 * This intercepts all the incoming requests.
 * @author Saurabh Tiwari
 *
 */

public class EntryPointInterceptor extends AbstractPhaseInterceptor<Message>{

	public EntryPointInterceptor() {
		super(Phase.RECEIVE);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void handleMessage(Message message) throws Fault {
		Map<String, List<String>> requestHeadersMap = (Map<String, List<String>>) message.get(Message.PROTOCOL_HEADERS);
		String requestToken = "rt_" + (RandomStringUtils.randomAlphanumeric(10)).toLowerCase();
		
		// Set the request token in the request header.
		List<String> requestTokenHeader = new ArrayList<String>();
		requestTokenHeader.add(requestToken);
		requestHeadersMap.put(ServiceHeaders.REQUEST_TOKEN_HEADER.getName(), requestTokenHeader);
		message.put(Message.PROTOCOL_HEADERS, requestHeadersMap);
		
		// Set the request token in message exchange, so that it can be 
		// shared with the rest services & other interceptors in the chain.
		// Also, this is useful in the case of AsyncResponse where threadlocal
		// variable is not available.
		RequestContext requestContext = new RequestContext();
		requestContext.setRequestToken(requestToken);
		message.getExchange().put(ServiceHeaders.REQUEST_TOKEN_HEADER.getName(), requestContext);
	}
}
