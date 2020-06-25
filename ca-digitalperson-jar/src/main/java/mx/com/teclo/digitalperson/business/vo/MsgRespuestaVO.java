package mx.com.teclo.digitalperson.business.vo;

public class MsgRespuestaVO {
	private String idCanal;
	private MessageVO mensaje;

	public MsgRespuestaVO() {

	}

	public MsgRespuestaVO(String idCanal, MessageVO mensaje) {
		this.idCanal = idCanal;
		this.mensaje = mensaje;

	}

	public String getIdCanal() {
		return idCanal;
	}

	public void setIdCanal(String idCanal) {
		this.idCanal = idCanal;
	}

	public MessageVO getMensaje() {
		return mensaje;
	}

	public void setMensaje(MessageVO mensaje) {
		this.mensaje = mensaje;
	}

}
