package com.example.sampleapi.service;

import com.example.sampleapi.dto.Note;
import com.example.sampleapi.exception.NoteArgumentNotValidException;
import com.example.sampleapi.exception.NoteNotFoundException;
import com.example.sampleapi.repository.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    public Note saveNote(Note note){
        //titleが空の場合のバリデーション
        if(note.getTitle() == null || note.getTitle().isEmpty()){
            throw new NoteArgumentNotValidException("Title is empty");
        }

        //contentが空の場合のバリデーション
        if(note.getContent() == null || note.getContent().isEmpty()){
            throw new NoteArgumentNotValidException("Content is empty");
        }

        noteRepository.insertNote(note);
        return note;
    }
    public Note findById(Long id){
        Note note = noteRepository.findById(id);

        if(note == null){
            throw new NoteNotFoundException(id);
        }
        return note;
    }

    public boolean delete(long id){
        if(!noteRepository.delete(id)){
            throw new NoteNotFoundException(id);
        }
        return true;
    }

    @Transactional
    public Note update(Note note, long id){
        //titleが空の場合のバリデーション
        if(note.getTitle() == null || note.getTitle().isEmpty()){
            throw new NoteArgumentNotValidException("Title is empty");
        }

        //contentが空の場合のバリデーション
        if(note.getContent() == null || note.getContent().isEmpty()){
            throw new NoteArgumentNotValidException("Content is empty");
        }

        //指定されたIDが存在しない場合のバリデーション
        Note targetNote = noteRepository.findById(id);
        if (targetNote == null){
            throw new NoteNotFoundException(id);
        }
        note.setId(id);
        note.setCreatedAt(targetNote.getCreatedAt());
        return noteRepository.update(note);
    }
}
