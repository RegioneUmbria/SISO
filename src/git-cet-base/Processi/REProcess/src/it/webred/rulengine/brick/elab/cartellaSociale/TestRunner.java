package it.webred.rulengine.brick.elab.cartellaSociale;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class TestRunner {

	void test() throws SQLException, ClassNotFoundException, DataAccessException, GeocodingException {
		Connection connection = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@172.29.0.255:1521/DBCAT", "CS_DEV2", "CS_DEV2");

			ArgoDbClient argoDbClient = new ArgoDbClient(connection);
			List<IndirizziZoneDto> addressBatch = argoDbClient.findUnmappedAddresses();
			for (IndirizziZoneDto dto : addressBatch) {
				System.out.println(dto);
			}

			GoogleGeocodeClient client = new GoogleGeocodeClient("123");//Test
			client.geolocalizzaIndirizzi(addressBatch);
			//client.geoLocalize(addressBatch); // TODO

			for (IndirizziZoneDto dto : addressBatch) {
				System.out.println(dto);
			}
			
			argoDbClient.updateMappedAddresses(addressBatch);

		}
		finally {
			if (connection != null)
				connection.close();
		}
	}

	public static void main(String[] args) throws Exception {
		TestRunner testRunner = new TestRunner();
		testRunner.test();
	}

}
