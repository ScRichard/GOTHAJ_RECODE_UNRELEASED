package vip.gothaj.client.utils.render.text;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.lwjgl.input.Keyboard;

import vip.gothaj.client.utils.font.icons.Fonts;
import vip.gothaj.client.utils.render.RenderUtils;
import vip.gothaj.client.utils.shader.impl.RoundedUtils;
import vip.gothaj.client.utils.ui.PositionUtils;

public class TextBox {

	private String text = "";

	private String font = "roboto-regular";

	private PositionUtils position;

	private int index = 0;
	
	public int maxSize = 32;
	
	public boolean renderBG = true;

	private String allowedCharacters = "qwertzuiopasdfghjklyxcvbnm_ ";

	private boolean highlited = false, actived = false;

	private int ticks = 0;

	public TextBox(String font, PositionUtils position, String allowedCharacters) {
		this.font = font;
		this.position = position;
		this.allowedCharacters = allowedCharacters;
	}

	public TextBox(String font, PositionUtils position) {
		this.font = font;
		this.position = position;
	}

	public TextBox(PositionUtils position) {
		this.position = position;
	}

	public void drawScreen(int mouseX, int mouseY) {
		
		drawBackground();

		if (text.equals(""))
			this.highlited = false;
		if (this.highlited) renderHighlited();

		if (Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
			if (ticks % 20 == 0 && ticks > 200)
				removeChar();

			ticks++;
		}
		renderText();
		
		if (actived)
			renderCursor();

	}
	
	public void drawBackground() {
		if(renderBG) RoundedUtils.drawRoundedRect(position, 0xff000000, 4);
	}
	public void renderHighlited() {
		RenderUtils.drawRect(this.position.getX(), this.position.getY()+this.position.getHeight()/2-Fonts.getHeight(font)/2,
				this.position.getX() + Fonts.getWidth(text, font),this.position.getY()+this.position.getHeight()/2+Fonts.getHeight(font)/2,
				0x90aaaaaa);
	}
	public void renderCursor() {
		RenderUtils.drawRect(this.position.getX() + getWidthToIndex(),
				this.position.getY() + this.position.getHeight() / 2 - Fonts.getHeight(font) / 2,
				this.position.getX() + getWidthToIndex() + 1,
				this.position.getY() + this.position.getHeight() / 2 + Fonts.getHeight(font) / 2, 0xffffffff);
	}
	
	public void renderText() {
		Fonts.drawString(text, this.position.getX(),
				this.position.getY() + this.position.getHeight() / 2 - Fonts.getHeight(font) / 2, -1, font);
	}

	public void onClick(int mouseX, int mouseY, int button) {
		if (position.isInside(mouseX, mouseY) && button == 0) {
			actived = true;
			highlited = false;
		} else {
			actived = false;
		}
	}

	public void onKey(int key, char typedChar) {
		if (!actived) {
			highlited = false;
			return;
		}

		if (key == Keyboard.KEY_BACK) {
			removeChar();
			ticks = 0;
		}
		if (key == Keyboard.KEY_LEFT) {
			if (index > 0) {
				--index;
			}
		}
		if (key == Keyboard.KEY_RIGHT) {
			if (index < text.length()) {
				++index;
			}
			return;
		}
		if (Keyboard.isKeyDown(29) && key == Keyboard.KEY_A) {
			highlited = true;
			return;
		}

		if (key == Keyboard.KEY_C && Keyboard.isKeyDown(29) && highlited) {
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
			return;
		}
		if (key == Keyboard.KEY_V && Keyboard.isKeyDown(29)) {
			try {
				String data = (String) Toolkit.getDefaultToolkit().getSystemClipboard()
						.getData(DataFlavor.stringFlavor);
				if (highlited)
					text = data;
				else
					text += data;
			} catch (HeadlessException | UnsupportedFlavorException | IOException e) {
				e.printStackTrace();
			}
			return;
		}
		if (allowedCharacters.contains(Character.toString(typedChar).toLowerCase())) {
			addChar(typedChar);
		}

	}

	public double getWidthToIndex() {
		if (index == 0)
			return 0;
		return Fonts.getWidth(text.substring(0, index), font);
	}

	public void removeChar() {
		if (index == 0)
			return;
		if (this.highlited) {
			text = "";
			index = 0;
			this.highlited = false;
			return;
		}
		text = text.substring(0, index - 1) + text.substring(index);
		index--;
	}

	public void addChar(char chr) {
		if(text.length() == maxSize) return;
		text = text.substring(0, index) + chr + text.substring(index);
		index++;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public PositionUtils getPosition() {
		return position;
	}

	public void setPosition(PositionUtils position) {
		this.position = position;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public String getAllowedCharacters() {
		return allowedCharacters;
	}

	public void setAllowedCharacters(String allowedCharacters) {
		this.allowedCharacters = allowedCharacters;
	}

	public boolean isHighlited() {
		return highlited;
	}

	public void setHighlited(boolean highlited) {
		this.highlited = highlited;
	}

	public boolean isActived() {
		return actived;
	}

	public void setActived(boolean actived) {
		this.actived = actived;
	}

	public int getTicks() {
		return ticks;
	}

	public void setTicks(int ticks) {
		this.ticks = ticks;
	}
	
}
