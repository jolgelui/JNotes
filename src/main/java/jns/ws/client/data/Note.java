package jns.ws.client.data;

public class Note {
	public Note() {
	}
	
	public Note(String titulo, String contenido){
		setTitulo(titulo);
		setContenido(contenido);
	}

	private Integer id;
	private String titulo;
	private String contenido;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}	
}
