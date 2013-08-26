package com.example.sodacloudsmsexampleclient;

import org.magnum.soda.proxy.ObjRef;

public class QRCodeObjRefExtractor implements ObjRefExtractor {

	@Override
	public ExternalObjRef extract(String data) {
		String server = data.substring(0,data.indexOf("|"));
		String ref = data.substring(data.indexOf("|")+1);
		ObjRef oref = ObjRef.fromObjUri(ref);
		
		ExternalObjRefImpl externObjRef = new ExternalObjRefImpl();
		externObjRef.setObjRef(oref);
		externObjRef.setHost(server);

		/**
		 * Asgn Step 7: Use the data above to create an
		 * instance of your ExteranlObjRef implementation
		 * and return it.
		 * 
		 */
		
		return externObjRef;
	}

}
