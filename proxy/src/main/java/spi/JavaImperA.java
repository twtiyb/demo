package spi;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chunchun.xu on 2019/7/15.
 * @version 1.0
 * @descripte
 */

@Slf4j
public class JavaImperA implements JavaInterface {

	@Override
	public String printAbc() {
		log.info(this.getClass().getName());
		return this.getClass().getName();
	}
}
