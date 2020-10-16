package com.lubinpc.todolist.ui.notes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lubinpc.todolist.R;
import com.lubinpc.todolist.databinding.ItemNoteBinding;
import com.lubinpc.todolist.models.NoteVM;

import java.util.List;

import kotlin.jvm.functions.Function1;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<NoteVM> notes;
    private Function1<NoteVM, Void> click;

    public NoteAdapter(List<NoteVM> notes, Function1<NoteVM, Void> click) {
        this.notes = notes;
        this.click = click;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_note, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        holder.vBind.setNote(notes.get(position));
        holder.vBind.mcvNote.setOnClickListener(v -> click.invoke(notes.get(position)));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ItemNoteBinding vBind;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vBind = ItemNoteBinding.bind(itemView);
        }
    }

}
