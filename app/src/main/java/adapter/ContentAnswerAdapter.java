package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olx.R;

import java.util.List;

import model.ContentAnswerModel;

public class ContentAnswerAdapter extends RecyclerView.Adapter<ContentAnswerAdapter.ContentAnswerViewHolder> {
    private Context mcontext;
    private List<ContentAnswerModel> entity;

    public ContentAnswerAdapter(Context mcontext, List<ContentAnswerModel> entity) {
        this.mcontext = mcontext;
        this.entity = entity;
    }

    @NonNull
    @Override
    public ContentAnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View rootView = LayoutInflater.from(mcontext).inflate(R.layout.content_answer_feed,parent,false);
        return new ContentAnswerViewHolder(rootView);

    }

    @Override
    public void onBindViewHolder(@NonNull ContentAnswerViewHolder holder, int position) {

        ContentAnswerModel contentAnswerModel = entity.get(position);
        holder.answer.setText(contentAnswerModel.getAnswer());

    }

    @Override
    public int getItemCount() {
        return entity.size();
    }

    public class ContentAnswerViewHolder extends RecyclerView.ViewHolder {
        public TextView answer;
        public ContentAnswerViewHolder(@NonNull View itemView) {
            super(itemView);
            answer = itemView.findViewById(R.id.contentAnswer);
        }
    }
}
