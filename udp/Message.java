package udp;

/*
 * Message.java
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
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SuppressWarnings("serial")
public class Message<T> implements Serializable {

	/**
	 * .
	 */
	public T msg;
	/** destination port */
	public int destPort;
	/** destination address */
	public InetAddress destAddress;

	public Message(InetAddress destAddr, int port) {
		this(port);
		destAddress = destAddr;
	}

	public Message(InetAddress destAddr, int port, T msg) {
		this(destAddr, port);
		this.msg = msg;
	}

	private Message(int port) {
		destPort = port;
	}

	public Message(String destinationHost, int port)
			throws UnknownHostException {
		this(port);
		destAddress = InetAddress.getByName("127.0.0.1");
	}

	public Message(String destinationHost, int port, T msg)
			throws UnknownHostException {
		this(destinationHost, port);
		this.msg = msg;
	}

	public void setMessageBody(T o) {
		msg = o;
	}

	@Override
	public String toString() {
		return msg.toString();
	}

}
