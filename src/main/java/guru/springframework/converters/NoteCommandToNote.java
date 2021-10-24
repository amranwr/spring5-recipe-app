package guru.springframework.converters;

import guru.springframework.commands.NoteCommand;
import guru.springframework.domain.Note;
import org.springframework.core.convert.converter.Converter;

public class NoteCommandToNote implements Converter<NoteCommand, Note> {
    @Override
    public Note convert(NoteCommand notecommandd) {
        if(notecommandd == null){
            return null ;
        }
        Note note = new Note();
        note.setId(notecommandd.getId());
        note.setDescription(notecommandd.getDescription());
        return note;
    }
}
