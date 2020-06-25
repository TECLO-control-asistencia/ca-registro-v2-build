package mx.com.teclo.lectorhuella.base.app;

/**
 * Clase singleton que mantiene los parametros recibidos
 * 
 * @author UNITIS-ODM2
 *
 */
public class AppConfiguracion {
	private static AppConfiguracion instancia = null;
	private Long idLector;
	private String url;
	private Boolean appVisible;
	private String token;

	private AppConfiguracion(Long idLector, String url, Boolean appVisible, String token) {
		this.idLector = idLector;
		this.url = url;
		this.appVisible = appVisible;
		this.token = token;
	}

	public static AppConfiguracion configInstance(Long idLector, String url, Boolean appVisible, String token) {
		if (instancia == null) {
			instancia = new AppConfiguracion(idLector, url, appVisible, token);
		}
		return instancia;
	}

	public static AppConfiguracion getInstance() {
		return instancia;
	}

	public Long getIdLector() {
		return idLector;
	}

	public void setIdLector(Long idLector) {
		this.idLector = idLector;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getAppVisible() {
		return appVisible;
	}

	public void setAppVisible(Boolean appVisible) {
		this.appVisible = appVisible;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
