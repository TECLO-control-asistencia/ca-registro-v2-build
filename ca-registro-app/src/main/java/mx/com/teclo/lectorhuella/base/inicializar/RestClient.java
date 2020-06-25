package mx.com.teclo.lectorhuella.base.inicializar;

import org.apache.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import mx.com.teclo.digitalperson.business.vo.EstadoVO;
import mx.com.teclo.digitalperson.business.vo.MessageVO;
import mx.com.teclo.digitalperson.business.vo.MsgRespuestaVO;
import mx.com.teclo.digitalperson.business.vo.SolicitudLecturaVO;
import mx.com.teclo.lectorhuella.base.configuration.BeanLocator;


public class RestClient {
	
	private static final Logger LOGGER = Logger.getLogger(RestClient.class);

	private String urlRestApi;
	private String token;
	
	private RestTemplate restTemplate = new RestTemplate();

	public SolicitudLecturaVO getUltimaSolicitud(Long idLector) {
		try {
			String rest_url = urlRestApi + "/solicitud/ultima?idLector=" + idLector;
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", token);
			HttpEntity<SolicitudLecturaVO> requestEntity = new HttpEntity<>(null, headers);
			ResponseEntity<SolicitudLecturaVO> response = restTemplate.exchange(rest_url, HttpMethod.GET, requestEntity,
					new ParameterizedTypeReference<SolicitudLecturaVO>() {
					});
			SolicitudLecturaVO solicitud = response.getBody();
			if (solicitud != null) {
				LOGGER.info("Paso 1: Atendiendo solicitud " + solicitud.getIdSolicitud() + " por canal "
						+ solicitud.getIdCanal());
			}
			return solicitud;
		} catch (ResourceAccessException e) {
			return null;
		}
	}

	public void sendMessage(final EstadoVO estadoVO) {
		RestTemplate restTemplate = (RestTemplate) BeanLocator.getService("restTemplate");
		try {
			String rest_url = urlRestApi + "/mensaje/respuesta";
			MsgRespuestaVO msgRespuestaVO = new MsgRespuestaVO();
			msgRespuestaVO.setIdCanal(estadoVO.getSolicitud().getIdCanal());
			msgRespuestaVO.setMensaje(new MessageVO(estadoVO));
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", token);
			HttpEntity<MsgRespuestaVO> requestEntity = new HttpEntity<>(msgRespuestaVO, headers);
			restTemplate.exchange(rest_url, HttpMethod.POST, requestEntity, String.class);
			// restTemplate.postForEntity(rest_url, new HttpEntity<>(msgRespuestaVO,
			// headers), String.class);
			LOGGER.debug("Enviado " + estadoVO.getMensaje().toString() + " por canal "
					+ estadoVO.getSolicitud().getIdCanal());
		} catch (HttpServerErrorException e) {
			e.printStackTrace();
		}

	}

	public void sendPing(Long idLector) {
		RestTemplate restTemplate = (RestTemplate) BeanLocator.getService("restTemplate");
		try {
			String rest_url = urlRestApi + "/lector/ping?idLector=" + idLector;
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", token);
			HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
			restTemplate.exchange(rest_url, HttpMethod.PUT, requestEntity, String.class);
		} catch (ResourceAccessException e) {
			e.printStackTrace();
		}
	}

	public String getUrlRestApi() {
		return urlRestApi;
	}

	public void setUrlRestApi(String urlRestApi) {
		this.urlRestApi = urlRestApi;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}