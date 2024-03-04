package festivales.modelo;

import java.util.*;


    /**
     * Esta clase guarda una agenda con los festivales programados
     * en una serie de meses
     *
     * La agenda guardalos festivales en una colecci�n map
     * La clave del map es el mes (un enumerado festivales.modelo.festivales.modelo.Mes)
     * Cada mes tiene asociados en una colecci�n ArrayList
     * los festivales  de ese mes
     *
     * Solo aparecen los meses que incluyen alg�n festival
     *
     * Las claves se recuperan en orden alfab�ico
     *
     */
    public class AgendaFestivales {
        private TreeMap<Mes, ArrayList<Festival>> agenda;

        public AgendaFestivales() {
            this.agenda = new TreeMap<>();
        }

        /**
         * a�ade un nuevo festival a la agenda
         *
         * Si la clave (el mes en el que se celebra el festival)
         * no existe en la agenda se crear� una nueva entrada
         * con dicha clave y la colecci�n formada por ese �nico festival
         *
         * Si la clave (el mes) ya existe se a�ade el nuevo festival
         * a la lista de festivales que ya existe ese ms
         * insert�ndolo de forma que quede ordenado por nombre de festival.
         * Para este segundo caso usa el m�todo de ayuda
         * obtenerPosicionDeInsercion()
         *
         */
        public void addFestival(Festival festival) {
            Mes mes = festival.getMes();
            if(agenda.containsKey(mes)){
                int posicion = obtenerPosicionDeInsercion(agenda.get(mes), festival);
                agenda.get(mes).add(posicion, festival);
            }
            else{
                ArrayList<Festival> festivales = new ArrayList<>();
                festivales.add(festival);
                agenda.put(mes, festivales);
            }

        }

        /**
         *
         * @param festivales una lista de festivales
         * @param festival
         * @return la posici�n en la que deber�a ir el nuevo festival
         * de forma que la lista quedase ordenada por nombre
         */
        private int obtenerPosicionDeInsercion(ArrayList<Festival> festivales,
                                               Festival festival) {
            int anyadir = 0;
            for (Festival festival2 : festivales) {
                if (festival2.getNombre().compareTo(festival.getNombre()) > 0) {
                    anyadir++;
                }
                else {
                    break;
                }
            }
            return anyadir;
        }

        /**
         * Representaci�n textual del festival
         * De forma eficiente
         *  Usa el conjunto de entradas para recorrer el map
         */
        @Override
        public String toString() {
            //TODO
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<Mes, ArrayList<Festival>> festisEnMes : agenda.entrySet()) {
                Mes mes = festisEnMes.getKey();
                ArrayList<Festival> festivales = festisEnMes.getValue();
                sb.append(mes).append(" (").append(festivales.size()).append(" festival/es)\n");
                for (Festival festival : festivales) {
                    sb.append(festival).append("\n");
                }
            }
            return sb.toString();
        }

        /**
         *
         * @param mes el mes a considerar
         * @return la cantidad de festivales que hay en ese mes
         * Si el mes no existe se devuelve -1
         */
        public int festivalesEnMes(Mes mes) {
            if (agenda.containsKey(mes))
            {
                return agenda.get(mes).size();
            }
            else
            {
                return -1;
            }
        }

        /**
         * Se trata de agrupar todos los festivales de la agenda
         * por estilo.
         * Cada estilo que aparece en la agenda tiene asociada una colecci�n
         * que es el conjunto de nombres de festivales que pertenecen a ese estilo
         * Importa el orden de los nombres en el conjunto
         *
         * Identifica el tipo exacto del valor de retorno
         */
        public  Map <Estilo, List<Festival>>  festivalesPorEstilo() {
           //TODO
            Map <Estilo, List<Festival>> mapFestivales = new HashMap<Estilo, List<Festival>>();
            for (Estilo estilo : Estilo.values()){
                mapFestivales.put(estilo, new ArrayList<Festival>());
            }
            for (ArrayList<Festival> listFest : agenda.values()){
                for (Festival festi: listFest) {
                    for (Estilo est : festi.getEstilos()){
                        ArrayList<Festival> listMod = (ArrayList<Festival>) mapFestivales.get(est);


                        int posicion = obtenerPosicionDeInsercion(listMod, festi);

                        listMod.add(posicion,festi);

                        mapFestivales.put(est, listMod);
                    }
                }

            }

            return mapFestivales;



        }

        /**
         * Se cancelan todos los festivales organizados en alguno de los
         * lugares que indica el conjunto en el mes indicado. Los festivales
         * concluidos o que no empezados no se tienen en cuenta
         * Hay que borrarlos de la agenda
         * Si el mes no existe se devuelve -1
         *
         * Si al borrar de un mes los festivales el mes queda con 0 festivales
         * se borra la entrada completa del map
         */
        public int cancelarFestivales(HashSet<String> lugares, Mes mes) {
           //TODO
            if (mes.compareTo(Mes.ENERO) < 0 || mes.compareTo(Mes.DICIEMBRE) > 0){
                return -1;
            }

            int cancelados = 0;

            if (festivalesEnMes(mes) != -1){
               ArrayList<Festival> listFest = agenda.get(mes);
               for (Festival festi : listFest){
                   festi.getLugar();
                   if (!festi.haConcluido() && lugares.contains(festi.getLugar())){
                       listFest.remove(festi);
                       cancelados++;

                       //Falta ver si el festi ha terminado
                   }


               }
               agenda.put(mes, listFest);
            }else{
                return 0;
            }

            return cancelados;



        }
    }