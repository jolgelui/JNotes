package jns;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.context.RequestContext;

import jns.ws.client.data.Note;
import jns.ws.client.domain.NotesDomain;

@ManagedBean
@SessionScoped
public class BeanText {
	private String noteTitle;
	private String noteContent;
	private boolean isEditing;
	
	private Mode dialogMode;
	public enum Mode{View, Add, Update}
	
	@ManagedProperty(value = "#{indexBean}")
	private IndexBean indexB;
	
	public String getNoteTitle() {
		return noteTitle;
	}

	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}

	public String getNoteContent() {
		return noteContent;
	}

	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}

	public void addOrUpdateNote(){
		if (isEditing) {
			Note n = getIndexB().getNotaSelec();
			
			n.setContenido(getNoteContent());
			n.setTitulo(getNoteTitle());
			
			new NotesDomain().updateNote(n);
		}else {
			NotesDomain nd = new NotesDomain();
			getIndexB().getNotas().add(0, nd.insertarNota(getNoteTitle(), getNoteContent()));
						
			cleanFields();
			
			isEditing = false;				
		}

		RequestContext.getCurrentInstance().execute("PF('diag1').hide();");
	}

	public void deleteNote(){
		new NotesDomain().deleteNote(getIndexB().getNotaSelec());
		getIndexB().getNotas().remove(getIndexB().getNotaSelec());
	}
	
	public void onUpdateNote(){
		setNoteContent(getIndexB().getNotaSelec().getContenido());
		setNoteTitle(getIndexB().getNotaSelec().getTitulo());
		isEditing = true;
		setDialogMode(Mode.Update);
		
		RequestContext.getCurrentInstance().execute("PF('diag1').show();");
	}
	
	public void onAddNote(){
		cleanFields();
		
		isEditing = false;
		setDialogMode(Mode.Add);
		RequestContext.getCurrentInstance().execute("PF('diag1').show();");		
	}
	
	public void onViewNote(){
		setNoteContent(getIndexB().getNotaSelec().getContenido());
		setNoteTitle(getIndexB().getNotaSelec().getTitulo());
		
		setDialogMode(Mode.View);
		
		RequestContext.getCurrentInstance().execute("PF('diag1').show();");
		
	}
	
	public void cleanFields(){
		setNoteContent(null);
		setNoteTitle(null);
	}

	public IndexBean getIndexB() {
		return indexB;
	}

	public void setIndexB(IndexBean indexB) {
		this.indexB = indexB;
	}
	
	public boolean isEditing() {
		return isEditing;
	}

	public void setEditing(boolean isEditing) {
		this.isEditing = isEditing;
	}

	public Mode getDialogMode() {
		return dialogMode;
	}

	public void setDialogMode(Mode dialogMode) {
		this.dialogMode = dialogMode;
	}
}
