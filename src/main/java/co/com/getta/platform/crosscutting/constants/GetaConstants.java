package co.com.getta.platform.crosscutting.constants;

public class GetaConstants {

	private GetaConstants() {
	}

	public static final String ROW_KEY = "0d9d8741-57fc-4bfe-950c-9dc5dc1a7egtt";
	public static final String PARTITION_KEY = "0d9d8741-57fc-4bfe-950c-9dc5dc1a7gtb";
	public static final String NAME_TABLE_RULES = "Documents";
	public static final String KEY_JSON_RULES = "Data";
	public static final String KEY_ROOT_JSON = "TournamentRules";

	// Conection String
	public static final String CONECTING_STRING_AZURE_STORAGE = "${spring.data.azuredb.conecttion}";

	// Message
	public static final String KEY_NOT_FOUND = "not.found.key";
	public static final String BAD_REQUEST = "bad.request";

}
