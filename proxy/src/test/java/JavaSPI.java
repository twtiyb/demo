import org.junit.Test;
import spi.JavaInterface;

import java.util.ServiceLoader;

/**
 * @author chunchun.xu on 2019/7/15.
 * @version 1.0
 * @descripte
 */

public class JavaSPI {
	@Test
	public void testBasic() {
		ServiceLoader<JavaInterface> javaInterfaces = ServiceLoader.load(JavaInterface.class);
		javaInterfaces.forEach(JavaInterface::printAbc);
	}
}
