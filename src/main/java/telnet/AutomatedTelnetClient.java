/**
 * 
 */
package telnet;

import org.apache.commons.net.telnet.TelnetClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.PrintStream;

/**
 * @author alex_jd
 * http://twit88.com/blog/2007/12/22/java-writing-an-automated-telnet-client/
 */
public class AutomatedTelnetClient {
	private TelnetClient telnet = new TelnetClient();
	private InputStream in;
	private PrintStream out;
	private String prompt = ">";

	private Logger logger = LoggerFactory.getLogger(AutomatedTelnetClient.class);

	public AutomatedTelnetClient(String server, String user, String password) {
		try {
			// Connect to the specified server
			telnet.connect(server, 23);

			// Get input and output stream references
			in = telnet.getInputStream();
			out = new PrintStream(telnet.getOutputStream());

			// Log the user on
			readUntil("Username:");
			write(user);
			readUntil("Password:");
			write(password);

			// Advance to a prompt
			readUntil(prompt + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void su(String password) {
		try {
			write("su");
			readUntil("Password: ");
			write(password);
			prompt = "#";
			readUntil(prompt + " ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String readUntil(String pattern) {
		try {
			char lastChar = pattern.charAt(pattern.length() - 1);
			StringBuffer sb = new StringBuffer();
			boolean found = false;
			char ch = (char) in.read();
			while (true) {
				System.out.print(ch);
				sb.append(ch);
				if (ch == lastChar) {
					if (sb.toString().endsWith(pattern)) {
						return sb.toString();
					}
				}
				ch = (char) in.read();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void write(String value) {
		try {
			out.println(value);
			out.flush();
			System.out.println(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String sendCommand(String command) {
		try {
			write(command);
			return readUntil(prompt + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void disconnect() {
		try {
			logger.info("Try to disconnect telnet connection. Current telnet status is {}", telnet.isConnected());
			telnet.disconnect();
			logger.info("Telnet status (false - disconnect) {}", telnet.isConnected());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

	}
}