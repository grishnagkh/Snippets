package at.grish.net.udp;

/*
 * UDPMessageHandler.java
 *
 * Copyright (c) 2015, Stefan Petscharnig. All rights reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301 USA
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 * a message handler which forwards the received udp packages to the
 * synchronization modules
 *
 * @author stefan petscharnig
 *
 */

public class UDPMessageHandler extends Thread {

	/** singleton method using default port */
	public static UDPMessageHandler getInstance() {
		if (instance == null)
			throw new RuntimeException(
					"initialize the udpmessagehandler before yxou get the instance!");
		return instance;
	}

	/** singleton method using custom port */
	private static UDPMessageHandler getInstance(int port) {
		if (instance == null) {
			instance = new UDPMessageHandler(port);
		}
		return instance;
	}

	public static void init(int port, MsgObserver... msgObs) {
		observers = msgObs;
		getInstance(port);

	}

	public static MsgObserver[] observers;

	/** default port where we listen for synchronization messages */
	public static final int PORT = 12346;

	/** length of the receive buffer */
	public static final int RCF_BUF_LEN = 4096; // let us have a 4k buffer..

	/** UDP socket for receiving messages */
	private DatagramSocket serverSocket;
	/** receive Buffer */
	private byte[] rcvBuf = new byte[RCF_BUF_LEN];

	private ByteArrayInputStream byteStream;
	private ObjectInputStream is;

	/** actual port to listen */
	private int port;
	/** singleton instance */
	private static UDPMessageHandler instance;

	private DatagramPacket rcv;

	/** singleton constructor using default port */
	private UDPMessageHandler() {
		this(PORT);
	}

	/** singleton constructor using custom port */
	private UDPMessageHandler(int port) {
		this.port = port;
	}

	@Override
	public void interrupt() {

		if (serverSocket != null && is != null) {
			try {
				is.close();
				if (!serverSocket.isClosed()) {
					serverSocket.close();
				}
			} catch (IOException e) {
			}
		}
		instance = null;
		super.interrupt();
	}

	private void notify(Message m) {
		for (MsgObserver o : observers) {
			o.notify(m);
		}
	}

	@Override
	public void run() {

		while (!isInterrupted()) {
			Object readObj = null;
			try {
				rcv.setLength(RCF_BUF_LEN);
				serverSocket.receive(rcv);

				byteStream = new ByteArrayInputStream(rcvBuf);
				is = new ObjectInputStream(new BufferedInputStream(byteStream));
				readObj = is.readObject();

			} catch (IOException e) {
				continue;
			} catch (ClassNotFoundException e) {
				continue;
			} catch (Exception e) {
				continue;
			}
			if (readObj instanceof Message) {
				notify((Message) readObj);
			} else {
				// TODO
			}

		}
	}

	/**
	 * Method for sending messages via UDP
	 *
	 * @param msg
	 *            The message string to send
	 * @param destAddress
	 *            receive Inetaddress
	 * @param destPort
	 *            receive prot
	 */

	public synchronized void sendMsg(Message msg) {

		try {

			ByteArrayOutputStream byteStream = new ByteArrayOutputStream(4092);
			ObjectOutputStream os = new ObjectOutputStream(
					new BufferedOutputStream(byteStream));
			os.flush();
			os.writeObject(msg);
			os.flush();

			byte[] sendBuf = byteStream.toByteArray();
			DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length,
					msg.destAddress, msg.destPort);
			DatagramSocket clientSocket = new DatagramSocket();
			clientSocket.send(packet);
			clientSocket.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	@Override
	public void start() {

		try {
			serverSocket = new DatagramSocket(port);
			serverSocket.setSoTimeout(0);
		} catch (SocketException e1) {
		}
		rcv = new DatagramPacket(rcvBuf, RCF_BUF_LEN);

		super.start();
	}

	/**
	 * the method of choice when you want to start the handling :)
	 */
	public void startHandling() {
		startHandling(true);
	}

	/**
	 * start the handling of messages, starts a thread waiting for incoming
	 * messages and distributing the work to the sync modules
	 */

	private void startHandling(boolean active) {
		if (active) {
			this.start();
		} else
			throw new RuntimeException("inactive start not yet implemented");
	}

	/**
	 * stop the listener for requests
	 *
	 * @param clearSessionData
	 */
	public void stopHandling() {
		this.interrupt();
	}

}
