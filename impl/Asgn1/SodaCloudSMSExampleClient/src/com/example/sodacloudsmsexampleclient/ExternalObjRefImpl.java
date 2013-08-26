package com.example.sodacloudsmsexampleclient;

import org.magnum.soda.proxy.ObjRef;

public class ExternalObjRefImpl implements ExternalObjRef{

	private ObjRef objRef;
	private String host;
	 
	@Override
	public void setObjRef(ObjRef objRef){
		this.objRef = objRef;
	}
	
	@Override
	public void setHost(String host){
		this.host = host;
	}
	
	@Override
	public ObjRef getObjRef() {
		return this.objRef;
	}

	@Override
	public String getPubSubHost() {
		return host;
	}
}
