
package com.cardshifter.core.mod;

import java.nio.file.Path;

import com.cardshifter.modapi.base.ECSGame;

/**
 *
 * @author Frank van Heeswijk
 */
public class JavaMod extends BaseMod {
	public JavaMod(final Path bootFile) {
		super(bootFile);
	}
	
	@Override
	public ECSGame createGame() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}