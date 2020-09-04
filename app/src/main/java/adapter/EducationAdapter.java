package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.olx.EducationPerticularScheme;
import com.example.olx.R;

import java.util.List;

import model.EducationModel;

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.EducationAdapterViewHolder> {

    private Context mcontext;
    private List<EducationModel> entity;


    public EducationAdapter(Context mcontext, List<EducationModel> entity) {
        this.mcontext = mcontext;
        this.entity = entity;

    }

    @NonNull
    @Override
    public EducationAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View rootView = LayoutInflater.from(mcontext).inflate(R.layout.education_feed,parent,false);
        return new EducationAdapterViewHolder(rootView,mcontext,entity);


    }

    @Override
    public void onBindViewHolder(@NonNull EducationAdapterViewHolder holder, int position) {
        EducationModel educationModel = entity.get(position);
        holder.scheme_name.setText(educationModel.getName());

    }


    @Override
    public int getItemCount() {
        return entity.size();
    }



    public class EducationAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView scheme_name;
        Context context;
        List<EducationModel> entity;

        public EducationAdapterViewHolder(@NonNull View itemView,Context mcontext,List<EducationModel> entity) {
            super(itemView);
            scheme_name = itemView.findViewById(R.id.Eduscheme);

            itemView.setOnClickListener(this);
            this.context = mcontext;
            this.entity = entity;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, EducationPerticularScheme.class);
            intent.putExtra("name",entity.get(getAdapterPosition()).getName());
            intent.putExtra("overview",entity.get(getAdapterPosition()).getOverview());
            intent.putExtra("benefits",entity.get(getAdapterPosition()).getEligibility());
            intent.putExtra("documentRequired",entity.get(getAdapterPosition()).getDocumentRequired());
            intent.putExtra("link",entity.get(getAdapterPosition()).getLink());
            context.startActivity(intent);

        }
    }
}