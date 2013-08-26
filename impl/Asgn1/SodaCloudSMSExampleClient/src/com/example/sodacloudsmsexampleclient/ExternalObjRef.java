package com.example.sodacloudsmsexampleclient;

import org.magnum.soda.proxy.ObjRef;


/**
 * Asgn Step 6: Create an implementation of this
 * interface.
 * 
 */
public interface ExternalObjRef {

	public ObjRef getObjRef();
	
	public void setObjRef(ObjRef objRef);
	
	public String getPubSubHost();
		
	/**
	 * The toString() implementation should return
	 * a String in the following format:
	 * 
	 * getPubSubHost()+"|"+getObjRef().getUri()
	 * 
	 * 
	 * @return
	 */
	public String toString();

	void setHost(String host);
	
}
