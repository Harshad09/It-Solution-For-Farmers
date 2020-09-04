package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olx.Add_pert_answer;
import com.example.olx.R;
import com.example.olx.SearchedAnswer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchAdapterViewHolder> {

    private Context mcontext;
    private List<JSONObject> result;

    public SearchAdapter(Context mcontext, List<JSONObject> result) {
        this.mcontext = mcontext;
        this.result = result;
    }

    @NonNull
    @Override
    public SearchAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View rootView = LayoutInflater.from(mcontext).inflate(R.layout.search_feed,parent,false);
        return new SearchAdapterViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapterViewHolder holder, int position) {
        if (result.isEmpty()){
            Intent intent = new Intent(mcontext, Add_pert_answer.class);
            mcontext.startActivity(intent);
        }
        else {
            JSONObject jsonObject = result.get(position);
            try {
                holder.question.setText(jsonObject.getString("question"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class SearchAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView question;
        private Context context;
        private List<JSONObject> list;
        public SearchAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.context = mcontext;
            this.list = result;
            question = itemView.findViewById(R.id.question_search);
        }

        @Override
        public void onClick(View v) {
        Intent intent = new Intent(context, SearchedAnswer.class);
            try {
                intent.putExtra("question",list.get(getAdapterPosition()).getString("question"));
                intent.putExtra("tag1",list.get(getAdapterPosition()).getString("tag1"));
                intent.putExtra("tag2",list.get(getAdapterPosition()).getString("tag2"));
                intent.putExtra("tag3",list.get(getAdapterPosition()).getString("tag3"));
                intent.putExtra("questionid",list.get(getAdapterPosition()).getString("questionid"));
                intent.putExtra("userid",list.get(getAdapterPosition()).getString("userid"));
                context.startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
