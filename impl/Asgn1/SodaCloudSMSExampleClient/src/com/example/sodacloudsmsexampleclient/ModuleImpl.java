package com.example.sodacloudsmsexampleclient;

import java.util.HashMap;

public class ModuleImpl implements Module{
	private HashMap<Class, Object> moduleMap;

	public ModuleImpl() {
		moduleMap = new HashMap<Class, Object>();
	}
	@Override
	public <T> T getComponent(Class<T> type) {
		return (T) moduleMap.get(type);
	}

	@Override
	public <T> void setComponent(Class<T> type, T component) {
		moduleMap.put(type, component);
		
	}
	

}
