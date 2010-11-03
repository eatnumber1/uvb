package com.eatnumber1.uvb;

import com.eatnumber1.uvb.ai.AvoidObjectSenator;
import com.eatnumber1.uvb.ai.CollisionAvoidanceSenator;
import com.eatnumber1.uvb.ai.MoveEverywhereSenator;
import com.eatnumber1.uvb.ai.MoveToEngageSenator;
import com.eatnumber1.uvb.ai.Senate;
import com.eatnumber1.uvb.ai.SimpleSenate;
import com.eatnumber1.uvb.board.BoardObjectType;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Properties;

public class ClientMain {
	public static final String HOSTNAME_PROPERTY_NAME = ClientMain.class.getCanonicalName() + ".hostname";
	public static final String PORT_PROPERTY_NAME = ClientMain.class.getCanonicalName() + ".port";

	public static void main( String[] args ) throws IOException {
		Properties keyProp = new Properties();
		InputStream keyInput = ClassLoader.getSystemResourceAsStream("key.properties");
		try {
			keyProp.load(keyInput);
		} finally {
			keyInput.close();
		}
		String key = keyProp.getProperty("key");

		InputStream certStream = ClassLoader.getSystemResourceAsStream("opcomm.crt");
		SSLContext ctx;
		try {
			Certificate cert = CertificateFactory.getInstance("X.509").generateCertificate(certStream);
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			ks.load(null, null);
			ks.setCertificateEntry("opcomm", cert);

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
			Server server = new Server(socket.getInputStream(), socket.getOutputStream());
			RequestDispatcher dispatcher = new RequestDispatcher();
			dispatcher.addHandler(Request.KEY, new KeyRequestHandler(key));
			Senate senate = new SimpleSenate();
			senate.addSenator(new MoveEverywhereSenator());
			senate.addSenator(new MoveToEngageSenator());
			senate.addSenator(new CollisionAvoidanceSenator(BoardObjectType.TREE));
			senate.addSenator(new CollisionAvoidanceSenator(BoardObjectType.EDGE));
			senate.addSenator(new CollisionAvoidanceSenator(BoardObjectType.SNOWMAN));
			senate.addSenator(new AvoidObjectSenator(BoardObjectType.PLAYER));
			senate.addSenator(new AvoidObjectSenator(BoardObjectType.SNOWBALL));
			dispatcher.addHandler(Request.MOVE, new MoveRequestHandler(senate));
			while( server.await() ) dispatcher.dispatch(Request.getCommand(server.read()), server);
		} finally {
			socket.close();
		}
	}
}
