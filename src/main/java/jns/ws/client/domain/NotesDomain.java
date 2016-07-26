package jns.ws.client.domain;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import jns.ws.client.data.Note;

public class NotesDomain {
	private final WebTarget wt;
	
	public NotesDomain() {
		wt = ClientBuilder.newClient().target("http://localhost:8082/JNotesWS");
	}
	
	public Note insertarNota(String titulo, String contenido){
		try {			
			JSONObject notaObj = new JSONObject(toNote(titulo, contenido));
			String notaJson = notaObj.toString();

			String str = wt.request("application/json").post(Entity.entity(notaJson, MediaType.APPLICATION_JSON), String.class);
						
//			System.out.println(str2);
			System.out.println(str);
			
			notaObj = new JSONObject(str);
			Note n = new Note();
			n.setId(notaObj.getInt("id"));
			n.setTitulo(notaObj.getString("titulo"));
			n.setContenido(notaObj.getString("contenido"));
			
			return n;
		} catch (Exception e) {
			return null;
		}		
	}
	
	public List<Note> getAllNotes(){
		List<Note> notas = new ArrayList<Note>();

		String str2  = wt.request("text/plain").get(String.class);	
		
		JSONArray jArr = new JSONArray(str2);
		
		for (int i = 0; i < jArr.length(); i++) {
			JSONObject nota = (JSONObject) jArr.get(i);
			Note n = new Note();
			n.setId(nota.getInt("id"));
			n.setTitulo(nota.getString("titulo"));
			n.setContenido(nota.getString("contenido"));
			
			notas.add(n);
		}
		
		return notas;
	}
	
	public Note toNote(String titulo, String contenido){
		return new Note(titulo, contenido);
	}

	public String toJson(Note note){
		return new JSONObject(note).toString();
	}
	
	public void deleteNote(Note note){
		wt.path("/"+note.getId()).request().delete();	
	}

	public void updateNote(Note note){
		Response r = wt.path("/"+note.getId()).request().put(Entity.entity(toJson(note), MediaType.APPLICATION_JSON));
		
		System.out.println(r.getEntity());
	}
}
