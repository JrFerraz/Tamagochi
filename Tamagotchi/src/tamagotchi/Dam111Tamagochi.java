package tamagotchi;
import java.util.Scanner;
public class Dam111Tamagochi {
    static BotEnemigo miEnemigo;
    static Scanner teclado;
    static tamagochi nuevoSer;
    static boolean existeEnemigo;
    public static void main(String[] args) {
        teclado = new Scanner (System.in);
        existeEnemigo=false;
        System.out.println("Dame el nombre del bicho");
        String nomSer = teclado.nextLine();
        nuevoSer = new tamagochi (nomSer);
        do{
            generarEnemigo();
            if(existeEnemigo){
                miEnemigo.mantenerLimites();
                existeEnemigo=miEnemigo.caracteristicasEnemigo(nuevoSer);
            }
            nuevoSer.generadores();
            nuevoSer.comprobarEstadosEfectos();
            nuevoSer.subirNivel();
            nuevoSer.verEstados();
            System.out.println();
            generarAcciones(nuevoSer);
            if(existeEnemigo){
                miEnemigo.aburrimiento(nuevoSer.ataque);
                miEnemigo.mantenerLimites();
                existeEnemigo=miEnemigo.caracteristicasEnemigo(nuevoSer);
                if(existeEnemigo){
                    miEnemigo.accionesPosibles(nuevoSer);
                }
            }
        }while(nuevoSer.vivo);
        finPartida(nuevoSer);
    }
    public static void generarEnemigo(){
        if(!existeEnemigo && Math.random() < 0.2){
            miEnemigo = new BotEnemigo(nuevoSer);
            System.out.println();
            System.out.println("------>Otro ser te ha detectado y se acerca con intenciones hostiles...<------");
            existeEnemigo=true;
        }
     }
    public static void generarAcciones(tamagochi nuevoSer){
        if(nuevoSer.vivo!=false){
            int selector;
            System.out.println();
            System.out.print("¿Que deseas hacer " + nuevoSer.nombre + " ?");
            System.out.print("   Pulsa 1 para comer");
            System.out.print("   Pulsa 2 para usar medicina");
            System.out.print("   Pulsa 3 para dormir");
            System.out.print("   Pulsa 4 para jugar");
            System.out.print("   Pulsa 5 para usar un calmante");
            if(existeEnemigo){
                System.out.println("   Pulsa 6 para atacar al bicho");
            }
            if(existeEnemigo){
                System.out.println("   Pulsa 7 para intentar llevaros bien");
            }
            System.out.print("   Pulsa 0 para no hacer nada");
            System.out.println();
            selector = teclado.nextInt();
            switch (selector) {
                case 0:
                    System.out.println(nuevoSer.nombre + " pudo permitirse el lujo de actuar como un perezoso y no hacer nada recibiendo así 100 puntos de experiencia,hambre (+10), estrés (+20), salud (-20) y aburrimiento (+30)");
                    nuevoSer.noHacerNada();
                    break;
                case 1:
                    nuevoSer.noAtacar();
                    nuevoSer.verComidas();
                    System.out.println("Elige una comida");
                    System.out.print("1-Galletita");
                    System.out.print("   2-Pizza");
                    System.out.println("   3-Tarta");
                    selector=teclado.nextInt();
                    nuevoSer.comer(selector);
                    break;
                case 2:
                    nuevoSer.curarse();
                    nuevoSer.noAtacar();
                    break;
                case 3:
                    nuevoSer.dormir();
                    nuevoSer.noAtacar();
                    break;
                case 4:
                    nuevoSer.jugar();
                    nuevoSer.noAtacar();
                    break;
                case 5:
                    nuevoSer.usarCalmante();
                    nuevoSer.noAtacar();
                    break;
                case 6:
                    if(existeEnemigo){
                        nuevoSer.atacar(miEnemigo);
                    }
                    break;
                case 7:
                    boolean exito;
                    if(existeEnemigo){
                        exito=nuevoSer.persuadir();
                        if(exito){
                            System.out.println("Decidiste dialogar con el ser enemigo y le caiste bien, decide irse muy feliz, no sin antes dejarte un regalo");
                            existeEnemigo = miEnemigo.regalarItem(exito, nuevoSer);
                        }else{
                            System.out.println("No le caiste bien y sigue teniendo intenciones hostiles");
                        }
                    }
                    break;
                default:
                    System.out.println("Debido a tu falta de atención, pulsaste una tecla sin funcion y "+nuevoSer.nombre+" sufre las consecuencias...");
                    break;
            } 
        }
    }
    public static void finPartida(tamagochi nuevoSer){
        System.out.println();
        System.out.println(nuevoSer.nombre + " ha vivido un tiempo de " + nuevoSer.tiempoVida + " segundos");
        System.out.println(nuevoSer.nombre +" sobrevivió hasta el nivel "+nuevoSer.nivel+" con "+nuevoSer.experiencia+" puntos de EXP");
        if(nuevoSer.estres==100) System.out.println(nuevoSer.nombre+" murio muy estresado");
        if(nuevoSer.aburrimiento==100) System.out.println(nuevoSer.nombre+" se aburrió tanto que se mató el mismo");
        if(nuevoSer.hambre==100) System.out.println(nuevoSer.nombre+" la palmó de hambre");
        if(nuevoSer.salud==0) System.out.println(nuevoSer.nombre+" murió debido a su pésima salud");
    }
}
