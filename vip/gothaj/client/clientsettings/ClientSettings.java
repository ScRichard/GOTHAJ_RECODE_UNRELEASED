package vip.gothaj.client.clientsettings;

import java.util.ArrayList;
import java.util.Arrays;

import vip.gothaj.client.values.settings.BooleanValue;
import vip.gothaj.client.values.settings.CategoryValue;
import vip.gothaj.client.values.settings.DescriptionValue;

public class ClientSettings {

	private ArrayList<CategoryValue> settings = new ArrayList();
	//Main Settings
	private CategoryValue main = new CategoryValue(null, "Main settings");
	
	private DescriptionValue description1 = new DescriptionValue(null, "Enables physics on item entity");
	public BooleanValue itemphisics = new BooleanValue(null, "Item Physics", false, null);
	private DescriptionValue description2 = new DescriptionValue(null, "Disables animation when you hurt");
	public BooleanValue hurtcam = new BooleanValue(null, "No HurtCam", false, null);
	
	public ClientSettings() {
		this.main.addSettings(
				description1,
				itemphisics,
				description2,
				hurtcam
				);
		
		this.addSettings(
				main,
				main,
				main
				);
	}
	
	private void addSettings(CategoryValue...settings) {
		this.settings.addAll(Arrays.asList(settings));
	}

	public ArrayList<CategoryValue> getSettings() {
		return settings;
	}

	public void setSettings(ArrayList<CategoryValue> settings) {
		this.settings = settings;
	}

	public CategoryValue getMain() {
		return main;
	}

	public void setMain(CategoryValue main) {
		this.main = main;
	}

	public DescriptionValue getDescription1() {
		return description1;
	}

	public void setDescription1(DescriptionValue description1) {
		this.description1 = description1;
	}

	public BooleanValue getItemphisics() {
		return itemphisics;
	}

	public void setItemphisics(BooleanValue itemphisics) {
		this.itemphisics = itemphisics;
	}

	public DescriptionValue getDescription2() {
		return description2;
	}

	public void setDescription2(DescriptionValue description2) {
		this.description2 = description2;
	}

	public BooleanValue getHurtcam() {
		return hurtcam;
	}

	public void setHurtcam(BooleanValue hurtcam) {
		this.hurtcam = hurtcam;
	}
	
	
}