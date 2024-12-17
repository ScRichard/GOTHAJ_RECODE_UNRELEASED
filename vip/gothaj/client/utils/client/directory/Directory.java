package vip.gothaj.client.utils.client.directory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vip.gothaj.client.utils.Wrapper;

public class Directory extends Wrapper {

	private List<String> isIn;
	
	private String name;

	public Directory(String[] isIn, String name) {
		this.isIn = Arrays.asList(isIn);
		this.name = name;
	}
	public Directory(String name) {
		this.isIn = new ArrayList();
		this.name = name;
	}
	public List<String> getIsIn() {
		return isIn;
	}
	public void setIsIn(List<String> isIn) {
		this.isIn = isIn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
