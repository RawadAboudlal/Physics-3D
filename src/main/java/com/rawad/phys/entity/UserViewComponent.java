package com.rawad.phys.entity;

import com.rawad.gamehelpers.game.entity.Component;

public class UserViewComponent extends Component {
	
	@Override
	public Component copyData(Component comp) {
		
		if(comp instanceof UserViewComponent) {
			
			UserViewComponent userViewComp = (UserViewComponent) comp;
			
			return userViewComp;
			
		}
		
		return comp;
		
	}
	
}
