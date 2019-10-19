package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoxSizeTest {

	@Test
	public void testBoxSize() {
		
		final int BOX_PADDING = 4;
		final int W = 500, H = 600;
		final int DIM = W < H? W : H;
		final int BOX_SIZE = (DIM - 2 * BOX_PADDING) / 8;
		final int CHECKER_SIZE = Math.max(0, BOX_SIZE - 2 * BOX_PADDING);
		assertNotEquals(BOX_SIZE,CHECKER_SIZE);
		
	}

}
