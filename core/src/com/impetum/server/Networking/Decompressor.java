package com.impetum.server.Networking;

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class Decompressor {
	
	private Inflater decompressor = new Inflater();
	private static final int BUFFER_SIZE = 1024;
	private byte[] buffer = new byte[BUFFER_SIZE];

	public byte[] decompress(byte[] data){
		decompressor.setInput(data);				
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while (!decompressor.finished()) {
		  int n = tryDecompressing();
		  if (n == -1){
			  raiseBrokenPacketError();
			  return null;
		  }
		  baos.write(buffer, 0, n);
		}
		decompressor.reset();
		return baos.toByteArray();
	}

	private int tryDecompressing() {
		int n = -1;
		try {
			n = decompressor.inflate(buffer);
		} catch (DataFormatException e) {
			e.printStackTrace();
		}
		return n;
	}
	
	private void raiseBrokenPacketError() {
		System.out.println("Broken packet");	
	}
}
