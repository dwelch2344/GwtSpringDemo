package co.davidwelch.test.GwtSpringDemo.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
/**
 * This is to be used with the JSONP service. But not really.
 * @author dwelch
 *
 */
@Deprecated
public class GwtJsonpFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}
	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Wrapper w = new Wrapper( (HttpServletResponse) response, baos);
		
		baos.write( "gwtJsonCallback(  ".getBytes() );
		chain.doFilter(request, w);
		baos.write( "   )".getBytes() );
		
		response.getOutputStream().write(baos.toByteArray());
	}

	public class Wrapper extends HttpServletResponseWrapper{

		private PrintWriter writer;
		private ServletOutputStream os;

		public Wrapper(HttpServletResponse response, final OutputStream os) {
			super(response);
			this.os = new ServletOutputStream(){
				@Override
				public void write(int b) throws IOException {
					os.write(b);
				}
			};
			this.writer = new PrintWriter(os);
		}
		
		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			return os;
		}
		
		@Override
		public PrintWriter getWriter() throws IOException {
			return writer;
		}
		
	}
	
}
