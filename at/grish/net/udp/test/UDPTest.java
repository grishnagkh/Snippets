package at.grish.net.udp.test;

import java.net.UnknownHostException;
import java.util.Scanner;

import at.grish.net.udp.Message;
import at.grish.net.udp.MsgObserver;
import at.grish.net.udp.UDPMessageHandler;

public class UDPTest {

	public static void main(String[] args) {
		UDPMessageHandler mh;
		Scanner sc = new Scanner(System.in);
		System.out.println("PORT:");
		int port = sc.nextInt();



		UDPMessageHandler.init(port, new MsgObserver() {
			@Override
			public void notify(Message m) {
				System.out.println("notified");
				System.out.println(m);
			}
		});

		mh = UDPMessageHandler.getInstance();

		mh.startHandling();

		System.out.println("0 for exit, or numbner for destination port");
		int dp = sc.nextInt();
		while (dp != 0) {
			try {
				mh.sendMsg(new Message<String>("127.0.0.1", dp, "test"));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			dp = sc.nextInt();
		}

		mh.stopHandling();
		sc.close();
	}
}
