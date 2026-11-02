package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvPartidos;
    private SwipeRefreshLayout swipeRefresh;
    private TabLayout tabLigas;
    private List<Partido> listaPartidosGlobal = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvPartidos = findViewById(R.id.rvPartidos);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        tabLigas = findViewById(R.id.tabLigas);

        rvPartidos.setLayoutManager(new LinearLayoutManager(this));

        configurarPestanas();

        swipeRefresh.setOnRefreshListener(this::obtenerPartidos);
        obtenerPartidos();
    }



    private void configurarPestanas() {
        tabLigas.removeAllTabs(); // Limpiamos por si acaso
        tabLigas.addTab(tabLigas.newTab().setText("La Liga"));
        tabLigas.addTab(tabLigas.newTab().setText("Premier League"));
        tabLigas.addTab(tabLigas.newTab().setText("Champions"));
        tabLigas.addTab(tabLigas.newTab().setText("Serie A"));
        tabLigas.addTab(tabLigas.newTab().setText("Bundesliga"));

        tabLigas.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                filtrarPorLiga(tab.getText().toString());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void filtrarPorLiga(String tabSeleccionada) {
        List<Partido> listaFiltrada = new ArrayList<>();
        String palabraClave = tabSeleccionada.toLowerCase();

        // Ajustamos las palabras clave para que coincidan con la API
        if (tabSeleccionada.equals("La Liga")) palabraClave = "primera division";
        if (tabSeleccionada.equals("Champions")) palabraClave = "champions";
        if (tabSeleccionada.equals("Serie A")) palabraClave = "serie a";
        if (tabSeleccionada.equals("Bundesliga")) palabraClave = "bundesliga";
        // Premier League funciona directamente con "premier"

        for (Partido partido : listaPartidosGlobal) {
            if (partido.getLiga() != null && partido.getLiga().toLowerCase().contains(palabraClave)) {
                listaFiltrada.add(partido);
            }
        }

        rvPartidos.setAdapter(new PartidoAdapter(listaFiltrada));

        if (!listaFiltrada.isEmpty() && rvPartidos.getLayoutManager() != null) {
            int posicion = calcularPosicionCercana(listaFiltrada);
            ((LinearLayoutManager) rvPartidos.getLayoutManager()).scrollToPositionWithOffset(posicion, 0);
        }
    }

    // Buscador automático de la fecha actual
    private int calcularPosicionCercana(List<Partido> lista) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm", Locale.getDefault());
            String ahoraStr = sdf.format(new Date());
            long ahoraMillis = sdf.parse(ahoraStr).getTime();

            for (int i = 0; i < lista.size(); i++) {
                Date fechaPartido = sdf.parse(lista.get(i).getFechaUtc());
                // Encuentra el primer partido que es hoy o en el futuro
                if (fechaPartido != null && fechaPartido.getTime() >= ahoraMillis) {
                    // Restamos 1 para mostrar el último partido ya jugado justo encima
                    return Math.max(0, i - 1);
                }
            }
            return lista.size() - 1; // Si terminó la temporada, muestra los últimos
        } catch (Exception e) {
            return 0;
        }
    }

    private void obtenerPartidos() {
        swipeRefresh.setRefreshing(true);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            try {
                // Sustituye por tu IPv4
                URL url = new URL("http://192.168.1.44:8080/api/partidos/sincronizar");
                HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
                conexion.setRequestMethod("GET");
                conexion.setConnectTimeout(5000);
                conexion.setReadTimeout(5000);

                BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                StringBuilder respuesta = new StringBuilder();
                String linea;
                while ((linea = reader.readLine()) != null) respuesta.append(linea);
                reader.close();

                JSONObject jsonRaiz = new JSONObject(respuesta.toString());
                JSONArray arrayPartidos = jsonRaiz.getJSONArray("matches");
                List<Partido> listaTemporal = new ArrayList<>();

                for (int i = 0; i < arrayPartidos.length(); i++) {
                    JSONObject partidoJson = arrayPartidos.getJSONObject(i);
                    String fechaUtc = partidoJson.optString("utcDate", "");
                    // Extraemos la jornada (si es un torneo como la Champions puede venir nulo, le ponemos un 0)
                    String jornada = partidoJson.optString("matchday", "0");
                    JSONObject competition = partidoJson.optJSONObject("competition");
                    String nombreLiga = competition != null ? competition.optString("name", "Desconocida") : "Desconocida";

                    JSONObject homeTeam = partidoJson.getJSONObject("homeTeam");
                    JSONObject awayTeam = partidoJson.getJSONObject("awayTeam");
                    String nombreLocal = homeTeam.optString("name", "Local");
                    String urlEscudoLocal = homeTeam.optString("crest", "");
                    String nombreVisitante = awayTeam.optString("name", "Visitante");
                    String urlEscudoVisitante = awayTeam.optString("crest", "");

                    // Lógica del marcador: " vs " por defecto para el futuro
                    String marcador = " vs ";
                    JSONObject score = partidoJson.optJSONObject("score");

                    if (score != null) {
                        JSONObject fullTime = score.optJSONObject("fullTime");
                        if (fullTime != null && !fullTime.isNull("home")) {
                            int golesLocal = fullTime.optInt("home", 0);
                            int golesVisitante = fullTime.optInt("away", 0);
                            marcador = golesLocal + " - " + golesVisitante;
                        }
                    }

                    listaTemporal.add(new Partido(nombreLiga, jornada, formatearFecha(fechaUtc), nombreLocal, nombreVisitante, marcador, urlEscudoLocal, urlEscudoVisitante));
                }

                runOnUiThread(() -> {
                    listaPartidosGlobal = listaTemporal;
                    int pestanaActual = tabLigas.getSelectedTabPosition();
                    if (pestanaActual >= 0) {
                        filtrarPorLiga(tabLigas.getTabAt(pestanaActual).getText().toString());
                    }
                    swipeRefresh.setRefreshing(false);
                });

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> swipeRefresh.setRefreshing(false));
            }
        });
    }

    private String formatearFecha(String fechaUtc) {
        if (fechaUtc.isEmpty()) return "";
        try {
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
            formatoEntrada.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date fecha = formatoEntrada.parse(fechaUtc);
            SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM HH:mm", Locale.getDefault());
            formatoSalida.setTimeZone(TimeZone.getDefault());
            return formatoSalida.format(fecha);
        } catch (Exception e) {
            return fechaUtc;
        }
    }
}