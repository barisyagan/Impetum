package com.impetum.server.Utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.util.io.InterruptTimer;
import org.eclipse.jgit.util.io.TimeoutInputStream;

import com.impetum.server.Networking.ByteCode;

public class ByteUtils {
	private static final int readTimeout = 400;
	
	public static byte[] signData(byte[] data){
		byte[] output = new byte[data.length + ByteCode.SIGN.length];
		for (int i=0; i < ByteCode.SIGN.length; i++)
			output[i] = ByteCode.SIGN[i];
		for (int i=0; i < data.length; i++)
			output[ByteCode.SIGN.length + i] = data[i];
		return output;
	}
	
	public static byte[] readStream(InputStream inputStream){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte data;
		while (isDataAvailable(inputStream) != -1){
			try {
				data = (byte) inputStream.read();
			} catch (IOException e) {
				break;
			} 
			bos.write(data);
		}
		return bos.toByteArray();
	}

	private static int isDataAvailable(InputStream inputStream){
		try {
			return inputStream.available();
		} catch (Exception e) {
			return -1;
		}
	}
	
	public static byte[] connectBytes(byte[] first, byte[] second){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			bos.write(first);
			bos.write(second);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}
	
	public static boolean compareBytes(byte[] first, byte[] second){
		if (first.length != second.length)
			return false;
		for (int i=0; i < first.length; i++)
			if (first[i] != second[i])
				return false;
		return true;
	}
	
	public static boolean compareBytes(byte[] first, byte[] second, int size){
		for (int i=0; i < size; i++)
			if (first[i] != second[i])
				return false;
		return true;
	}
	
	public static boolean compareBytes(byte[] first, byte[] second,int offset, int size){
		for (int i=0; i < size; i++)
			if (first[offset + i] != second[i])
				return false;
		return true;
	}

	public static byte[] removeSignAndEOP(byte[] data) {
		byte[] sign = new byte[ByteCode.SIGN.length];
		for (int i=0; i < sign.length; i++)
			sign[i] = data[i];
		if (!compareBytes(sign, ByteCode.SIGN))
			throw new RuntimeException("Data is not signed!");
		if (!compareBytes(subByte(data, data.length - ByteCode.EOP.length, data.length), ByteCode.EOP))
			throw new RuntimeException("Data does not have EOP!");
		byte[] content = new byte[data.length - sign.length - ByteCode.EOP.length];
		for (int i= sign.length; i < data.length - ByteCode.EOP.length; i++)
			content[i - sign.length] = data[i];
		return content;
	}
	
	public static byte[] removeSign(byte[] data) {
		byte[] sign = new byte[ByteCode.SIGN.length];
		for (int i=0; i < sign.length; i++)
			sign[i] = data[i];
		if (!compareBytes(sign, ByteCode.SIGN))
			throw new RuntimeException("Data is not signed!");
		byte[] content = new byte[data.length - sign.length];
		for (int i= sign.length; i < data.length; i++)
			content[i - sign.length] = data[i];
		return content;
	}
	
	public static byte[] endPacket(byte[] data){
		byte[] output = new byte[data.length + ByteCode.EOP.length];
		for (int i=0; i < data.length; i++)
			output[i] = data[i];
		for (int i=0; i < ByteCode.EOP.length; i++)
			output[data.length + i] = ByteCode.EOP[i];
		return output;
	}
	
	public static byte[] subByte(byte[] data, int start, int end){
		byte[] out = new byte[end - start];
		for (int i= start; i < end; i++)
			out[i - start] = data[i];
		return out;
	}

	public static byte[] extractPacket(byte[] buffer) {
		if (!compareBytes(buffer, ByteCode.SIGN, ByteCode.SIGN.length)){
			//System.out.println("Packet not signed! Content = " + new String(subByte(buffer, 0, ByteCode.SIGN.length)));
			return null;
		}
		int endIndex = 0;
		for (int i=0; i < buffer.length; i++){
			if(buffer[i] == ByteCode.SEPARATOR[0] && isEndOfPacket(i, buffer)){
				endIndex = i + ByteCode.EOP.length;
				break;
			}
		}
		if (endIndex == 0){
			System.out.println("EOP is not found!");
			return null;
		}
		return subByte(buffer, 0, endIndex);
	}

	private static boolean isEndOfPacket(int index, byte[] buffer) {
		return compareBytes(buffer, ByteCode.EOP, index, ByteCode.EOP.length);
	}
	
	public static int indexOfBytes(byte[] data, byte[] small){
		if (small.length > data.length)
			return -1;
		for (int i = 0; i < data.length - small.length; i++){
			if (data[i] == small[0]){
				if (compareBytes(subByte(data,i,i+small.length),small))
					return i;
			}
		}
		return -1;
	}
	
	public static byte[] extractData(byte[] body, byte[] data) {
		int index = indexOfBytes(body,data);
		for (int i=index; i < body.length; i++){
			if (body[i] == ByteCode.SEPARATOR[0]){
				return subByte(body, index, i);
			}
		}
		return subByte(body, index, body.length);
	}

	

}
