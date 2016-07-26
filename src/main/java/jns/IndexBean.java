package jns;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import jns.ws.client.data.Note;
import jns.ws.client.domain.NotesDomain;

@ManagedBean
@SessionScoped
public class IndexBean implements Serializable{
	private static final long serialVersionUID = 1L;

	public IndexBean() {
		nd = new NotesDomain();
		List<Note> notas = nd.getAllNotes();
		Collections.reverse(notas);
		setNotas(notas);
	}
	private List<Note> notas;
	private NotesDomain nd;
	private Note notaSelec;

	public List<Note> getNotas() {		
		return notas;
	}

	public void setNotas(List<Note> notas) {
		this.notas = notas;
	}

	public Note getNotaSelec() {
		return notaSelec;
	}

	public void setNotaSelec(Note notaSelec) {
		this.notaSelec = notaSelec;
	}
}
