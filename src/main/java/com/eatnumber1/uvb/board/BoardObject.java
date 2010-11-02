package com.eatnumber1.uvb.board;

import com.eatnumber1.uvb.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @author Russell Harmon
 * @since Nov 1, 2010
 */
public interface BoardObject {
	@NotNull
	BoardObject getAdjacentObject( Direction direction );

	@NotNull
	Set<BoardObject> getAdjacentObjcets();
}
