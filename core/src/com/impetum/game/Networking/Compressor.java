package com.impetum.game.Networking;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;


public class Compressor {
	
	private Deflater compressor = new Deflater();
	private static final int BUFFER_SIZE = 1024;
	private byte[] buffer = new byte[BUFFER_SIZE];
	
	public byte[] compress(byte[] data){
		compressor.setInput(data);
		compressor.finish();				
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while (!compressor.finished()) {
		  int n = compressor.deflate(buffer);
		  baos.write(buffer, 0, n);
		}
		compressor.reset();
		return baos.toByteArray();
	}

}
