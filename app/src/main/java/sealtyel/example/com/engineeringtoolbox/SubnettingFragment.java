package sealtyel.example.com.engineeringtoolbox;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.*;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sealtyel.example.com.engineeringtoolbox.subnetting.ElementoRed;
import sealtyel.example.com.engineeringtoolbox.subnetting.ListRedesAdapter;
import sealtyel.example.com.engineeringtoolbox.subnetting.OperacionesRedes;


public class SubnettingFragment extends Fragment {

    public SubnettingFragment(){}

    int numeroRed=0;
    int arrayNoNodo[];
    OperacionesRedes o=new OperacionesRedes();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayList<ElementoRed> values;
        final ListRedesAdapter adapter;

        View rootView = inflater.inflate(R.layout.fragment_subnetting, container, false);

        final Button botonCalcular = (Button) rootView.findViewById(R.id.buttonCalcularRed);
        final EditText red = (EditText) rootView.findViewById(R.id.editTextRed);
        final Button botonAgregar = (Button) rootView.findViewById(R.id.buttonAgregarRed);
        final ListView listaRedes = (ListView) rootView.findViewById(R.id.listViewRedes);
        values =  new ArrayList<ElementoRed>();
        adapter = new ListRedesAdapter(rootView.getContext(), values);

        listaRedes.setAdapter(adapter);

        botonAgregar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                adapter.notifyDataSetChanged();
                ElementoRed red=new ElementoRed();
                red.setTitulo("Red "+contador());
                adapter.add(red);
                listaRedes.setAdapter(adapter);
       }
        });

        botonCalcular.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                arrayNoNodo=new int[numeroRed];

                for(int i=0;i<numeroRed;i++)
                    arrayNoNodo[i]=adapter.getItem(i).getHost();

                arrayNoNodo=o.ordenarArray(arrayNoNodo);

                Fragment fragment =new MathFFragment();

                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, fragment).commit();
                } else {
                    Log.e("SubnettingFragment", "Error in creating fragment");
                }
            }
        });

        red.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {

                    String myPattern = "()|((([0-9]|[0-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5]).){3})([0-9]|[0-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])";
                    Pattern p = Pattern.compile(myPattern);
                    Matcher m = p.matcher(red.getText().toString());
                    if (!m.matches())
                        red.setError("Formato de ip incorrecto");
                }
            }
        });

        return rootView;

    }

    public int contador(){
        return ++numeroRed;
    }




}
