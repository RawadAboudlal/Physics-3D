package com.rawad.phys.client.input;

public final class Input {
	
	private final int button;
	private final int mods;
	
	public Input(int button, int mods) {
		super();
		
		this.button = button;
		this.mods = mods;
		
	}
	
	/**
	 * @return the button
	 */
	public int getButton() {
		return button;
	}
	
	/**
	 * @return the mods
	 */
	public int getMods() {
		return mods;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof Input) {
			
			Input input = (Input) obj;
			
			if(getButton() == input.getButton() && getMods() == input.getMods()) return true;
			
		}
		
		return false;
		
	}
	
}
