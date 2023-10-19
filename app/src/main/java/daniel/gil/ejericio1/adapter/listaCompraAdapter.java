package daniel.gil.ejericio1.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import daniel.gil.ejericio1.R;
import daniel.gil.ejericio1.modelos.listaCompra;

public class listaCompraAdapter extends RecyclerView.Adapter<listaCompraAdapter.ListaCompraVH>{
    private List<listaCompra> objects;
    private int resource;
    private Context context;

    public listaCompraAdapter(List<listaCompra> objects, int resource, Context context) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
    }


    @NonNull
    @Override
    public listaCompraAdapter.ListaCompraVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View toDoView = LayoutInflater.from(context).inflate(resource,null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        toDoView.setLayoutParams(layoutParams);
        return new ListaCompraVH(toDoView);
    }

    @Override
    public void onBindViewHolder(@NonNull listaCompraAdapter.ListaCompraVH holder, int position) {
        listaCompra toDo = objects.get(position);
        holder.lblNombre.setText(toDo.getNombre());
        holder.lblCantidad.setText(toDo.getCantidad());


        holder.btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmarBorrar(context.getString(R.string.app_name), holder.getAdapterPosition()).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }


    private AlertDialog confirmarBorrar(String titulo, int posicion){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titulo);
        builder.setCancelable(true);

        builder.setNegativeButton("NO",null);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                objects.remove(posicion);
                notifyItemRemoved(posicion);
            }
        });

        return builder.create();
    }

    public class ListaCompraVH extends RecyclerView.ViewHolder {
        TextView lblNombre;
        TextView lblCantidad;
        ImageButton btnBorrar;

        public ListaCompraVH(@NonNull View itemView){
            super(itemView);
            lblNombre = itemView.findViewById(R.id.lblNombreListaCompraView);
            lblCantidad = itemView.findViewById(R.id.lblCantidadListaCompraView);
            btnBorrar = itemView.findViewById(R.id.btnBorraToDoViewModel);
        }
    }
}
