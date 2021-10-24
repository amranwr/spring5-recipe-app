package guru.springframework.converters;

import guru.springframework.commands.NoteCommand;
import guru.springframework.domain.Note;
import org.springframework.core.convert.converter.Converter;

public class NoteToNoteCommand implements Converter<Note, NoteCommand> {
    @Override
    public NoteCommand convert(Note note) {
        if(note == null){
            return null ;
        }
        NoteCommand noteCommand = new NoteCommand();
        noteCommand.setId(note.getId());
        noteCommand.setDescription(note.getDescription());
        return noteCommand;
    }
}
