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

import model.ContentQuestionModel;

public class ContentQuestionAdapter extends RecyclerView.Adapter<ContentQuestionAdapter.ContentQuestionViewHolder> {
    private List<ContentQuestionModel> entity ;
    private Context mcontext;

    public ContentQuestionAdapter(List<ContentQuestionModel> entity, Context mContext) {
        this.entity = entity;
        this.mcontext = mContext;
    }

    @NonNull
    @Override
    public ContentQuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View rootView = LayoutInflater.from(mcontext).inflate(R.layout.content_question_feed,parent,false);
        return new ContentQuestionViewHolder(rootView);

    }

    @Override
    public void onBindViewHolder(@NonNull ContentQuestionAdapter.ContentQuestionViewHolder holder, int position) {
        ContentQuestionModel contentQuestionModel  = entity.get(position);
        holder.question.setText(contentQuestionModel.getQuestion());

    }

    @Override
    public int getItemCount() {
        return entity.size();
    }

    public class ContentQuestionViewHolder extends RecyclerView.ViewHolder {
        public TextView question;
        public ContentQuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.contentOwnQuestion);
        }
    }
}
