package daniel.gil.ejericio1;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import daniel.gil.ejericio1.adapter.listaCompraAdapter;
import daniel.gil.ejericio1.databinding.ActivityMainBinding;
import daniel.gil.ejericio1.modelos.listaCompra;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<listaCompra> compraList;
    private listaCompraAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        compraList = new ArrayList<>();

        adapter = new listaCompraAdapter(compraList, R.layout.lista_compra_view_model, MainActivity.this);
        binding.contentMain.contenedor.setAdapter(adapter);
        layoutManager = new GridLayoutManager(MainActivity.this,2);
        binding.contentMain.contenedor.setLayoutManager(layoutManager);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearListaCompra().show();
            }
        });
    }

    private AlertDialog crearListaCompra() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Crear ListaCompra");
        builder.setCancelable(false);

        View listaCompraAlert = LayoutInflater.from(this).inflate(R.layout.lista_compra_alert_model, null);
        EditText txtNombre = listaCompraAlert.findViewById(R.id.txtNombreListaCompraModelAlert);
        EditText txtCantidad = listaCompraAlert.findViewById(R.id.txtCantidadListaCompraModelAlert);
        EditText txtPrecio = listaCompraAlert.findViewById(R.id.txtPrecioListaCompraModelAlert);
        builder.setView(listaCompraAlert);

        builder.setNegativeButton("Cancelar", null);
        builder.setPositiveButton("Crear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!txtNombre.getText().toString().isEmpty() && !txtPrecio.getText().toString().isEmpty()){
                    compraList.add(new listaCompra(txtNombre.getText().toString(), txtPrecio.getText(), txtCantidad.getText()));
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(MainActivity.this,"Faltan datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return builder.create();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("LISTA",compraList);
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        compraList.addAll((ArrayList<listaCompra>) savedInstanceState.getSerializable("LISTA"));
        adapter.notifyItemRangeInserted(0,compraList.size());
    }
}