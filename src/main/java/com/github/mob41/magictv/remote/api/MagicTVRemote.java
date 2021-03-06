package com.github.mob41.magictv.remote.api;

/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 Anthony Law
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MagicTVRemote {
	
	public static final byte[] gainInfo = {0x55, 0x00, 0x00, 0x40, 0x40, 0x04, 0x00, 0x01, 0x00, 0x00, 0x00, (byte) 0xAA};
	
	public static final byte[] returnOK = {0x55, 0x00, 0x00, (byte) 0x80, 0x40, 0x06, 0x00, 0x01, 0x10, 0x02, 0x00, 0x04, 0x00, (byte) 0xAA};

	//Total 16. start hex is only 13
	public static final byte[] startHex = {0x55, 0x00, 0x00, 0x40, 0x40, 0x08, 0x00, 0x04, 0x00, 0x04, 0x00, (byte) 0xD8, 0x2A};
	
	//Ending HEX
	public static final byte endingHex = (byte) 0xAA;
	
	//Commands
	public static final byte[] CMD_MENU = {0x0F, (byte) 0xF0};
	
	public static final byte[] CMD_POWER = {0x11, (byte) 0xEE};
	
	public static final byte[] CMD_GUIDE = {0x32, (byte) 0xCD};
	
	public static final byte[] CMD_TEXT = {0x33, (byte) 0xCC};
	
	public static final byte[] CMD_ASPECT = {0x34, (byte) 0xCB};
	
	public static final byte[] CMD_AUDIO = {0x4E, (byte) 0xB1};
	
	public static final byte[] CMD_SUBTITLE = {0x4F, (byte) 0xB0};
	
	public static final byte[] CMD_BACK = {0x35, (byte) 0xCA};
	
	public static final byte[] CMD_UP = {0x0A, (byte) 0xF5};
	
	public static final byte[] CMD_DOWN = {0x0B, (byte) 0xF4};
	
	public static final byte[] CMD_LEFT = {0x0C, (byte) 0xF3};
	
	public static final byte[] CMD_RIGHT = {0x0D, (byte) 0xF2};
	
	public static final byte[] CMD_INFO = {0x36, (byte) 0xC9};
	
	public static final byte[] CMD_OK = {0x0E, (byte) 0xF1};
	
	public static final byte[] CMD_VOL_UP = {0x14, (byte) 0xEB};
	
	public static final byte[] CMD_VOL_DOWN = {0x15, (byte) 0xEA};
	
	public static final byte[] CMD_MUTE = {0x18, (byte) 0xE7};
	
	public static final byte[] CMD_CH_UP = {0x16, (byte) 0xE9};
	
	public static final byte[] CMD_CH_DOWN = {0x17, (byte) 0xE8};
	
	public static final byte[] CMD_REC = {0x24, (byte) 0xDB};
	
	public static final byte[] CMD_PAUSE = {0x22, (byte) 0xDD};
	
	public static final byte[] CMD_STOP = {0x23, (byte) 0xDC};
	
	public static final byte[] CMD_PLAY = {0x21, (byte) 0xDE};
	
	public static final byte[] CMD_FAST_REVERSE = {0x25, (byte) 0xDA};
	
	public static final byte[] CMD_FAST_FORWARD = {0x26, (byte) 0xD9};
	
	public static final byte[] CMD_REPLAY = {0x27, (byte) 0xD8};
	
	public static final byte[] CMD_SKIP = {0x28, (byte) 0xD7};
	
	public static final byte[] CMD_LIVE_SOURCE = {0x37, (byte) 0xC8};
	
	public static final byte[] CMD_ONE = {0x61, (byte) 0x9E};
	
	public static final byte[] CMD_TWO = {0x62, (byte) 0x9D};
	
	public static final byte[] CMD_THREE = {0x63, (byte) 0x9C};
	
	public static final byte[] CMD_FOUR = {0x64, (byte) 0x9B};
	
	public static final byte[] CMD_FIVE = {0x65, (byte) 0x9A};
	
	public static final byte[] CMD_SIX = {0x66, (byte) 0x99};
	
	public static final byte[] CMD_SEVEN = {0x67, (byte) 0x98};
	
	public static final byte[] CMD_EIGHT = {0x68, (byte) 0x97};
	
	public static final byte[] CMD_NINE = {0x69, (byte) 0x96};
	
	public static final byte[] CMD_ZERO = {0x60, (byte) 0x98};
	
	public static final byte[] CMD_CROSS_CLEAR = {0x38, (byte) 0xC7};
	
	public static final byte[] CMD_TICK_ENTER = {0x39, (byte) 0xC6};
	
	public static final byte[] CMD_RED = {0x48, (byte) 0xB7};
	
	public static final byte[] CMD_GREEN = {0x4B, (byte) 0xB4};
	
	public static final byte[] CMD_YELLOW = {0x4C, (byte) 0xB3};
	
	public static final byte[] CMD_BLUE = {0x4D, (byte) 0xB2};
	
	//Default port
	public static final int DEFAULT_PORT = 23456;
	
	private String model;
	
	private String firmware;
	
	private final String ip;
	
	private final int port;
	
	public MagicTVRemote(String ip){
		this.ip = ip;
		this.port = DEFAULT_PORT;
	}
	
	public MagicTVRemote(String ip, int port){
		this.ip = ip;
		this.port = port;
		try {
			gainInfo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getModel(){
		return model;
	}
	
	public void gainInfo() throws IOException{
		byte[] bytes = sendUDPRaw(gainInfo);
		String model = "";
		boolean capModel = false;
		for (int i = 0; i < bytes.length; i++){
			System.out.println(i + " [" + ((char) bytes[i]) + "] " + Integer.toHexString(bytes[i]));
			if (model.equals("") && bytes[i] == 0x5A){
				System.out.println("Start Capturing...");
				capModel = true;
				i++;
				continue;
			}
			if (capModel){
				if (bytes[i] == 0x00){
					System.out.println("End capture");
					capModel = false;
					continue;
				}
				model += (char) bytes[i];
				System.out.println((char)bytes[i]);
			}
		}
		this.model = model;
		System.out.println("[" + model + "]");
	}
	
	public void menu() throws IOException{
		sendUDPRaw(compile(CMD_MENU));
	}
	
	public void power() throws IOException{
		sendUDPRaw(compile(CMD_POWER));
	}
	
	public void guide() throws IOException{
		sendUDPRaw(compile(CMD_GUIDE));
	}
	
	public void text() throws IOException{
		sendUDPRaw(compile(CMD_TEXT));
	}
	
	public void aspect() throws IOException{
		sendUDPRaw(compile(CMD_ASPECT));
	}
	
	public void audio() throws IOException{
		sendUDPRaw(compile(CMD_AUDIO));
	}
	
	public void subtitle() throws IOException{
		sendUDPRaw(compile(CMD_SUBTITLE));
	}
	
	public void back() throws IOException{
		sendUDPRaw(compile(CMD_BACK));
	}
	
	public void up() throws IOException{
		sendUDPRaw(compile(CMD_UP));
	}
	
	public void down() throws IOException{
		sendUDPRaw(compile(CMD_DOWN));
	}
	
	public void left() throws IOException{
		sendUDPRaw(compile(CMD_LEFT));
	}
	
	public void right() throws IOException{
		sendUDPRaw(compile(CMD_RIGHT));
	}
	
	public void info() throws IOException{
		sendUDPRaw(compile(CMD_INFO));
	}
	
	public void ok() throws IOException{
		sendUDPRaw(compile(CMD_OK));
	}
	
	public void volume_up() throws IOException{
		sendUDPRaw(compile(CMD_VOL_UP));
	}
	
	public void volume_down() throws IOException{
		sendUDPRaw(compile(CMD_VOL_DOWN));
	}
	
	public void mute() throws IOException{
		sendUDPRaw(compile(CMD_MUTE));
	}
	
	public void channel_up() throws IOException{
		sendUDPRaw(compile(CMD_CH_UP));
	}
	
	public void channel_down() throws IOException{
		sendUDPRaw(compile(CMD_CH_DOWN));
	}
	
	public void record() throws IOException{
		sendUDPRaw(compile(CMD_REC));
	}
	
	public void pause() throws IOException{
		sendUDPRaw(compile(CMD_PAUSE));
	}
	
	public void stop() throws IOException{
		sendUDPRaw(compile(CMD_STOP));
	}
	
	public void play() throws IOException{
		sendUDPRaw(compile(CMD_PLAY));
	}
	
	public void fast_reverse() throws IOException{
		sendUDPRaw(compile(CMD_FAST_REVERSE));
	}
	
	public void fast_forward() throws IOException{
		sendUDPRaw(compile(CMD_FAST_FORWARD));
	}
	
	public void replay() throws IOException{
		sendUDPRaw(compile(CMD_REPLAY));
	}

	public void skip() throws IOException{
		sendUDPRaw(compile(CMD_SKIP));
	}

	public void source() throws IOException{
		sendUDPRaw(compile(CMD_LIVE_SOURCE));
	}

	public void one() throws IOException{
		sendUDPRaw(compile(CMD_ONE));
	}
	
	public void two() throws IOException{
		sendUDPRaw(compile(CMD_TWO));
	}

	public void three() throws IOException{
		sendUDPRaw(compile(CMD_THREE));
	}

	public void four() throws IOException{
		sendUDPRaw(compile(CMD_FOUR));
	}

	public void five() throws IOException{
		sendUDPRaw(compile(CMD_FIVE));
	}

	public void six() throws IOException{
		sendUDPRaw(compile(CMD_SIX));
	}

	public void seven() throws IOException{
		sendUDPRaw(compile(CMD_SEVEN));
	}

	public void eight() throws IOException{
		sendUDPRaw(compile(CMD_EIGHT));
	}

	public void nine() throws IOException{
		sendUDPRaw(compile(CMD_NINE));
	}

	public void zero() throws IOException{
		sendUDPRaw(compile(CMD_ZERO));
	}
	
	public void clear() throws IOException{
		cross();
	}

	public void cross() throws IOException{
		sendUDPRaw(compile(CMD_CROSS_CLEAR));
	}
	
	public void enter() throws IOException{
		tick();
	}

	public void tick() throws IOException{
		sendUDPRaw(compile(CMD_TICK_ENTER));
	}

	public void red() throws IOException{
		sendUDPRaw(compile(CMD_RED));
	}
	
	public void green() throws IOException{
		sendUDPRaw(compile(CMD_GREEN));
	}
	
	public void yellow() throws IOException{
		sendUDPRaw(compile(CMD_YELLOW));
	}
	
	public void blue() throws IOException{
		sendUDPRaw(compile(CMD_BLUE));
	}
	
	public static byte[] compile(byte[] command){
		byte[] out = new byte[startHex.length + command.length + 1];
		int i = 0;
		for (i = 0; i < startHex.length; i++){
			out[i] = startHex[i];
		}
		int j = 0;
		for (; j < command.length; i++, j++){
			out[i] = command[j];
		}
		out[i] = endingHex;
		return out;
	}
	
	public byte[] sendUDPRaw(byte[] raw) throws IOException{
		System.out.print("Sending: [");
		for (int i = 0; i < raw.length; i++){
			System.out.print(Integer.toHexString(raw[i]));
		}
		System.out.println("]");
	    InetAddress hostAddress = InetAddress.getByName(ip);
		DatagramSocket s = new DatagramSocket(23456);
		
		byte[] buf = new byte[156];
	    
	    DatagramPacket in = new DatagramPacket(buf, buf.length);
	    DatagramPacket out = new DatagramPacket(raw, raw.length, hostAddress, port);
	    s.send(out);
	    
	    s.receive(in);
	    
	    System.out.print("Recevied: [");
	    byte[] recevied = in.getData();
	    for (int i = 0; i < recevied.length; i++){
	    	System.out.print(Integer.toHexString(recevied[i]));
	    }
	    System.out.println("]");
	    System.out.println("Returned OK? " + recevied.equals(returnOK));
	    
	    s.close();
	    return recevied;
	}
}
