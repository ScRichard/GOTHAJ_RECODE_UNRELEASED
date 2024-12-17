package vip.gothaj.client.utils.render.text.ext;

import org.lwjgl.input.Keyboard;

import vip.gothaj.client.utils.font.icons.Fonts;
import vip.gothaj.client.utils.font.icons.IconUtils;
import vip.gothaj.client.utils.render.RenderUtils;
import vip.gothaj.client.utils.render.text.TextBox;
import vip.gothaj.client.utils.shader.impl.RoundedUtils;
import vip.gothaj.client.utils.ui.PositionUtils;

public class SearchTextBox extends TextBox {

	private int cursorTicks;
	
	public SearchTextBox(String font, PositionUtils position) {
		super(font, position);
		
		this.maxSize = 20;
	}

	@Override
	public void drawBackground() {
		int color = 0x40ffffff;
		if(this.isActived()) {
			color = 0xffffffff;
		}
		IconUtils.drawIcon("search", this.getPosition().getX()-15, this.getPosition().getY()+this.getPosition().getHeight()/2-IconUtils.getIconHeight("search", 0.8)/2, 0xaaffffff, 0.8);
		
	}

	@Override
	public void renderHighlited() {
		
	}

	@Override
	public void renderCursor() {
		if(getText().equals("")) return;
		
		if(cursorTicks % 290 < 150)
		RenderUtils.drawRect(this.getPosition().getX()+this.getPosition().getWidth()/2 + getWidthToIndex()/2,
				this.getPosition().getY() + this.getPosition().getHeight() / 2 - Fonts.getHeight(this.getFont(), 1.2) / 2,
				this.getPosition().getX()+this.getPosition().getWidth()/2 + getWidthToIndex()/2 + 1,
				this.getPosition().getY() + this.getPosition().getHeight() / 2 + Fonts.getHeight(this.getFont(), 1.2) / 2, -1);
		cursorTicks++;
	}

	@Override
	public void renderText() {
		if(getText().equals(""))Fonts.drawString("Search...", this.getPosition().getX()+this.getPosition().getWidth()/2-Fonts.getWidth("Search...", this.getFont(), 1.2)/2, this.getPosition().getY() + this.getPosition().getHeight() / 2 - Fonts.getHeight(this.getFont(), 1.2) / 2, 0x90ffffff, this.getFont(), 1.2);
		Fonts.drawString(getText(), this.getPosition().getX()+this.getPosition().getWidth()/2-Fonts.getWidth(getText(), this.getFont(), 1.2)/2, this.getPosition().getY() + this.getPosition().getHeight() / 2 - Fonts.getHeight(this.getFont(), 1.2)/2, -1, this.getFont(), 1.2);
	}
	
	public double getWidthToIndex() {
		if (this.getIndex() == 0)
			return 0;
		return Fonts.getWidth(this.getText().substring(0, this.getIndex()), this.getFont(), 1.2);
	}
	
	
	
}
