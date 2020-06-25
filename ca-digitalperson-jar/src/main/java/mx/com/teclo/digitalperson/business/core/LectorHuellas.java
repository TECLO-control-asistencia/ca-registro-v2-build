package mx.com.teclo.digitalperson.business.core;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.digitalpersona.onetouch.DPFPCaptureFeedback;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.DPFPCapturePriority;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPImageQualityAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPImageQualityEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;

import mx.com.teclo.digitalperson.business.comun.EnumTipoSolicitud;
import mx.com.teclo.digitalperson.business.vo.EstadoVO;
import mx.com.teclo.digitalperson.business.vo.HuellasVO;
import mx.com.teclo.digitalperson.business.vo.SolicitudLecturaVO;

/**
 * Clase que agrupa las funciones del lector
 * 
 * @author beatriz.orosio@unitis.com.mx
 *
 */
public class LectorHuellas extends Observable {
	private final static Logger LOGGER = Logger.getLogger(LectorHuellas.class);
	private static final String MSG_CONECTADO = "¡Lector biom\u00E9trico conectado!";
	private static final String MSG_DESCONECTADO = "¡Lector biom\u00E9trico desconectado!";
	public static final String MSG_TRIGGER_PARA_BUSCAR_SOLICITUD = "Por favor espere...";
	private static final String MSG_LECTOR_DOBLEMENTE_INSTALADO = "Ya esta corriendo la aplicaci\u00F3n";
	private static final String MSG_INICIAL_VERIFICACION = "Verifica tu identidad";
	private static final String MSG_INICIAL_ENROLAMIENTO = "Coloque su dedo para iniciar el enrolamiento";
	private static final String MSG_POST_LECTURA_VERIFICACION = "Retire";
	private static final String MSG_PRE_LECTURA_VERIFICACION = "No retire";
	private static final String MSG_BUENA_CALIDAD_LECTURA = "La calidad de la muestra de huellas dactilares es buena";
	private static final String MSG_MALA_CALIDAD_LECTURA = "La calidad de la muestra de huellas dactilares es pobre";
	private static final String MSG_ERROR_USUARIO_SIN_HUELLAS ="Usuario sin huellas";
	private static final String MSG_FINAL_ENROLAMIENTO_EXITOSO = "La huella se cre\u00F3 correctamente";
	private static final String MSG_FINAL_ENROLAMIENTO_FALLIDO = "La huella no se cre\u00F3 correctamente, vuelva a escanear la huella";
	private static final String MSG_FINAL_VERIFICACION_EXITOSA = "Huella v\u00E1lida";
	private static final String MSG_FINAL_VERIFICACION_FALLIDA = "Huella inv\u00E1lida";
	private static final String MSG_TOMA_CORRECTA_ENROLAMIENTO = "Coloque nuevamente el dedo para continuar el escaneo";
	private List<DPFPTemplate> listTemplates;
	private DPFPCapture capturer;
	private DPFPVerification verificador;
	private DPFPEnrollment enroller;
	private EstadoVO estado;
	private static final int MAX_LECTURAS = 4;

	public LectorHuellas() {
		listTemplates = new ArrayList<DPFPTemplate>();
		capturer = DPFPGlobal.getCaptureFactory().createCapture();
		verificador = DPFPGlobal.getVerificationFactory().createVerification();
		enroller = DPFPGlobal.getEnrollmentFactory().createEnrollment();
		estado = new EstadoVO();

	}

	public EstadoVO getEstado() {
		return estado;
	}

	public void setEstado(EstadoVO estado) {
		this.estado = estado;
		setChanged();
		notifyObservers();
	}

	public void iniciarDispositivo() {
		LOGGER.debug("Iniciando");
		capturer.setPriority(DPFPCapturePriority.CAPTURE_PRIORITY_LOW);
		agregarListeners();
		try {
			capturer.startCapture();
			actualizarMensaje(MSG_TRIGGER_PARA_BUSCAR_SOLICITUD);
		}catch(RuntimeException e) {
			actualizarMensaje(MSG_LECTOR_DOBLEMENTE_INSTALADO);
			LOGGER.error(MSG_LECTOR_DOBLEMENTE_INSTALADO);
			if(capturer.isStarted()) {
				capturer.stopCapture();
			}
			System.exit(BigDecimal.ZERO.intValue());
		}finally {			
		
		}
		
		
	}

	public void procesar(SolicitudLecturaVO solicitud) {
		if (solicitud == null) {
			continuarCentinela();
		} else {
			LOGGER.debug("Paso 2: "+solicitud.getIdCanal());
			if (EnumTipoSolicitud.VERIFICACION.equals(EnumTipoSolicitud.getById(solicitud.getTipoOperacion()))) {
				cargarHuellasDactilaresBD(solicitud.getHuellas());
				actualizarSolicitud(MSG_INICIAL_VERIFICACION, solicitud);
			} else {
				enroller.clear();
				actualizarSolicitud(MSG_INICIAL_ENROLAMIENTO, solicitud);
			}
		}
	}

	private void agregarConectores() {
		capturer.addReaderStatusListener(new DPFPReaderStatusAdapter() {
			@Override
			public void readerConnected(final DPFPReaderStatusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						actualizarConexion(MSG_CONECTADO, Boolean.TRUE.booleanValue());
					}
				});
			}

			@Override
			public void readerDisconnected(final DPFPReaderStatusEvent e) {

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						actualizarConexion(MSG_DESCONECTADO, Boolean.FALSE.booleanValue());
					}
				});
			}
		});
	}

	private void agregarCapturadores() {
		// agregando listeners de captura de huella
		capturer.addDataListener(new DPFPDataAdapter() {
			@Override
			public void dataAcquired(final DPFPDataEvent e) {
				if (!getEstado().isOcupado()) {
					continuarCentinela();
				} else {

					if (getEstado().getSolicitud().getTipoOperacion() == EnumTipoSolicitud.ENROLAMIENTO.getId()) {
						// enrolamiento
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								process(e.getSample());
							}
						});
					} else {
						// verificacion
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								actualizarMensaje(MSG_POST_LECTURA_VERIFICACION);
								LOGGER.debug(MSG_POST_LECTURA_VERIFICACION);
								try {
									validarHuella(e.getSample());
								} catch (InterruptedException e) {
									actualizarMensaje("ERROR " + e.getCause());
								}
							}
						});
					}
				}
			}
		});
	}

	private void agregarSensores() {
		capturer.addSensorListener(new DPFPSensorAdapter() {
			@Override
			public void fingerTouched(final DPFPSensorEvent e) {
				if (getEstado().isOcupado()) {
					if (getEstado().getSolicitud().getTipoOperacion() == EnumTipoSolicitud.ENROLAMIENTO.getId()) {
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								actualizarImagenes("", null, null);
							}
						});
					} else {
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								actualizarMensaje(MSG_PRE_LECTURA_VERIFICACION);
								LOGGER.debug(MSG_PRE_LECTURA_VERIFICACION);
							}
						});
					}
				} else {
					SwingUtilities.invokeLater(new Runnable() {						
						public void run() {
							// Crea mensaje trigger para buscar solicitud
							actualizarMensaje(MSG_TRIGGER_PARA_BUSCAR_SOLICITUD);
							LOGGER.debug(MSG_TRIGGER_PARA_BUSCAR_SOLICITUD);														
						}
					});
				}

			}

			@Override
			public void fingerGone(final DPFPSensorEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {

					}
				});
			}
		});
	}

	private void agregarCalibradores() {
		capturer.addImageQualityListener(new DPFPImageQualityAdapter() {
			@Override
			public void onImageQuality(final DPFPImageQualityEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						if (e.getFeedback().equals(DPFPCaptureFeedback.CAPTURE_FEEDBACK_GOOD)) {
							actualizarMensaje(MSG_BUENA_CALIDAD_LECTURA);
						} else {
							actualizarMensaje(MSG_MALA_CALIDAD_LECTURA);
						}
					}
				});
			}
		});
	}

	private void agregarListeners() {
		agregarConectores();
		agregarCapturadores();
		agregarSensores();
		agregarCalibradores();
	}

	private void continuarCentinela() {
		if (getEstado().isConectado()) {
			actualizarConexion(MSG_CONECTADO, Boolean.TRUE.booleanValue());
		} else {
			actualizarConexion(MSG_DESCONECTADO, Boolean.FALSE.booleanValue());
		}

	}

	private byte[] convertirImagenToArrayBytes(Image imagen) {
		try {
			RenderedImage render = (RenderedImage) imagen;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(render, "jpg", baos);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (IOException e) {
			LOGGER.error("ERROR: " + e.getMessage() + " FIN MENSAJE ERROR");
			return null;
		}
	}

	private Image convertirSampleToBitImage(DPFPSample sample) {
		return DPFPGlobal.getSampleConversionFactory().createImage(sample);
	}

	private DPFPFeatureSet extraerFeatures(DPFPSample sample, DPFPDataPurpose purpose) {
		DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
		try {
			return extractor.createFeatureSet(sample, purpose);
		} catch (DPFPImageQualityException e) {
			return null;
		}
	}
	
	/**
	 * Para actualiza el mensaje
	 * @param mensaje
	 */
	public void actualizarMensaje(String mensaje) {
		LOGGER.debug("actualizarMensaje "+(estado.getSolicitud() == null ? "":estado.getSolicitud().getIdCanal()));
		setEstado(new EstadoVO(mensaje, estado));
	}

	/**
	 * Para actualizar imagen y/o template	
	 * @param mensaje
	 * @param imagen
	 * @param template
	 */
	public void actualizarImagenes(String mensaje, byte[] imagen, byte[] template) {
		LOGGER.debug("actualizarImagen "+(estado.getSolicitud() == null ? "":estado.getSolicitud().getIdCanal()));
		setEstado(new EstadoVO(mensaje, imagen, template, estado));
	}

	/**
	 * Para actualizar conexión
	 * @param mensaje
	 * @param isConectado
	 */
	public void actualizarConexion(String mensaje, boolean isConectado) {
		LOGGER.debug("actualizarConexion "+(estado.getSolicitud() == null ? "":estado.getSolicitud().getIdCanal()));
		setEstado(new EstadoVO(mensaje, isConectado, estado));
	}

	/**
	 * Para actualizar solicitud
	 * @param mensaje
	 * @param solicitud
	 */
	public void actualizarSolicitud(String mensaje, SolicitudLecturaVO solicitud) {
		LOGGER.debug("actualizarSolicitud "+(solicitud == null ? "":solicitud.getIdCanal()));
		setEstado(reiniciarEstado(mensaje, solicitud));
	}

	protected void removerDataListeners() {
		DPFPDataAdapter[] dataListeners = capturer.getListeners(DPFPDataAdapter.class);
		for (DPFPDataAdapter dpfpDataAdapter : dataListeners) {
			capturer.removeDataListener(dpfpDataAdapter);
		}
	}

	protected void removerSensorListeners() {
		DPFPSensorAdapter[] sensorListeners = capturer.getListeners(DPFPSensorAdapter.class);

		for (DPFPSensorAdapter dpfpSensorAdapter : sensorListeners) {
			capturer.removeSensorListener(dpfpSensorAdapter);
		}
	}

	@SuppressWarnings("unused")
	private EstadoVO reiniciarEstado(String mensaje, SolicitudLecturaVO solicitud) {
		LOGGER.debug("Paso 3: Actualizando solicitud "+solicitud.getIdCanal());
		EstadoVO estado = new EstadoVO();
		if (solicitud == null) {			
			estado.setConectado(getEstado().isConectado());
			estado.setOcupado(false);
			estado.setMensaje(mensaje);
			estado.setImagen(null);
			estado.setTemplate(null);
			estado.setSolicitud(null);
			return estado;
		}
		
		estado.setConectado(getEstado().isConectado());
		estado.setOcupado(true);
		estado.setMensaje(mensaje);
		estado.setImagen(null);
		estado.setTemplate(null);
		estado.setSolicitud(solicitud);
		return estado;
	}

	private void validarHuella(DPFPSample sample) throws InterruptedException {
		DPFPFeatureSet features = extraerFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
		byte[] imagenCapturada = convertirImagenToArrayBytes(convertirSampleToBitImage(sample));
		
		if (features == null) {
			actualizarImagenes(MSG_ERROR_USUARIO_SIN_HUELLAS, null, null);
			LOGGER.error(MSG_ERROR_USUARIO_SIN_HUELLAS);
			terminar();
			return;
		}

		if (isHuellaValida(listTemplates, features)) {
			actualizarImagenes(MSG_FINAL_VERIFICACION_EXITOSA, imagenCapturada, null);
			LOGGER.info(MSG_FINAL_VERIFICACION_EXITOSA);
			terminar();
		} else {
			actualizarImagenes(MSG_FINAL_VERIFICACION_FALLIDA, imagenCapturada, null);
			LOGGER.info(MSG_FINAL_VERIFICACION_FALLIDA);
			terminar();

		}

	}

	private boolean isHuellaValida(List<DPFPTemplate> listTemplates, DPFPFeatureSet features) {
		for (DPFPTemplate template : listTemplates) {
			DPFPVerificationResult result = verificador.verify(features, template);
			if (result.isVerified()) {
				return true;
			}
		}
		return false;
	}

	private void process(DPFPSample sample) {
		DPFPFeatureSet features = extraerFeatures(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);
		if (features != null)
			try {
				enroller.addFeatures(features);
				Image image = convertirSampleToBitImage(sample);
				if (image != null && enroller.getFeaturesNeeded() < MAX_LECTURAS
						&& enroller.getFeaturesNeeded() >= BigDecimal.ZERO.intValue()) {
					actualizarImagenes(getMensajeToma(), convertirImagenToArrayBytes(image), null);
				}
			} catch (DPFPImageQualityException ex) {
				LOGGER.error("Error de calidad");
			} finally {
				generarHuella();
			}
	}

	private void generarHuella() {
		if (enroller.getFeaturesNeeded() == BigDecimal.ZERO.intValue()) {
			byte[] nvoTemplate = enroller.getTemplate().serialize();
			switch (enroller.getTemplateStatus()) {
			case TEMPLATE_STATUS_READY:				
				actualizarImagenes(MSG_FINAL_ENROLAMIENTO_EXITOSO, null, nvoTemplate );
				LOGGER.debug(MSG_FINAL_ENROLAMIENTO_EXITOSO);
				terminar();
				break;
			case TEMPLATE_STATUS_FAILED:				
				actualizarImagenes(MSG_FINAL_ENROLAMIENTO_FALLIDO, null, nvoTemplate);
				terminar();
				LOGGER.debug(MSG_FINAL_ENROLAMIENTO_FALLIDO);
				break;
			default:
				break;
			}
		}
	}

	public void terminar() {
		listTemplates = new ArrayList<DPFPTemplate>();
		enroller.clear();

		EstadoVO nvoEstado = new EstadoVO();
		nvoEstado.setConectado(estado.isConectado());
		nvoEstado.setOcupado(false);
		nvoEstado.setMensaje(estado.getMensaje());
		nvoEstado.setImagen(null);
		nvoEstado.setTemplate(null);
		nvoEstado.setSolicitud(null);
		setEstado(nvoEstado);
	}

	private void cargarHuellasDactilaresBD(List<HuellasVO> huellasBD) {
		for (HuellasVO huellaVO : huellasBD) {
			if (huellaVO != null && huellaVO.getHuella() != null) {
				DPFPTemplate template = DPFPGlobal.getTemplateFactory().createTemplate(huellaVO.getHuella());
				listTemplates.add(template);
			}
		}
	}
	
	private String getMensajeToma() {		
		int toma = MAX_LECTURAS - enroller.getFeaturesNeeded();
		StringBuilder sb = new StringBuilder();
		sb.append("Toma ").append(toma).append(" correcta. ")
				.append(MSG_TOMA_CORRECTA_ENROLAMIENTO);
		return sb.toString();
	}

}