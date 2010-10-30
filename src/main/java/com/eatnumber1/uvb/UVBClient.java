package com.eatnumber1.uvb;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Properties;
import java.util.Scanner;

public class UVBClient {
	public static final String HOSTNAME_PROPERTY_NAME = UVBClient.class.getCanonicalName() + ".hostname";
	public static final String PORT_PROPERTY_NAME = UVBClient.class.getCanonicalName() + ".port";

	public static void main( String[] args ) throws IOException {
		Properties keyProp = new Properties();
		InputStream keyInput = new FileInputStream(args[0]);
		try {
			keyProp.load(keyInput);
		} finally {
			keyInput.close();
		}
		String key = keyProp.getProperty("key");

		File certFile = new File(args[1]);
		InputStream certStream = new FileInputStream(certFile);
		SSLContext ctx;
		try {
			Certificate cert = CertificateFactory.getInstance("X.509").generateCertificate(certStream);
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			ks.load(null, null);
			ks.setCertificateEntry(certFile.getName(), cert);

			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(ks);

			ctx = SSLContext.getInstance("TLS");
			ctx.init(null, tmf.getTrustManagers(), null);
		} catch( KeyStoreException e ) {
			throw new RuntimeException(e);
		} catch( CertificateException e ) {
			throw new RuntimeException(e);
		} catch( NoSuchAlgorithmException e ) {
			throw new RuntimeException(e);
		} catch( KeyManagementException e ) {
			throw new RuntimeException(e);
		} finally {
			certStream.close();
		}
		Socket socket = ctx.getSocketFactory().createSocket(System.getProperty(HOSTNAME_PROPERTY_NAME, "uvb.csh.rit.edu"), Integer.getInteger(PORT_PROPERTY_NAME, 13783));
		try {
			PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("ASCII")), true);
			Scanner in = new Scanner(socket.getInputStream());
			CommandDispatcher dispatch = new CommandDispatcher();
			dispatch.addHandler(Command.KEY, new KeyCommandHandler(key));
			dispatch.addHandler(Command.MOVE, new MoveCommandHandler());
			while( in.hasNextLine() ) {
				dispatch.dispatch(Command.getCommand(in.nextLine()), out, in);
			}
		} finally {
			socket.close();
		}
	}
}
