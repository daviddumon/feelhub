package com.steambeat.web;

import org.apache.commons.io.FilenameUtils;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.restlet.Context;

import javax.servlet.ServletContext;
import java.io.File;

import static org.mockito.Mockito.*;

public final class ContextTestFactory {

	private ContextTestFactory() {
	}

	public static Context buildContext() {
		final Context result = new Context();
		result.getAttributes().put("org.restlet.ext.servlet.ServletContext", mockServletContext());
		return result;
	}

	private static ServletContext mockServletContext() {
		final ServletContext servletContext = mock(ServletContext.class);
		when(servletContext.getRealPath(anyString())).thenAnswer(new Answer<String>() {
			@Override
			public String answer(final InvocationOnMock invocation) throws Throwable {
				File file = new File("steambeat-web/src/main/webapp");
				if (!file.exists()) {
					file = new File("src/main/webapp");
				}
				return FilenameUtils.concat(file.getAbsolutePath(),
						((String) invocation.getArguments()[0]).substring(1));
			}
		});
		when(servletContext.getContextPath()).thenReturn(SERVER_ROOT);
		when(servletContext.getContextPath()).thenReturn("/");
		return servletContext;
	}

	private static final String SERVER_ROOT = "http://localhost";
}