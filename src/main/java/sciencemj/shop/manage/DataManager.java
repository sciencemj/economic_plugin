package sciencemj.shop.manage;

import sciencemj.shop.Ecnm;

public class DataManager {

    public static void saveData(String name, Object data){
        if(!Ecnm.config.contains(name)){
            Ecnm.config.addDefault(name, data);
        }else {
            Ecnm.config.set(name, data);
        }
    }
    public static Object loadData(String name){
        return Ecnm.config.get(name);
    }
}
