package com.dreamstone.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.dreamstone.core.DisplayCarrier;
import com.dreamstone.world.Chunk;
import com.dreamstone.world.Coordinate;
import com.dreamstone.world.Grid;
import com.dreamstone.world.Quadrant;

public class GridDisplay {

	static void drawGrid(Graphics2D display, Grid grid, boolean showCoords) {
		ArrayList<Quadrant> quads = grid.QUADRANTS;
		ArrayList<ArrayList<Chunk>> chunks;
		BufferedImage tileImg;
		int startX, startY;
		float strX, strY, ascent, baseY;
		String coord;

		int screenWidth = DisplayCarrier.getCanvas().getWidth();
		int screenHeight = DisplayCarrier.getCanvas().getHeight();
		FontRenderContext context = display.getFontRenderContext();

		Font f = new Font("Comic Sans MS", Font.PLAIN, 16);
		display.setFont(f);
		
		for (int i = 0; i < quads.size(); i++) {
			chunks = quads.get(i).getChunks();
			for (int y = 0; y < chunks.size(); y++) {
				for (int x = 0; x < chunks.get(y).size(); x++) {
					for (int yy = 0; yy < Chunk.CHUNK_SIZE; yy++) {
						for (int xx = 0; xx < Chunk.CHUNK_SIZE; xx++) {
							Coordinate c = chunks.get(y).get(x).getCoordinateFromIndex(xx, yy);
							startX = 0;
							startY = 0;
							
							if (c.getTile() == null) {
								continue;
							}
							
							tileImg = c.getImage();
							
							if (i == 0) {
								startX = screenWidth / 2 + c.xCoordinate * tileImg.getWidth();
								startY = screenHeight / 2 - (c.yCoordinate + 1) * tileImg.getHeight();
							}
							else if (i == 1) {
								startX = screenWidth / 2 - Math.abs(c.xCoordinate) * tileImg.getWidth();
								startY = screenHeight / 2 - (c.yCoordinate + 1) * tileImg.getHeight();
							}
							else if (i == 2) {
								startX = screenWidth / 2 - Math.abs(c.xCoordinate) * tileImg.getWidth();
								startY = screenHeight / 2 + Math.abs(c.yCoordinate + 1) * tileImg.getHeight();
							}
							else if (i == 3) {
								startX = screenWidth / 2 + c.xCoordinate * tileImg.getWidth();
								startY = screenHeight / 2 + Math.abs(c.yCoordinate + 1) * tileImg.getHeight();
							}
							
							display.drawImage(tileImg, startX, startY, null);
							
//							coord = "(" + Integer.toString(c.xCoordinate) + ", " + Integer.toString(c.yCoordinate) + ")";
//							display.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//							Rectangle2D bounds = f.getStringBounds(coord, context);
							
							coord = c.getTile().getName();
							display.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
							Rectangle2D bounds = f.getStringBounds(coord, context);
							
							strX = (float) (startX + (tileImg.getWidth() / 2 - bounds.getWidth() / 2));
							strY = (float) (startY + (tileImg.getHeight() / 2 - bounds.getHeight() / 2));
							
							ascent = (float) -bounds.getY();
							baseY = strY + ascent;
							
							if (showCoords) {
								display.drawString(coord, (int) strX, (int) baseY);
							}
						}
					}
				}
			}
		}

		display.setColor(Color.BLACK);
		display.drawLine(0, screenHeight / 2, screenWidth, screenHeight / 2);
		display.drawLine(screenWidth / 2, screenHeight, screenWidth / 2, 0);
	}
}